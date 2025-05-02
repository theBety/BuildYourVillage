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
        up1 = setUpImage("/NPC/back1NPC");
        up2 = setUpImage("/NPC/back2NPC");
        down1 = setUpImage("/NPC/front1NPC");
        down2 = setUpImage("/NPC/front2NPC");
        left1 = setUpImage("/NPC/leftStandNPC");
        left2 = setUpImage("/NPC/leftStand2NPC");
        right1 = setUpImage("/NPC/rightStandNPC");
        right2 = setUpImage("/NPC/rightStand2NPC");
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
