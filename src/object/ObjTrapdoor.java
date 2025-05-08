package object;

import entity.Entity;
import main.GamePanel;

public class ObjTrapdoor extends Entity {
    public ObjTrapdoor(GamePanel gp) {
        super(gp);

        name = "trapdoor";
        down1 = setUpImage("/objects/trapdoor", gp.tileSize, gp.tileSize);
        collisionObject = true;
    }
}
