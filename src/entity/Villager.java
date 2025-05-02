package entity;

import main.GamePanel;
import object.SuperObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Villager extends Entity {
    public HashMap<Integer, String> trades = new HashMap<>();

    public Villager(GamePanel gp, VillagerType type) {
        super(gp);
        direction = "left";
        speed = 2;
        String typeOfVillager = utilityTool.firstLetterUppercase(type.toString());
        getVillagerImage(typeOfVillager);
        //makeTrades(typeOfVillager);
    }


    public void getVillagerImage(String typeOfVillager) {
        up1 = setUpImage("/NPC/back1" + typeOfVillager + "Villager");
        up2 = setUpImage("/NPC/back2" + typeOfVillager + "Villager");
        down1 = setUpImage("/NPC/front1" + typeOfVillager + "Villager");
        down2 = setUpImage("/NPC/front2" + typeOfVillager + "Villager");
        left1 = setUpImage("/NPC/left1" + typeOfVillager + "Villager");
        left2 = setUpImage("/NPC/left2" + typeOfVillager + "Villager");
        right1 = setUpImage("/NPC/right1" + typeOfVillager + "Villager");
        right2 = setUpImage("/NPC/right2" + typeOfVillager + "Villager");
    }

    //idk jestli funguje
    public void makeTrades(String typeOfVillager) {
        int wantedNumberOnCounter = -1;
        wantedNumberOnCounter = switch (typeOfVillager) {
            case "Wood" -> 0;
            case "Builder" -> 1;
            case "Smith" -> 2;
            default -> wantedNumberOnCounter;
        };
        if (wantedNumberOnCounter != -1) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("Trades.txt"));
                String text;
                int counter = -1;
                while ((text = br.readLine()) != null) {
                    counter++;
                    if (counter == wantedNumberOnCounter) {
                        String[] line = text.split(",");
                        String[] oneTrade;
                        for (String s : line) {
                            oneTrade = s.split(":");
                            trades.put(Integer.parseInt(oneTrade[0]), oneTrade[1]);
                        }
                    }
                }
            } catch (IOException i) {
                System.out.println("IO Exception");
            }
        }
    }

    public void setDialogue(String typeOfVillager) {
        switch (typeOfVillager) {
            case "Wood" -> dialogues[0] = "Wood\n";
            case "Builder" -> dialogues[0] = "Builder\n";
            case "Smith" -> dialogues[0] = "Smith\n";
        }
    }

    public void speak() {
        super.speak();
    }
}
