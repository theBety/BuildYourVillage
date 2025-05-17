package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

import java.awt.*;

public class ObjPurpleHouse extends Entity {

    public ObjPurpleHouse(GamePanel gp) {
        super(gp);

        name = "purpleHouse";
        typeOfItem = ToolType.PLACED;
        down1 = setUpImage("/objects/purpleHouse", gp.tileSize * 2, gp.tileSize*2);
        collisionObject = true;
        solidArea = new Rectangle(solidAreaDefaultX,solidAreaDefaultY, gp.tileSize*3, gp.tileSize*2);
    }

    @Override
    public void draw(Graphics2D g2) {
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
            g2.drawImage(image, screenX, screenY, gp.tileSize*3, gp.tileSize*3, null);
        }
    }
}
