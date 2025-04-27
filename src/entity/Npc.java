package entity;

import main.GamePanel;

import java.util.Random;

public class Npc extends Entity {

    public Npc(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 2;
        getNpcImage();
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
     * The simplest AI movement. Every 2 seconds direction on a npc randomly changes.
     */
    public void action(){
        counterForEntityMovement++;

        if(counterForEntityMovement == 120){
            Random rd = new Random();
            int index = rd.nextInt(4);
            Directions directionFromEnum = Directions.values()[index];
            direction = directionFromEnum.toString().toLowerCase();
            counterForEntityMovement = 0;
        }
    }
}
