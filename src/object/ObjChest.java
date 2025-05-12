package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ObjChest extends Entity {

    public ObjChest(GamePanel gp) {
        super(gp);

        name = "chest";
        typeOfItem = ToolType.PLACED;
        down1 = setUpImage("/objects/chest", gp.tileSize, gp.tileSize);
        collisionObject = true;
    }
}

