package object;

import entity.Entity;
import main.GamePanel;
import main.ToolType;

public class ToolAxe extends Entity {

    public ToolAxe(GamePanel gp, int level) {
        super(gp);
        name = "Axe";
        switch(level) {
            case 1:
                down1 = setUpImage("/objects/AxeLvl1", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Axe level 1.\nNot too strong but it will get\nthe job done.";
                attackArea.height =25;
                attackArea.width = 25;
                break;
            case 2:
                down1 = setUpImage("/objects/AxeLvl2", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Axe level 2.\nNot to bad gets the job \ndone quickly.";
                attackArea.height =30;
                attackArea.width = 30;
                break;
            case 3:
                down1 = setUpImage("/objects/AxeLvl3", gp.tileSize, gp.tileSize);
                descriptionOfItem = "Name: Axe level 3.\nThe best axe!\nFast and gets the job done.";
                attackArea.height =35;
                attackArea.width = 35;
                break;
        }
        toolType = ToolType.AXE;
        attackValue = level;
    }
}
