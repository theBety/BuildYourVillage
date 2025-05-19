package main;

import entity.Entity;
import entity.VillagerType;
import object.ObjCoin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font tNewRoman;
    public boolean messageOn = false;
    public String currentDialogue = "";
    Font bookMan;
    public int commandNum = 0;
    public int slotColPlayer = 0;
    public int slotRowPlayer = 0;
    public int slotColVil = 0;
    public int slotRowVil = 0;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public Entity villager;
    int tradingState = 0;
    BufferedImage coin;

    //for now, unused things.
    public boolean endGame = false;
    public int SettingsInTitleScreen = 0;
    double playTime;
    DecimalFormat formatTime = new DecimalFormat("#0.0");

    public UI(GamePanel gp) {
        this.gp = gp;
        tNewRoman = new Font("TimesRoman", Font.PLAIN, 30);
        bookMan = new Font("Bookman Old Style", Font.PLAIN, 30);
        Entity coinE = new ObjCoin(gp);
        coin = coinE.down1;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(tNewRoman);
        g2.setColor(Color.WHITE);

        switch (gp.gameState) {
            case PLAYING:
                drawMessages();
                break;
            case PAUSED:
                drawPauseScreen();
                break;
            case DIALOGUE:
                gp.player.exp++;
                drawDialogueScreen();
                break;
            case TITLE:
                drawTitleScreen();
                break;
            case TUTORIAL:
                drawHowToPlayScreen();
                break;
            case CHARACTER:
                drawInventory(gp.player, true);
                drawInfoScreen();
                break;
            case SETTINGS:
                drawSettingsScreen();
                break;
            case TRADING:
                drawTradeScreen();
        }

    }

    public void addMessage(String text) {
        messages.add(text);
        messageCounter.add(0);
    }

    /**
     * Draw messages on a screen.
     */
    public void drawMessages() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) != null) {

                g2.setColor(Color.white);
                g2.drawString(messages.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter); //replaces i with counter
                messageY += 50;
                //so the message disappears after 3 seconds
                if (messageCounter.get(i) >= 180) {
                    messages.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawPauseScreen() {
        String text = "PAUSED GAME";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g2.setColor(new Color(126, 42, 83, 255));
        int x = xForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    /**
     * Draw dialogues. The window and the text
     */
    public void drawDialogueScreen() {
        int x = gp.tileSize * 3;
        int y = gp.tileSize;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;
        drawPopUpWindow(x, y, width, height);

        //These x,y are moves so the text doesn't start at the very edge.
        g2.setColor(new Color(126, 42, 83, 255));
        g2.setFont(bookMan);
        x += gp.tileSize / 2;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    /**
     * Draws title screen.
     */
    public void drawTitleScreen() {
        g2.setColor(new Color(202, 153, 171));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setColor(new Color(65, 21, 40));

        g2.setFont(bookMan.deriveFont(Font.BOLD, 70));
        String text = "BUILD YOUR VILLAGE";
        int x = xForCenteredText(text);
        int y = gp.screenHeight / 4;
        g2.drawString(text, x, y);

        g2.setFont(bookMan.deriveFont(Font.BOLD, 35));
        text = "START";
        x = xForCenteredText(text);
        y += gp.tileSize * 5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString("*", x - gp.tileSize, y);
        }

        y += gp.tileSize * 2;
        text = "HOW TO PLAY";
        x = xForCenteredText(text);
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString("*", x - gp.tileSize, y);
        }

        y -= gp.tileSize * 6;
        x = (gp.screenWidth / 2) - gp.tileSize;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    /**
     * Draws screen after user hits "how to play" button.
     */
    public void drawHowToPlayScreen() {
        g2.setColor(new Color(202, 153, 171));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setColor(new Color(65, 21, 40));

        g2.setFont(bookMan.deriveFont(Font.BOLD, 30));
        try {
            BufferedReader br = new BufferedReader(new FileReader("howToPlay.txt"));
            int helpInt = 1;
            int x;
            int y;
            String text;
            while ((text = br.readLine()) != null) {
                if (helpInt == 5) {
                    helpInt += 1;
                    y = gp.tileSize * helpInt;
                    x = gp.tileSize;
                } else if (helpInt >= 5) {
                    y = gp.tileSize * helpInt;
                    x = xForCenteredText(text);
                } else {
                    y = gp.tileSize * helpInt;
                    x = gp.tileSize;
                }
                g2.drawString(text, x, y);
                helpInt++;
            }
            g2.setFont(bookMan.deriveFont(Font.BOLD, 35));
            text = "* PLAY";
            x = gp.screenWidth - gp.tileSize * 3;
            y = gp.tileSize * helpInt;
            g2.drawString(text, x, y);
        } catch (IOException i) {
            System.err.println("IO Exception help");
        }
    }

    /**
     * Draws players' inventory including items and cursor.
     */
    public void drawInventory(Entity entity, boolean cursor) {
        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if (entity == gp.player) {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = slotColPlayer;
            slotRow = slotRowPlayer;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = slotColVil;
            slotRow = slotRowVil;
        }

        drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        if (cursor) {
            int cursorX = slotXStart + (gp.tileSize * slotCol);
            int cursorY = slotYStart + (gp.tileSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;
            g2.setColor(new Color(80, 42, 80));
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            int desFrameY = frameY + frameHeight + gp.tileSize / 2;
            int desFrameHeight = gp.tileSize * 3;

            int textX = frameX + 20;
            int textY = desFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));
            int indexOfItem = getItemIndexInInventory(slotCol, slotRow);

            if (indexOfItem < entity.inventory.size()) {
                drawPopUpWindow(frameX, desFrameY, frameWidth, desFrameHeight);
                for (String line : entity.inventory.get(indexOfItem).descriptionOfItem.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += gp.tileSize / 2;
                }
            }
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
        }

        for (int i = 0; i < entity.inventory.size(); i++) {
            if (entity.inventory.get(i) == entity.currentTool) {
                g2.setColor(new Color(162, 76, 97));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            //display amount of item
            if (entity == gp.player && entity.inventory.get(i).howManyOfItem > 1) {

                g2.setFont(g2.getFont().deriveFont(30F));
                int howManyX;
                int howManyY;
                String howManyString = Integer.toString(entity.inventory.get(i).howManyOfItem);
                howManyX = slotX + 35;
                howManyY = slotY + gp.tileSize;
                g2.setColor(new Color(245, 188, 193));
                g2.drawString(howManyString, howManyX, howManyY);
                g2.setColor(new Color(63, 10, 41));
                g2.drawString(howManyString, howManyX - 3, howManyY - 3);
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
            }

            slotX += gp.tileSize;
            if (i == 4 || i == 9 || i == 14) {
                slotY += gp.tileSize;
                slotX = slotXStart;
            }
        }
    }

    public void drawInfoScreen() {
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 5;
        int frameHeight = gp.tileSize * 7;
        drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);
        int yForText = frameY + gp.tileSize;
        int xFortext = gp.tileSize + 20;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        g2.drawString("STATISTICS", frameX + 38, yForText);
        yForText += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        g2.drawString("Level: " + gp.player.level, xFortext, yForText);
        yForText += gp.tileSize;
        g2.drawString("Coins: " + gp.player.coins, xFortext, yForText);
        yForText += gp.tileSize;
        g2.drawString("Strength: " + gp.player.strength, xFortext, yForText);
        yForText += gp.tileSize;
        g2.drawString("Current tool: ", xFortext, yForText);
        g2.drawImage(gp.player.currentTool.down1, xFortext + (gp.tileSize * 3) + 10, yForText - gp.tileSize / 2, null);
    }

    public void drawSettingsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

        String text = "SETTINGS";
        int x = xForCenteredText(text);
        int y = gp.tileSize * 2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));

        y += gp.tileSize + gp.tileSize / 2;
        x = frameX + gp.tileSize;
        text = "Music";
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString("*", x - 20, y + 5);
        }
        y += gp.tileSize + gp.tileSize / 2;
        text = "Sound Effects";
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString("*", x - 20, y + 5);
        }
        y += gp.tileSize + gp.tileSize / 2;
        text = "Controls";
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString("*", x - 20, y + 5);
        }
        y += gp.tileSize + gp.tileSize / 2;
        text = "End game";
        g2.drawString(text, x, y);
        if (commandNum == 3) {
            g2.drawString("*", x - 20, y + 5);
        }
        y += gp.tileSize * 2;
        text = "Back";
        g2.drawString(text, x, y);
        if (commandNum == 4) {
            g2.drawString("*", x - 20, y + 5);
        }

        x = frameX + gp.tileSize * 5;
        y = frameY + gp.tileSize * 2;
        g2.drawRect(x, y, 120, 24); //120/11 = 10.9
        int volumeWidth = (int) 10.9 * gp.soundMusic.volumeScale;
        g2.fillRect(x, y, volumeWidth, 24);

        y += gp.tileSize + gp.tileSize / 2;
        g2.drawRect(x, y, 120, 24);
        volumeWidth = (int) 10.9 * gp.soundEffects.volumeScale;
        g2.fillRect(x, y, volumeWidth, 24);
    }

    public void drawTradeScreen() {
        switch (tradingState) {
            case 0:
                tradeSelectItem();
                break;
            case 1:
                tradeBuy();
                break;
            case 2:
                tradeSell();
                break;
        }
    }

    /**
     * Select what player wants to do.
     */
    public void tradeSelectItem() {
        drawDialogueScreen();

        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = gp.tileSize * 4;
        drawPopUpWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString("*", x - 20, y + 5);
            if (gp.keyH.enterPressed) {
                tradingState = 1;
                gp.keyH.enterPressed = false;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString("*", x - 20, y + 5);
            if (gp.keyH.enterPressed) {
                tradingState = 2;
                gp.keyH.enterPressed = false;
            }
        }
        y += gp.tileSize;
        g2.drawString("Back", x, y);
        if (commandNum == 2) {
            g2.drawString("*", x - 20, y + 5);
            if (gp.keyH.enterPressed) {
                gp.gameState = GameState.PLAYING;
                commandNum = 0;
                currentDialogue = "See you nex time!";
                gp.keyH.enterPressed = false;
            }
        }
    }

    public void tradeBuy() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
        drawInventory(gp.player, false);
        drawInventory(villager, true);

        //hint window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawPopUpWindow(x, y, width, height);
        g2.drawString("[ESC] -> back", x + 24, y + 60);

        x = gp.tileSize * 12;
        drawPopUpWindow(x, y, width, height);
        g2.drawString("Coins: " + gp.player.coins, x + 24, y + 60);

        //draw a price window
        int indexItem = getItemIndexInInventory(slotColVil, slotRowVil);
        if (indexItem < villager.inventory.size()) {

            x = (int) (gp.tileSize * 6.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = (int) (gp.tileSize * 1.5);
            drawPopUpWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 16, 32, 32, null);

            String price = String.valueOf(villager.inventory.get(indexItem).price);
            x += gp.tileSize;
            g2.drawString(price, x, y + (gp.tileSize - 5));

            if (gp.keyH.enterPressed) {
                if (villager.inventory.get(indexItem) != null) {
                    if (villager.inventory.get(indexItem).price > gp.player.coins) {
                        tradingState = 0;
                        commandNum = 0;
                        gp.gameState = GameState.DIALOGUE;
                        currentDialogue = "You don't have enough money";
                        drawDialogueScreen();
                    } else {
                        if (gp.player.canStackItem(villager.inventory.get(indexItem))) {
                            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
                            gp.player.coins -= villager.inventory.get(indexItem).price;
                        } else {
                            tradingState = 0;
                            commandNum = 0;
                            gp.gameState = GameState.DIALOGUE;
                            currentDialogue = "You don't have enough room in\nyour inventory";
                        }
                    }
                } else {
                    tradingState = 0;
                    commandNum = 0;
                    gp.gameState = GameState.DIALOGUE;
                    currentDialogue = "You can't do that";
                }
                gp.keyH.enterPressed = false;
            }
        }
    }

    public void tradeSell() {
        drawInventory(gp.player, true);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
        int x;
        int y;
        int width;
        int height;

        //hint window
        x = gp.tileSize * 2;
        y = gp.tileSize * 10;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawPopUpWindow(x, y, width, height);
        g2.drawString("[ESC] -> back", x + 24, y + 25);
        g2.drawString("[C] -> sell", x + 24, y + 60);
        x = gp.tileSize * 12;
        drawPopUpWindow(x, y, width, height);
        g2.drawString("Coins: " + gp.player.coins, x + 24, y + 60);

        if(villager.typeOfVillager.equals(VillagerType.BUILDER)){
            builderSell();
        }else{
            normalSell();
        }
    }

    public void normalSell(){
        //draw a price window
        int indexItem = getItemIndexInInventory(slotColPlayer, slotRowPlayer);

        int sellingPrice = 0;
        if (indexItem < gp.player.inventory.size()) {
            int x = (int) (gp.tileSize * 16.5);
            int y = (int) (gp.tileSize * 5.5);
            int width = (int) (gp.tileSize * 2.5);
            int height = (int) (gp.tileSize * 1.5);
            drawPopUpWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 16, 32, 32, null);
            sellingPrice = gp.player.inventory.get(indexItem).price - (gp.player.inventory.get(indexItem).price / 3);
            String price = String.valueOf(sellingPrice);
            x += gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
            g2.drawString(price, x, y + (gp.tileSize - 15));
        }

        if (gp.keyH.cPressed) {
            try {
                if (gp.player.inventory.get(indexItem) != null) {
                    if (gp.player.inventory.get(indexItem) == gp.player.currentTool) {
                        commandNum = 0;
                        tradingState = 0;
                        gp.gameState = GameState.DIALOGUE;
                        currentDialogue = "You can't sell your current\nweapon";
                    } else {
                        if (gp.player.inventory.get(indexItem).howManyOfItem > 1) {
                            gp.player.inventory.get(indexItem).howManyOfItem--;
                        } else {
                            gp.player.inventory.remove(indexItem);
                        }
                        gp.player.coins += (int) (sellingPrice);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                commandNum = 0;
                tradingState = 0;
                gp.gameState = GameState.DIALOGUE;
                currentDialogue = "You can't sell nothing.";
            }
            gp.keyH.cPressed = false;
        }
    }

    public void builderSell(){
        int x = gp.tileSize * 2;
        int y = gp.tileSize;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 5;
        drawPopUpWindow(x, y, width, height);
    }

    /**
     * When in inventory, slots are drawn 4x5, so this method returns index of item in arraylist.
     *
     * @return index of item in arraylist.
     */
    public int getItemIndexInInventory(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void drawCharacterStats() {
        int frameX = gp.tileSize * 2;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 4;
        int frameHeight = gp.tileSize * 8;
        drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);
    }

    /**
     * Draws "Pop up" windows.
     * Why separated method?
     * Well, we can draw different sizes of windows, so it's practical to have method for all the windows.
     *
     * @param x      coordinate of the upper right corner
     * @param y      coordinate of the upper right corner
     * @param width  width of the window
     * @param height height of the window
     */
    public void drawPopUpWindow(int x, int y, int width, int height) {
        //4. number - alfa - capacity
        g2.setColor(new Color(205, 173, 171, 210));
        g2.fillRoundRect(x, y, width, height, 25, 25);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(80, 42, 80));
        //draws rectangle, these numbers are there, so it looks better.
        g2.drawRoundRect(x + 2, y + 2, width - 5, height - 5, 25, 25);
    }

    /**
     * Centers text to the middle of screen
     *
     * @param text text we want to center
     * @return centered text
     */
    public int xForCenteredText(String text) {
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //returns length of text
        return gp.getWidth() / 2 - textLength / 2;
    }
}

/*if (endGame) {
            g2.setFont(tNewRoman);
            g2.setColor(Color.BLUE);
            g2.setFont(g2.getFont().deriveFont(60F));

            String text = "END GAME";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //returns length of text
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Your time is: " + formatTime.format(playTime) + " seconds";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 6);
            g2.drawString(text, x, y);
        } else {
            g2.setFont(tNewRoman);
            g2.setColor(Color.MAGENTA);
            //g2.drawImage(imgKey, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.keyCounter, 74, 60);

            playTime += (double) 1/60;
            g2.drawString("Play time: " + formatTime.format(playTime), gp.tileSize*15, 60);

            if (messageOn) {
                g2.setColor(Color.BLACK);
                g2.setFont(g2.getFont().deriveFont(20f));//change size of a font
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 7);
                messageCounter++;

                if (messageCounter == 120) {//120frames - 60FPS = 2 s
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }*/
