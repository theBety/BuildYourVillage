package object;

import entity.Entity;
import main.GamePanel;
import main.GameState;
import main.ItemType;

public class ObjKey extends Entity {

    public ObjKey(GamePanel gp) {
        super(gp);

        name = "key";
        typeOfItem = ItemType.MATERIAL;
        down1 = setUpImage("/objects/key", gp.tileSize, gp.tileSize);
        descriptionOfItem = "Name: Key.\nFor opening doors or chests.\nDon't lose it!";
        price = 150;
        isStackable = true;
    }

    public boolean useObject(Entity entity) {
        gp.gameState = GameState.DIALOGUE;
        int objIndex = getDetected(entity, gp.objects, "chest");
        if(objIndex != -1){
            gp.ui.currentDialogue = "You opened the chest";
            gp.objects[gp.currentMap][objIndex].interact2();
            return true;
        }else{
            gp.ui.currentDialogue = "What are you trying to do?";
            return false;
        }
    }
}