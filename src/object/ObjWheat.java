package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

import java.awt.*;

public class ObjWheat extends Entity {

    public ObjWheat(GamePanel gp) {
        super(gp);
        down1 = setUpImage("/objects/Wheat", gp.tileSize/2, gp.tileSize/2);
        name = "wheat";
        typeOfItem = ToolType.MATERIAL;
        descriptionOfItem = "Wheat\nGive to builder to build roof!";
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = down1;
        g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
    }
}
