package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public abstract class Entity {
    GamePanel gp;
    public int worldX;
    public int worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea = new Rectangle(8, 16, 32, 32);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int counterForEntityMovement = 0;
    public String[] dialogues = new String[10];
    UtilityTool utilityTool = new UtilityTool();
    int dialogueCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Scaled images. Why? - saves time.
     *
     * @param imageName path and name of an image
     * @return scaled image
     */
    public BufferedImage setUpImage(String imageName) {

        BufferedImage scaledImage;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            scaledImage = utilityTool.scaledImage(scaledImage, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }

    public void action(){
        counterForEntityMovement++;

        if(counterForEntityMovement == 90){
            Random rd = new Random();
            int index = rd.nextInt(4);
            Directions directionFromEnum = Directions.values()[index];
            direction = directionFromEnum.toString().toLowerCase();
            counterForEntityMovement = 0;
        }
    }

    public void update(){
        action();

        collisionOn = false;
        gp.checkCollision.checkTile(this);
        gp.checkCollision.checkPlayer(this);
        gp.checkCollision.checkObject(this, false);
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
            spriteCounter++;
            if (spriteCounter > 13) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }
//dialog nefunguje nevim proc
    public void speak() {
        if (dialogues[dialogueCounter] == null){
            dialogueCounter = 0;
        }else{
            gp.ui.currentDialogue = dialogues[dialogueCounter];
            dialogueCounter++;
        }
//So NPC is facing the player.
        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    /**
     * This method firstly calculates if an entity can be visible on a screen.
     * If not, nothing happens because there's nothing to draw.
     * But if so, based on sprite number
     * (this number is constantly switching from 1 to 2, it just shows what image to draw so it looks
     * like the character is walking) one picture is selected and drawn.
     * @param g2 drawing with help of graphics2D
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up":
                    if (spriteNumber == 1) image = up1;
                    if (spriteNumber == 2) image = up2;
                    break;
                case "down":
                    if (spriteNumber == 1) image = down1;

                    if (spriteNumber == 2) image = down2;
                    break;
                case "left":
                    if (spriteNumber == 1) image = left1;
                    if (spriteNumber == 2) image = left2;
                    break;
                case "right":
                    if (spriteNumber == 1) image = right1;
                    if (spriteNumber == 2) image = right2;
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }


    }
}
