package interactiveTile;

import entity.Entity;
import main.GamePanel;
import main.ToolType;
import object.ObjWheat;

public class IntTileWheat extends InteractiveTile{
    GamePanel gp;

    public IntTileWheat(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setUpImage("/InteractiveTiles/wheatField", gp.tileSize, gp.tileSize);
        isDestructible = true;
        life = 4;
        dropItem = new ObjWheat(gp);
    }

    public boolean isRequiredItem(Entity entity){
        return entity.currentTool.typeOfItem == ToolType.HOE;
    }

    public void playSoundEffect(){
        //pridat sound effect
    }
}
