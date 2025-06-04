package entity;

import main.GamePanel;
import main.GameState;
import main.ItemType;
import object.*;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Villager extends Entity {
    public HashMap<Entity, String[]> requireForHouse = new HashMap<>();
    public int indexInArray = 0;
    public int curValLog, curValWheat, curValStone, curValClay;
    public boolean isDoneLog, isDoneWheat, isDoneStone, isDoneClay = false;

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

    /**
     * Sets dialogue based on villagers' type
     * @param typeOfVillager type of villager
     */
    public void setDialogue(String typeOfVillager) {
        switch (typeOfVillager) {
            case "Seller" ->
                    dialogues[0] = "Hi! I'm your new best friend!\nI can sell you any material\nyou could need!";
            case "Builder" ->
                    dialogues[0] = "If you have all the materials,\ncome to me and we can build\nthis village together!";
            case "Smith" -> dialogues[0] = "Mining takes long time doesn't it?\n Well I can help you with that!";
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

    /**
     * Sets items a villager is selling/player can buy from him.
     *
     * @param typeOfVillager Type of villager (Seller, Builder, Smith)
     */
    public void setItems(String typeOfVillager) {
        switch (typeOfVillager) {
            case "Seller", "Builder": {
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

    /**
     * Sets values in a Hashmap. Could've been solved better.
     */
    public void valuesForHouse() {
        try {
            InputStream is = getClass().getResourceAsStream("/files/valuesForHouse.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            String line;
            Entity[] objects = new Entity[4];
            objects[0] = new ObjLog(gp);
            objects[1] = new ObjClay(gp);
            objects[2] = new ObjStone(gp);
            objects[3] = new ObjWheat(gp);
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] text = line.split(",");
                requireForHouse.put(objects[counter], text);
                counter++;
            }
        } catch (IOException i) {
            System.err.println(i.getMessage());
        }
    }

    /**
     * Sets 'prizes' of houses.
     */
    public void setKeySet() {
        for (Entity key : requireForHouse.keySet()) {
            gp.ui.uiTrading.values = requireForHouse.get(key);
            switch (key.name) {
                case "log":
                    curValLog = Integer.parseInt(gp.ui.uiTrading.values[indexInArray]);
                    break;
                case "wheat":
                    curValWheat = Integer.parseInt(gp.ui.uiTrading.values[indexInArray]);
                    break;
                case "clay":
                    curValClay = Integer.parseInt(gp.ui.uiTrading.values[indexInArray]);
                    break;
                case "stone":
                    curValStone = Integer.parseInt(gp.ui.uiTrading.values[indexInArray]);
                    break;
            }
        }
    }

    /**
     * Logic of giving items to builder. This method is messy, but it works.
     */
    public void builderSell() {
        gp.ui.uiTrading.x = gp.tileSize * 2;
        gp.ui.uiTrading.y = gp.tileSize;
        gp.ui.uiTrading.width = gp.tileSize * 6;
        gp.ui.uiTrading.height = gp.tileSize * 5;
        gp.ui.ut.drawPopUpWindow(gp.ui.uiTrading.x, gp.ui.uiTrading.y, gp.ui.uiTrading.width, gp.ui.uiTrading.height);
        gp.ui.uiTrading.y -= gp.tileSize / 2;

        gp.ui.uiTrading.soutKeySet();

        if (gp.keyH.cPressed) {
            int indexItem = gp.ui.ut.getItemIndexInInventory(gp.ui.slotColPlayer, gp.ui.slotRowPlayer);
            try {
                if (gp.player.inventory.get(indexItem) != null) {
                    if (gp.player.inventory.get(indexItem) == gp.player.currentTool) {
                        gp.ui.ut.goToPlayState("You can't sell your current\nweapon");
                    } else {
                        if (gp.player.inventory.get(indexItem).typeOfItem.equals(ItemType.MATERIAL)) {
                            switch (gp.player.inventory.get(indexItem).name) {
                                case "log": {
                                    if (gp.ui.villager.curValLog <= 0 ||
                                            (gp.ui.villager.curValLog - gp.player.inventory.get(indexItem).howManyOfItem <= 0)) {
                                        gp.player.inventory.get(indexItem).howManyOfItem -= gp.ui.villager.curValLog;
                                        isDoneLog = true;
                                        gp.ui.villager.curValLog = 0;
                                        break;
                                    }
                                    gp.ui.villager.curValLog -= gp.player.inventory.get(indexItem).howManyOfItem;
                                    gp.player.inventory.remove(indexItem);
                                    break;
                                }
                                case "wheat":
                                    if (gp.ui.villager.curValWheat <= 0 ||
                                            (gp.ui.villager.curValWheat - gp.player.inventory.get(indexItem).howManyOfItem <= 0)) {
                                        gp.player.inventory.get(indexItem).howManyOfItem -= gp.ui.villager.curValWheat;
                                        isDoneWheat = true;
                                        gp.ui.villager.curValWheat = 0;
                                        break;
                                    }
                                    gp.ui.villager.curValWheat -= gp.player.inventory.get(indexItem).howManyOfItem;
                                    gp.player.inventory.remove(indexItem);
                                    break;
                                case "clay":
                                    if (gp.ui.villager.curValClay <= 0 ||
                                            (gp.ui.villager.curValClay - gp.player.inventory.get(indexItem).howManyOfItem <= 0)) {
                                        gp.player.inventory.get(indexItem).howManyOfItem -= gp.ui.villager.curValClay;
                                        isDoneClay = true;
                                        gp.ui.villager.curValClay = 0;
                                        break;
                                    }
                                    gp.ui.villager.curValClay -= gp.player.inventory.get(indexItem).howManyOfItem;
                                    gp.player.inventory.remove(indexItem);
                                    break;
                                case "stone":
                                    if (gp.ui.villager.curValStone <= 0 ||
                                            (gp.ui.villager.curValStone - gp.player.inventory.get(indexItem).howManyOfItem <= 0)) {
                                        gp.player.inventory.get(indexItem).howManyOfItem -= gp.ui.villager.curValStone;
                                        isDoneStone = true;
                                        gp.ui.villager.curValStone = 0;
                                        break;
                                    }
                                    gp.ui.villager.curValStone -= gp.player.inventory.get(indexItem).howManyOfItem;
                                    gp.player.inventory.remove(indexItem);

                                    break;
                            }
                            if (isDoneLog && isDoneWheat && isDoneClay && isDoneStone) {
                                gp.plSetter.placeHouses(indexInArray);
                                gp.playSoundEffect(4);
                                gp.ui.villager.indexInArray++;
                                gp.ui.villager.setKeySet();
                                isDoneLog = false;
                                isDoneWheat = false;
                                isDoneStone = false;
                                isDoneClay = false;
                            }
                        } else {
                            gp.ui.ut.goToPlayState("You can't build house\nfrom this");
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                gp.ui.ut.goToPlayState("You can't sell nothing.");
            }
            gp.keyH.cPressed = false;
        }
    }
}
