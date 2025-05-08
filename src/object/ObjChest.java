package object;

import entity.Entity;
import main.GamePanel;
public class ObjChest extends Entity {

    public ObjChest(GamePanel gp) {
        super(gp);

        name = "chest";
        down1 = setUpImage("/objects/chest", gp.tileSize, gp.tileSize);
        collisionObject = true;
    }
}

