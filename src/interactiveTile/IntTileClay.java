package interactiveTile;

import entity.Entity;
import main.GamePanel;
import main.ItemType;
import object.ObjClay;

public class IntTileClay extends InteractiveTile{
    GamePanel gp;

    public IntTileClay(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setUpImage("/InteractiveTiles/clay", gp.tileSize, gp.tileSize);
        isDestructible = true;
        dropItem = new ObjClay(gp);
        life = 4;
    }

    public boolean isRequiredItem(Entity entity){
        return entity.currentTool.typeOfItem == ItemType.PICAXE;
    }
}
