package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ObjPurpleHouse extends Entity {

    public ObjPurpleHouse(GamePanel gp) {
        super(gp);

        name = "purpleHouse";
        typeOfItem = ToolType.PLACED;
        down1 = setUpImage("/objects/purpleHouse", gp.tileSize * 2, gp.tileSize);
        collisionObject = true;
    }
}
