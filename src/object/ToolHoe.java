package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ToolHoe extends Entity {
    public ToolHoe(GamePanel gp, int level) {
        super(gp);
        name = "hoe";
        switch(level) {
            case 1:
                down1 = setUpImage("/objects/HoeLvl1", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Hoe level 1.\nNot too strong but it will get\nthe job done.";
                attackArea.height =25;
                attackArea.width = 25;
                break;
            case 2:
                down1 = setUpImage("/objects/HoeLvl2", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Hoe level 2.\nNot to bad gets the job \ndone quickly.";
                attackArea.height =30;
                attackArea.width = 30;
                break;
            case 3:
                down1 = setUpImage("/objects/HoeLvl3", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Hoe level 3.\nThe best hoe!\nFast and gets the job done.";
                attackArea.height =35;
                attackArea.width = 35;
                break;
        }
        typeOfItem = ToolType.HOE;
        attackValue = level;
    }
}
