package object;

import entity.Entity;
import main.GamePanel;
import main.ItemType;

public class ObjTrapdoor extends Entity {
    public ObjTrapdoor(GamePanel gp) {
        super(gp);

        name = "trapdoor";
        typeOfItem = ItemType.PLACED;
        down1 = setUpImage("/objects/trapdoor", gp.tileSize, gp.tileSize);
        collisionObject = true;
    }
}
