package interactiveTile;

import entity.Entity;
import main.GamePanel;
import main.ItemType;
import object.ObjStone;

public class IntTileStone extends InteractiveTile{
    GamePanel gp;

    public IntTileStone(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setUpImage("/InteractiveTiles/stone", gp.tileSize, gp.tileSize);
        isDestructible = true;
        life = 4;
        dropItem = new ObjStone(gp);
    }

    public boolean isRequiredItem(Entity entity){
        return entity.currentTool.typeOfItem == ItemType.PICAXE;
    }
}
