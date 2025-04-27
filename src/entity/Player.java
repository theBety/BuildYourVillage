package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setValues();
        getPlayerImage();
    }

    public void setValues() {
        worldX = gp.tileSize * 23; //Start position of a player. the number is tiles x,y. YOU CAN CHANGE THAT
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    /**
     * Reads images of player - they're changing when he's moving
     */
    public void getPlayerImage() {
        up1 = setUpImage("/player/back1");
        up2 = setUpImage("/player/back2");
        down1 = setUpImage("/player/front1");
        down2 = setUpImage("/player/front2");
        left1 = setUpImage("/player/left1");
        left2 = setUpImage("/player/left2");
        right1 = setUpImage("/player/right1");
        right2 = setUpImage("/player/right2");
    }

    /**
     * based on what key is pressed, player moves to some direction. Then, program checks if there's collision happening.
     * if not, spriteCounter++. if the counter is bigger than 13, it resets. (13 means that the images are changing every 13 frames.
     * because this game has 60 FPS).
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }
            collisionOn = false;
            gp.checkCollision.checkTile(this);

            int objectIndex = gp.checkCollision.checkObject(this, true);
            pickUpObject(objectIndex);

            int npcIndex = gp.checkCollision.checkEntity(this, gp.npc);
            interactWithNpc(npcIndex);
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

    public void pickUpObject(int index) {
        if (index != -1) {

        }
    }
    public void interactWithNpc(int i){
        if(i !=-1){
            System.out.println("You're hitting npc");
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                }
                if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                }
                if (spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }
}
/*
String objectName = gp.objects[index].name;
            switch (objectName) {
                case "key":
                    gp.objects[index] = null;
                    gp.ui.printMessage("You picked up key"); //prints message on a screen.
                    break;
            }
 */
