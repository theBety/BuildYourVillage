package object;

import entity.Entity;
import main.GamePanel;
import main.ItemType;

public class ToolPicaxe extends Entity {
    public ToolPicaxe(GamePanel gp, int level) {
        super(gp);
        name = "Picaxe";

        switch(level) {
            case 1:
                down1 = setUpImage("/objects/PicaxeLvl1", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Picaxe level 1.\nNot too strong but it will get\nthe job done.";
                attackArea.height =25;
                attackArea.width = 25;
                price = 40;
                break;
            case 2:
                down1 = setUpImage("/objects/PicaxeLvl2", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Picaxe level 2.\nNot to bad gets the job\n done quickly.";
                attackArea.height =30;
                attackArea.width = 30;
                price = 80;
                break;
            case 3:
                down1 = setUpImage("/objects/PicaxeLvl3", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Picaxe level 3.\nThe best picaxe!\nFast and gets the job done.";
                attackArea.height =35;
                attackArea.width = 35;
                price = 320;
                break;
        }
        typeOfItem = ItemType.PICAXE;
        attackValue = level;
    }
}
