package entity;

import main.GamePanel;
import main.ToolType;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    GamePanel gp;
    public int worldX;
    public int worldY;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage up1Axe1, up1Axe2, down1Axe1, down1Axe2, left1Axe1, left1Axe2, right1Axe1, right1Axe2;

    public String direction = "down";
    public int spriteCounter = 0;
    int dialogueCounter = 0;
    public int invincibleCounter = 0;
    public int counterForEntityMovement = 0;
    public int spriteNumber = 1;
    public boolean collisionOn = false;
    boolean attacking = false;

    public Rectangle solidArea = new Rectangle(8, 16, 32, 32);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public String[] dialogues = new String[10];
    UtilityTool utilityTool = new UtilityTool();

    //Character Attributes
    public int speed;
    public String name;
    public boolean isInvincible = false;
    public int coins;
    public int strength;
    public int attack;
    public Entity currentTool;
    public Entity currentBoots;
    public int life;
    public int level;
    public int exp;
    public int expToNextLevel;
    public int value;


    //Item attributes
    public int attackValue;
    public BufferedImage image;
    public boolean collisionObject = false;
    public String descriptionOfItem = "";
    public ToolType typeOfItem;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Scaled images. Why? - saves time.
     *
     * @param imageName path and name of an image
     * @return scaled image
     */
    public BufferedImage setUpImage(String imageName, int width, int height) {
        BufferedImage scaledImage;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            scaledImage = utilityTool.scaledImage(scaledImage, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }

    /**
     * Movement for player.
     */
    public void action() {
    }

    public void useObject(Entity e) {
        //video 28
    }
    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gp.objects.length; i++) {
            if(gp.objects[i] == null){
                gp.objects[i] = droppedItem;
                gp.objects[i].worldX = worldX;
                gp.objects[i].worldY = worldY;
                break;
            }
        }
    }

    /**
     * Updates entity movement. Checks if entity isn't walking through something they can't.
     */
    public void update() {
        action();
        collisionOn = false;
        gp.checkCollision.checkTile(this);
        gp.checkCollision.checkPlayer(this);
        gp.checkCollision.checkObject(this, false);
        gp.checkCollision.checkEntity(this, gp.iTile);
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
        if (dialogues[dialogueCounter] == null) {
            dialogueCounter = 0;
        } else {
            gp.ui.currentDialogue = dialogues[dialogueCounter];
            dialogueCounter++;
        }
        //So NPC is facing the player.
        switch (gp.player.direction) {
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
     *
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
