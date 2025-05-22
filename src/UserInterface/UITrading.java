package UserInterface;

import entity.Entity;
import main.GamePanel;
import main.GameState;

import java.awt.*;

public class UITrading {
    GamePanel gp;
    UI ui;
    public String[] values = new String[4];
    public int x, y, width, height;
    boolean firstTime = true;

    public UITrading(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    /**
     * Selects what player wants to do.
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
                ui.ut.goToPlayState("See you nex time!");
                gp.keyH.enterPressed = false;
            }
        }
    }

    /**
     * Logic of buying items
     */
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
                        ui.ut.goToPlayState("You don't have enough money");
                        //ui.drawDialogueScreen();
                    } else {
                        if (gp.player.canStackItem(ui.villager.inventory.get(indexItem))) {
                            ui.g2.setFont(ui.g2.getFont().deriveFont(Font.PLAIN, 25));
                            gp.player.coins -= ui.villager.inventory.get(indexItem).price;
                        } else {
                            ui.ut.goToPlayState("You don't have enough room in\nyour inventory");
                        }
                    }
                } else {
                    ui.ut.goToPlayState("You can't do that");
                }
                gp.keyH.enterPressed = false;
            }
        }
    }

    /**
     * If player hit the sell button, this method chooses which 'method of selling' is going to be played,
     * Based on villagers' type.
     */
    public void tradeSell() {
        ui.drawInventory(gp.player, true);
        ui.g2.setFont(ui.g2.getFont().deriveFont(Font.PLAIN, 25));

        ui.ut.drawHintWindow();

        if (ui.villager.typeOfVillager.equals("Builder")) {
            if (firstTime) {
                ui.villager.setKeySet();
                firstTime = false;
            }
            ui.villager.builderSell();
        } else {
            normalSell();
        }
    }

    /**
     * Prints keySet on a screen
     */
    public void soutKeySet() {
        for (Entity key : ui.villager.requireForHouse.keySet()) {
            gp.ui.uiTrading.y += gp.tileSize;
            ui.villager.drawBiggerMaterial = true;
            gp.ui.g2.drawImage(key.down2, gp.ui.uiTrading.x + gp.tileSize, gp.ui.uiTrading.y, null);
            gp.ui.uiTrading.values = ui.villager.requireForHouse.get(key);
            switch (key.name) {
                case "log":
                    gp.ui.g2.drawString(ui.villager.curValLog + " X", gp.ui.uiTrading.x + (3 * gp.tileSize),
                            gp.ui.uiTrading.y + gp.tileSize / 2);
                    break;
                case "wheat":
                    gp.ui.g2.drawString(ui.villager.curValWheat + " X", gp.ui.uiTrading.x + (3 * gp.tileSize),
                            gp.ui.uiTrading.y + gp.tileSize / 2);
                    break;
                case "clay":
                    gp.ui.g2.drawString(ui.villager.curValClay + " X", gp.ui.uiTrading.x + (3 * gp.tileSize),
                            gp.ui.uiTrading.y + gp.tileSize / 2);
                    break;
                case "stone":
                    gp.ui.g2.drawString(ui.villager.curValStone + " X", gp.ui.uiTrading.x + (3 * gp.tileSize),
                            gp.ui.uiTrading.y + gp.tileSize / 2);
                    break;
            }
        }
        ui.villager.drawBiggerMaterial = false;
    }

    /**
     * Selling process of all the villagers accept the builder.
     */
    public void normalSell() {
        //draw a price window
        int indexItem = ui.ut.getItemIndexInInventory(ui.slotColPlayer, ui.slotRowPlayer);

        int sellingPrice = 0;
        if (indexItem < gp.player.inventory.size()) {
            int x = (int) (gp.tileSize * 16.5);
            int y = (int) (gp.tileSize * 5.5);
            ui.ut.drawPopUpWindow(x, y, (int) (gp.tileSize * 2.5), (int) (gp.tileSize * 1.5));
            ui.g2.drawImage(ui.coin, x + 10, y + 16, 32, 32, null);
            sellingPrice = gp.player.inventory.get(indexItem).price - (gp.player.inventory.get(indexItem).price / 3);
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

}
