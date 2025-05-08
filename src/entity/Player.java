package entity;

import main.GamePanel;
import main.GameState;
import main.KeyHandler;
import object.ObjKey;
import object.ObjTrapdoor;
import object.ToolAxe;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX, screenY;
    public ArrayList<Entity> inventory = new ArrayList<>();
    final int inventoryCapacity = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 36;//muze byt zmeneno podle zbrane nebo neco
        attackArea.height = 36;
        setValues();
        getPlayerImage();
        getAxeImages();
        setDefaultItems();
    }

    public void setValues() {
        worldX = gp.tileSize * 23; //Start position of a player. The number is tiles x,y. YOU CAN CHANGE THAT
        worldY = gp.tileSize * 21;

        speed = 4;
        direction = "down";
        coins = 0;
        currentTool = new ToolAxe(gp);
        //currentBoots = new boty;
    }

    public void setDefaultItems(){
        inventory.add(currentTool);
        inventory.add(new ObjKey(gp));
        inventory.add(new ObjTrapdoor(gp));
        inventory.add(new ObjKey(gp));
        inventory.add(new ObjTrapdoor(gp));
        inventory.add(new ObjKey(gp));
        inventory.add(new ObjTrapdoor(gp));
        inventory.add(new ObjKey(gp));
        inventory.add(new ObjTrapdoor(gp));
        inventory.add(new ObjKey(gp));
        inventory.add(new ObjTrapdoor(gp));
    }

    /**
     * Reads images of player - they're changing when he's moving
     */
    public void getPlayerImage() {
        up1 = setUpImage("/player/back1", gp.tileSize, gp.tileSize);
        up2 = setUpImage("/player/back2", gp.tileSize, gp.tileSize);
        down1 = setUpImage("/player/front1", gp.tileSize, gp.tileSize);
        down2 = setUpImage("/player/front2", gp.tileSize, gp.tileSize);
        left1 = setUpImage("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setUpImage("/player/left2", gp.tileSize, gp.tileSize);
        right1 = setUpImage("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setUpImage("/player/right2", gp.tileSize, gp.tileSize);
    }

    /**
     *
     */
    public void getAxeImages() {
        up1Axe1 = setUpImage("/player/back1Axe1", gp.tileSize * 2, gp.tileSize);
        up1Axe2 = setUpImage("/player/back1Axe2", gp.tileSize * 2, gp.tileSize);
        down1Axe1 = setUpImage("/player/front1Axe1", gp.tileSize * 2, gp.tileSize);
        down1Axe2 = setUpImage("/player/front1Axe2", gp.tileSize * 2, gp.tileSize);
        left1Axe1 = setUpImage("/player/left1Axe1", gp.tileSize * 2, gp.tileSize);
        left1Axe2 = setUpImage("/player/left1Axe2", gp.tileSize * 2, gp.tileSize);
        right1Axe1 = setUpImage("/player/right1Axe1", gp.tileSize * 2, gp.tileSize);
        right1Axe2 = setUpImage("/player/right1Axe2", gp.tileSize * 2, gp.tileSize);
    }

    /**
     * Based on what key is pressed, the player moves to some direction.
     * Then, the program checks if there's a collision happening.
     * If not, spriteCounter++.
     * If the counter is bigger than 13, it resets.
     * (13 means that the images are changing every 13 frames because this game has 60 FPS).
     */
    public void update() {
        if (attacking) {
            attackWithAxe();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
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
            gp.keyH.spacePressed = false;
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

    public void interactWithNpc(int i) {
        if (i != -1) {
            gp.gameState = GameState.DIALOGUE;
            gp.npc[i].speak();
        } else {
            if (gp.keyH.spacePressed) {
                attacking = true;
            }
        }
    }

    /**
     * Attacking animation
     */
    public void attackWithAxe() {
        spriteCounter++; //to do animations

        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNumber = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
        }
        if (spriteCounter > 25) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;


        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = up1Axe1;
                    }
                    if (spriteNumber == 2) {
                        image = up1Axe2;
                    }
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = down1Axe1;
                    }
                    if (spriteNumber == 2) {
                        image = down1Axe2;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = left1Axe1;
                    }
                    if (spriteNumber == 2) {
                        image = left1Axe2;
                    }
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = right1Axe1;
                    }
                    if (spriteNumber == 2) {
                        image = right1Axe2;
                    }
                }
                break;
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
    }
}
/*
String objectName = gp.objects[index].name;
            switch (objectName) {
                case "key":
                    gp.objects[index] = null;
                    gp.ui.printMessage("You picked up key"); //prints message on a screen.
                    Break;
            }
 */
