package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

import java.awt.*;

public class ObjCoin extends Entity {
    GamePanel gp;

    public ObjCoin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        typeOfItem = ToolType.PICKUP;
        name = "coin";
        value = 5;
        down1 = setUpImage("/objects/coin", gp.tileSize/2, gp.tileSize/2);
        descriptionOfItem = "Coin\nBuy items!";
    }

    public void useObject(Entity entity) {
        gp.ui.addMessage("coin +" + value);
        gp.player.coins += value;
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        image = down1;
        g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
    }
}
