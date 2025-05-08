package object;

import entity.Entity;
import main.GamePanel;

public class ToolAxe extends Entity {

    public ToolAxe(GamePanel gp) {
        super(gp);
        name = "Axe";
        down1 = setUpImage("/objects/justTree", gp.tileSize, gp.tileSize);
        attackValue = 1;
        descriptionOfItem = "Name: Axe level 1.\nNot too strong but it will get\nthe job done.";
    }
}
