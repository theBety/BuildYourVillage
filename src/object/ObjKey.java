package object;

import entity.Entity;
import main.GamePanel;

public class ObjKey extends Entity {

    public ObjKey(GamePanel gp) {
        super(gp);

        name = "key";
        down1 = setUpImage("/objects/key", gp.tileSize, gp.tileSize);
    }

}
