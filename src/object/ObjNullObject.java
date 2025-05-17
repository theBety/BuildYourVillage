package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

import java.awt.*;

public class ObjNullObject extends Entity {
    public ObjNullObject(GamePanel gp) {
        super(gp);
        name = "log";
        typeOfItem = ToolType.ELSE;
        price = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = down1;
        g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
    }
}
