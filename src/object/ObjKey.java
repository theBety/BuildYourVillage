package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ObjKey extends Entity {

    public ObjKey(GamePanel gp) {
        super(gp);

        name = "key";
        typeOfItem = ToolType.MATERIAL;
        down1 = setUpImage("/objects/key", gp.tileSize, gp.tileSize);
        descriptionOfItem = "Name: Key.\nFor opening doors or chests.\nDon't lose it!";
        price = 150;

    }

}
