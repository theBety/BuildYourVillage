package object;

import entity.Entity;
import main.GamePanel;
import main.ItemType;

import java.awt.*;

public class ObjLog extends Entity {

    public ObjLog(GamePanel gp) {
        super(gp);
        down1 = setUpImage("/objects/Log", gp.tileSize/2, gp.tileSize/2);
        down2 = setUpImage("/objects/Log", gp.tileSize, gp.tileSize);
        name = "log";
        typeOfItem = ItemType.MATERIAL;
        descriptionOfItem = "Log\nGive to builder to build floors!";
        price = 2;
        isStackable = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (drawBiggerMaterial) {
            image = down2;
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            image = down1;
            g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
        }
    }
}
