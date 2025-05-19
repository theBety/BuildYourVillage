package object;

import entity.Entity;
import main.GamePanel;
import main.ItemType;

import java.awt.*;

public class ObjStone extends Entity{

    public ObjStone(GamePanel gp) {
        super(gp);
        down1 = setUpImage("/objects/Stone", gp.tileSize/2, gp.tileSize/2);
        name = "stone";
        typeOfItem = ItemType.MATERIAL;
        descriptionOfItem = "Stone\nGive to builder to build walls!";
        price = 3;
        isStackable = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = down1;
        g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
    }
}
