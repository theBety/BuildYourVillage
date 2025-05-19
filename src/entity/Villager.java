package entity;

import main.GamePanel;
import main.GameState;
import object.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Villager extends Entity {

    public Villager(GamePanel gp, VillagerType type) {
        super(gp);
        direction = "left";
        speed = 2;
        typeOfVillager = utilityTool.firstLetterUppercase(type.toString());
        getVillagerImage(typeOfVillager);
        setDialogue(typeOfVillager);
        setItems(typeOfVillager);
        valuesForHouse();
    }


    public void getVillagerImage(String typeOfVillager) {
        up1 = setUpImage("/NPC/back1" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        up2 = setUpImage("/NPC/back2" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        down1 = setUpImage("/NPC/front1" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        down2 = setUpImage("/NPC/front2" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        left1 = setUpImage("/NPC/left1" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        left2 = setUpImage("/NPC/left2" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        right1 = setUpImage("/NPC/right1" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
        right2 = setUpImage("/NPC/right2" + typeOfVillager + "Villager", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(String typeOfVillager) {
        switch (typeOfVillager) {
            case "Seller" ->
                    dialogues[0] = "Hi! I'm your new best friend!\nI can sell you any material\nyou could need!";
            case "Builder" ->
                    dialogues[0] = "If you have all the materials,\ncome to me and we can build\nthis village together!";
            case "Smith" -> dialogues[0] = "Mining takes long time doesn't it?\n Well i can help you with that!";
        }
    }

    public void speak() {
        super.speak();
        gp.gameState = GameState.TRADING;
        gp.ui.villager = this;
    }

    public void action() {
        counterForEntityMovement++;

        if (counterForEntityMovement == 90) {
            Random rd = new Random();
            int index = rd.nextInt(4);
            Directions directionFromEnum = Directions.values()[index];
            direction = directionFromEnum.toString().toLowerCase();
            counterForEntityMovement = 0;
        }
    }

    public void setItems(String typeOfVillager) {
        switch (typeOfVillager) {
            case "Seller": {
                inventory.clear();
                inventory.add(new ObjWheat(gp));
                inventory.add(new ObjClay(gp));
                inventory.add(new ObjStone(gp));
                inventory.add(new ObjLog(gp));
                inventory.add(new ObjCoin(gp));
                break;
            }
            case "Builder": {
                inventory.clear();
                inventory.add(new ObjWheat(gp));
                inventory.add(new ObjClay(gp));
                inventory.add(new ObjStone(gp));
                inventory.add(new ObjLog(gp));
                break;
            }
            case "Smith": {
                inventory.clear();
                inventory.add(new ToolPicaxe(gp, 1));
                inventory.add(new ToolPicaxe(gp, 2));
                inventory.add(new ToolPicaxe(gp, 3));

                inventory.add(new ToolHoe(gp, 1));
                inventory.add(new ToolHoe(gp, 2));
                inventory.add(new ToolHoe(gp, 3));

                inventory.add(new ToolAxe(gp, 1));
                inventory.add(new ToolAxe(gp, 2));
                inventory.add(new ToolAxe(gp, 3));
            }
        }
    }

    public void valuesForHouse() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("valuesForHouse.txt"));
            String line;
            int[] finalValues = new int[4];
            Entity[] objects = new Entity[4];
            objects[0] = new ObjLog(gp);
            objects[1] = new ObjClay(gp);
            objects[2] = new ObjStone(gp);
            objects[3] = new ObjWheat(gp);
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] text = line.split(",");
                for (int i = 0; i < text.length; i++) {
                    finalValues[i] = Integer.parseInt(text[i]);
                }
                requireForHouse.put(objects[counter], finalValues);
                counter++;
            }
        } catch (IOException i) {
            System.err.println(i.getMessage());
        }
    }
}
