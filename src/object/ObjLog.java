package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ObjLog extends Entity {

    public ObjLog(GamePanel gp) {
        super(gp);
        down1 = setUpImage("/objects/Log", gp.tileSize/2, gp.tileSize/2);
        name = "log";
        typeOfItem = ToolType.MATERIAL;
    }
}
