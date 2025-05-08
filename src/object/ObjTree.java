package object;

import entity.Entity;
import main.GamePanel;

public class ObjTree extends Entity {

    public ObjTree(GamePanel gp) {
        super(gp);
        name = "tree";
        down1 = setUpImage("/objects/justTree", gp.tileSize, gp.tileSize);
        collisionObject = true;
    }
}
