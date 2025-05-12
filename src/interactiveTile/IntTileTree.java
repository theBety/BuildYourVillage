package interactiveTile;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class IntTileTree extends InteractiveTile{
    GamePanel gp;

    public IntTileTree(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setUpImage("/InteractiveTiles/justTree", gp.tileSize, gp.tileSize);
        isDestructible = true;
        life = 4;
    }

    public boolean isRequiredItem(Entity entity){
        return entity.currentTool.typeOfItem == ToolType.AXE;
    }

    public void playSoundEffect(){
        gp.playSoundEffect(2);
    }
}
