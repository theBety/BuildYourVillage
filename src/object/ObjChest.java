package object;

import entity.Entity;
import main.GamePanel;
import main.GameState;
import main.ItemType;

public class ObjChest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean isOpened = false;

    public ObjChest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        name = "chest";
        typeOfItem = ItemType.OBSTACLE;
        down1 = setUpImage("/objects/chest", gp.tileSize, gp.tileSize);
        collisionObject = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        gp.gameState = GameState.DIALOGUE;
        gp.ui.currentDialogue = "You need a key";
        if(isOpened){ gp.ui.currentDialogue = "An empty chest";}
    }

    public void interact2(){
        if (!isOpened) {
            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and found a ").append(loot.name).append(" !");

            if (!gp.player.canStackItem(loot)) {
                sb.append("\nBut your inventory is full!");
            } else {
                sb.append("\nYou collected ").append(loot.name).append(" !");
                if(loot.name.equals("coin")){
                    loot.useObject(this);
                }
                down1 = setUpImage("/objects/chestOpened", gp.tileSize, gp.tileSize);
                isOpened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }else{
            gp.ui.currentDialogue = "An empty chest";
        }
    }
}

