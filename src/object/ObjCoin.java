package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ObjCoin extends Entity {
    GamePanel gp;
    public ObjCoin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        toolType = ToolType.PICKUP;
        name = "coin";
        value = 5;
        down1 = setUpImage("/objects/coin", gp.tileSize, gp.tileSize);
    }

    public void useObject(Entity entity){
        gp.ui.addMessage("coin +" + value);
        gp.player.coins+=3;
    }
}
