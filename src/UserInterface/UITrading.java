package UserInterface;

import entity.Entity;
import main.GamePanel;
import main.GameState;
import main.ItemType;

import java.awt.*;

public class UITrading {
    GamePanel gp;
    UI ui;

    public UITrading(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    /**
     * Select what player wants to do.
     */
    public void tradeSelectItem() {
        ui.drawDialogueScreen();

        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = gp.tileSize * 4;
        ui.ut.drawPopUpWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        ui.g2.drawString("Buy", x, y);
        if (ui.commandNum == 0) {
            ui.g2.drawString("*", x - 20, y + 5);
            if (gp.keyH.enterPressed) {
                ui.tradingState = 1;
                gp.keyH.enterPressed = false;
            }
        }
        y += gp.tileSize;
        ui.g2.drawString("Sell", x, y);
        if (ui.commandNum == 1) {
            ui.g2.drawString("*", x - 20, y + 5);
            if (gp.keyH.enterPressed) {
                ui.tradingState = 2;
                gp.keyH.enterPressed = false;
            }
        }
        y += gp.tileSize;
        ui.g2.drawString("Back", x, y);
        if (ui.commandNum == 2) {
            ui.g2.drawString("*", x - 20, y + 5);
            if (gp.keyH.enterPressed) {
                gp.gameState = GameState.PLAYING;
                ui.commandNum = 0;
                ui.currentDialogue = "See you nex time!";
                gp.keyH.enterPressed = false;
            }
        }
    }

    public void tradeBuy() {
        ui.g2.setFont(ui.g2.getFont().deriveFont(Font.PLAIN, 25));
        ui.drawInventory(gp.player, false);
        ui.drawInventory(ui.villager, true);

        //hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        ui.ut.drawPopUpWindow(x, y, width, height);
        ui.g2.drawString("[ESC] -> back", x + 24, y + 60);

        x = gp.tileSize * 12;
        ui.ut.drawPopUpWindow(x, y, width, height);
        ui.g2.drawString("Coins: " + gp.player.coins, x + 24, y + 60);

        //draw a price window
        int indexItem = ui.ut.getItemIndexInInventory(ui.slotColVil, ui.slotRowVil);
        if (indexItem < ui.villager.inventory.size()) {

            x = (int) (gp.tileSize * 6.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = (int) (gp.tileSize * 1.5);
            ui.ut.drawPopUpWindow(x, y, width, height);
            ui.g2.drawImage(ui.coin, x + 10, y + 16, 32, 32, null);

            String price = String.valueOf(ui.villager.inventory.get(indexItem).price);
            x += gp.tileSize;
            ui.g2.drawString(price, x, y + (gp.tileSize - 5));

            if (gp.keyH.enterPressed) {
                if (ui.villager.inventory.get(indexItem) != null) {
                    if (ui.villager.inventory.get(indexItem).price > gp.player.coins) {
                        ui.tradingState = 0;
                        ui.commandNum = 0;
                        gp.gameState = GameState.DIALOGUE;
                        ui.currentDialogue = "You don't have enough money";
                        ui.drawDialogueScreen();
                    } else {
                        if (gp.player.canStackItem(ui.villager.inventory.get(indexItem))) {
                            ui.g2.setFont(ui.g2.getFont().deriveFont(Font.PLAIN, 25));
                            gp.player.coins -= ui.villager.inventory.get(indexItem).price;
                        } else {
                            ui.tradingState = 0;
                            ui.commandNum = 0;
                            gp.gameState = GameState.DIALOGUE;
                            ui.currentDialogue = "You don't have enough room in\nyour inventory";
                        }
                    }
                } else {
                    ui.tradingState = 0;
                    ui.commandNum = 0;
                    gp.gameState = GameState.DIALOGUE;
                    ui.currentDialogue = "You can't do that";
                }
                gp.keyH.enterPressed = false;
            }
        }
    }

    public void tradeSell() {
        ui.drawInventory(gp.player, true);
        ui.g2.setFont(ui.g2.getFont().deriveFont(Font.PLAIN, 25));

        ui.ut.drawHintWindow();

        if (ui.villager.typeOfVillager.equals("Builder")) {
            builderSell();
        } else {
            normalSell();
        }
    }

    public void normalSell() {
        //draw a price window
        int indexItem = ui.ut.getItemIndexInInventory(ui.slotColPlayer, ui.slotRowPlayer);

        int sellingPrice = 0;
        if (indexItem < gp.player.inventory.size()) {
            int x = (int) (gp.tileSize * 16.5);
            int y = (int) (gp.tileSize * 5.5);
            ui.ut.drawPopUpWindow(x, y, (int) (gp.tileSize * 2.5), (int) (gp.tileSize * 1.5));
            ui.g2.drawImage(ui.coin, x + 10, y + 16, 32, 32, null);
            sellingPrice = (int) (gp.player.inventory.get(indexItem).price - (gp.player.inventory.get(indexItem).price / 3));
            String price = String.valueOf(sellingPrice);
            x += gp.tileSize;
            ui.g2.setFont(ui.g2.getFont().deriveFont(Font.PLAIN, 25));
            ui.g2.drawString(price, x, y + (gp.tileSize - 15));
        }

        if (gp.keyH.cPressed) {
            try {
                if (gp.player.inventory.get(indexItem) != null) {
                    if (gp.player.inventory.get(indexItem) == gp.player.currentTool) {
                        ui.commandNum = 0;
                        ui.tradingState = 0;
                        gp.gameState = GameState.DIALOGUE;
                        ui.currentDialogue = "You can't sell your current\nweapon";
                    } else {
                        if (gp.player.inventory.get(indexItem).howManyOfItem > 1) {
                            gp.player.inventory.get(indexItem).howManyOfItem--;
                        } else {
                            gp.player.inventory.remove(indexItem);
                        }
                        gp.player.coins += sellingPrice;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                ui.commandNum = 0;
                ui.tradingState = 0;
                gp.gameState = GameState.DIALOGUE;
                ui.currentDialogue = "You can't sell nothing.";
            }
            gp.keyH.cPressed = false;
        }
    }

    public void builderSell() {
        String[] values = new String[4];
        int x = gp.tileSize * 2;
        int y = gp.tileSize;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 5;
        ui.ut.drawPopUpWindow(x, y, width, height);
        y -= gp.tileSize / 2;

        for (Entity key : ui.villager.requireForHouse.keySet()) {
            y += gp.tileSize;
            ui.villager.drawBiggerMaterial = true;
            ui.g2.drawImage(key.down2, x + gp.tileSize, y, null);
            values = ui.villager.requireForHouse.get(key);
            switch (key.name) {
                case "log":
                    ui.villager.curValLog = Integer.parseInt(values[ui.villager.indexInArray]);
                    ui.g2.drawString(ui.villager.curValLog + " X", x + (3 * gp.tileSize), y + gp.tileSize / 2);
                    break;
                case "wheat":
                    ui.villager.curValWheat = Integer.parseInt(values[ui.villager.indexInArray]);
                    ui.g2.drawString(ui.villager.curValWheat + " X", x + (3 * gp.tileSize), y + gp.tileSize / 2);
                    break;
                case "clay":
                    ui.villager.curValClay = Integer.parseInt(values[ui.villager.indexInArray]);
                    ui.g2.drawString(ui.villager.curValClay + " X", x + (3 * gp.tileSize), y + gp.tileSize / 2);
                    break;
                case "stone":
                    ui.villager.curValStone = Integer.parseInt(values[ui.villager.indexInArray]);
                    ui.g2.drawString(ui.villager.curValStone + " X", x + (3 * gp.tileSize), y + gp.tileSize / 2);
                    break;
            }
        }
        ui.villager.drawBiggerMaterial = false;

        if (gp.keyH.cPressed) {
            int indexItem = ui.ut.getItemIndexInInventory(ui.slotColPlayer, ui.slotRowPlayer);
            try {
                if (gp.player.inventory.get(indexItem) != null) {
                    if (gp.player.inventory.get(indexItem) == gp.player.currentTool) {
                        ui.commandNum = 0;
                        ui.tradingState = 0;
                        gp.gameState = GameState.DIALOGUE;
                        ui.currentDialogue = "You can't sell your current\nweapon";
                    } else {
                        if (gp.player.inventory.get(indexItem).typeOfItem.equals(ItemType.MATERIAL)) {
                            if (gp.player.inventory.get(indexItem).howManyOfItem <= Integer.parseInt(values[ui.villager.indexInArray])) {
                                switch (gp.player.inventory.get(indexItem).name) {
                                    case "log": {
                                        ui.villager.curValLog -= gp.player.inventory.get(indexItem).howManyOfItem;
                                        if (ui.villager.curValLog == 0) ui.villager.indexInArray++;
                                        break;
                                    }
                                    case "wheat":
                                        ui.villager.curValWheat -= gp.player.inventory.get(indexItem).howManyOfItem;
                                        if (ui.villager.curValWheat == 0) ui.villager.indexInArray++;
                                        break;
                                    case "clay":
                                        ui.villager.curValClay -= gp.player.inventory.get(indexItem).howManyOfItem;
                                        if (ui.villager.curValClay == 0) ui.villager.indexInArray++;
                                        break;
                                    case "stone":
                                        ui.villager.curValStone -= gp.player.inventory.get(indexItem).howManyOfItem;
                                        if (ui.villager.curValStone == 0) ui.villager.indexInArray++;
                                        break;
                                }
                                gp.player.inventory.remove(indexItem);
                            } else {
                                switch (gp.player.inventory.get(indexItem).name) {
                                    case "log":
                                        gp.player.inventory.get(indexItem).howManyOfItem -= ui.villager.curValLog;
                                        break;
                                    case "wheat":
                                        gp.player.inventory.get(indexItem).howManyOfItem -= ui.villager.curValWheat;
                                        break;
                                    case "clay":
                                        gp.player.inventory.get(indexItem).howManyOfItem -= ui.villager.curValClay;
                                        break;
                                    case "stone":
                                        gp.player.inventory.get(indexItem).howManyOfItem -= ui.villager.curValStone;
                                        break;
                                }
                                ui.villager.indexInArray++;
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Kurva uz");
                /*ui.commandNum = 0;
                ui.tradingState = 0;
                gp.gameState = GameState.DIALOGUE;
                ui.currentDialogue = "You can't sell nothing.";*/
            }
            gp.keyH.cPressed = false;
        }
    }
}
