package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

import java.awt.*;

public class ObjClay extends Entity {
    public ObjClay(GamePanel gp) {
        super(gp);
        down1 = setUpImage("/objects/Clay", gp.tileSize / 2, gp.tileSize / 2);
        name = "clay";
        typeOfItem = ToolType.MATERIAL;
        descriptionOfItem = "Clay\nGive to builder to build walls!";
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = down1;
        g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
    }
}