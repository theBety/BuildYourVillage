package entity;

import main.GamePanel;

import java.util.Random;

public class Npc extends Entity {

    public Npc(GamePanel gp) {
        super(gp);
        direction = "left";
        speed = 2;
        getNpcImage();
        setDialouge();
    }

    public void getNpcImage() {
        up1 = setUpImage("/NPC/back1NPC", gp.tileSize, gp.tileSize);
        up2 = setUpImage("/NPC/back2NPC", gp.tileSize, gp.tileSize);
        down1 = setUpImage("/NPC/front1NPC", gp.tileSize, gp.tileSize);
        down2 = setUpImage("/NPC/front2NPC", gp.tileSize, gp.tileSize);
        left1 = setUpImage("/NPC/leftStandNPC", gp.tileSize, gp.tileSize);
        left2 = setUpImage("/NPC/leftStand2NPC", gp.tileSize, gp.tileSize);
        right1 = setUpImage("/NPC/rightStandNPC", gp.tileSize, gp.tileSize);
        right2 = setUpImage("/NPC/rightStand2NPC", gp.tileSize, gp.tileSize);
    }

    /**
     * The simplest AI movement. Every 2-second direction on a npc randomly changes.
     */
    public void action() {
        counterForEntityMovement++;

        if (counterForEntityMovement == 120) {
            Random rd = new Random();
            int index = rd.nextInt(4);
            Directions directionFromEnum = Directions.values()[index];
            direction = directionFromEnum.toString().toLowerCase();
            counterForEntityMovement = 0;
        }
    }

    public void setDialouge() {
        dialogues[0] = "HI!! How can i help you?";
        dialogues[1] = "You seem lost. I'll explain you everything.\nYou're here in our little world.";
    }

    public void speak(){
        super.speak();
    }

}
