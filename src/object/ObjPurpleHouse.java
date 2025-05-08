package object;

import entity.Entity;
import main.GamePanel;

public class ObjPurpleHouse extends Entity {

    public ObjPurpleHouse(GamePanel gp) {
        super(gp);

        name = "purpleHouse";
        down1 = setUpImage("/objects/purpleHouse", gp.tileSize*2, gp.tileSize*2);
        collisionObject = true;
    }
}
