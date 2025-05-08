package object;

import entity.Entity;
import main.GamePanel;

public class ObjBoots extends Entity {
    //pridat neco jako druh boty - rychlejsi/ lepsi boty

    public ObjBoots(GamePanel gp) {
        super(gp);

        name = "boots";
        down1 = setUpImage("/objects/boots", gp.tileSize, gp.tileSize);
    }
}
