package UserInterface;

import entity.Entity;
import entity.VillagerType;
import main.GamePanel;
import main.GameState;
import main.UtilityTool;
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
    public Graphics2D g2;
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
    public UtilityTool ut;
    public UITrading uiTrading;
    public UITitleScreen uiTitleScreen;
    public int tradingState = 0;
    BufferedImage coin;

    //for now, unused things.
    public boolean endGame = false;
    public int SettingsInTitleScreen = 0;
    double playTime;
    DecimalFormat formatTime = new DecimalFormat("#0.0");

    public UI(GamePanel gp) {
        this.gp = gp;
        ut = new UtilityTool(gp);
        uiTrading = new UITrading(gp, this);
        uiTitleScreen = new UITitleScreen(gp, this);
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
                uiTitleScreen.drawTitleScreen();
                break;
            case TUTORIAL:
                uiTitleScreen.drawHowToPlayScreen();
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
        int x = ut.xForCenteredText(text);
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
        ut.drawPopUpWindow(x, y, width, height);

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

        ut.drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

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
            int indexOfItem = ut.getItemIndexInInventory(slotCol, slotRow);

            if (indexOfItem < entity.inventory.size()) {
                ut.drawPopUpWindow(frameX, desFrameY, frameWidth, desFrameHeight);
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
        ut.drawPopUpWindow(gp.tileSize, gp.tileSize, gp.tileSize * 5, gp.tileSize * 7);
        int yForText = gp.tileSize*2;
        int xFortext = gp.tileSize + 20;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        g2.drawString("STATISTICS", gp.tileSize + 38, yForText);
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
        ut.drawPopUpWindow(gp.tileSize * 6, gp.tileSize, gp.tileSize * 8, gp.tileSize * 10);

        String text = "SETTINGS";
        int x = ut.xForCenteredText(text);
        int y = gp.tileSize * 2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));

        y += gp.tileSize + gp.tileSize / 2;
        x = gp.tileSize*7;
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

        x = gp.tileSize * 11;
        y = gp.tileSize * 3;
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
                uiTrading.tradeSelectItem();
                break;
            case 1:
                uiTrading.tradeBuy();
                break;
            case 2:
                uiTrading.tradeSell();
                break;
        }
    }

    public void drawCharacterStats() {
        int frameX = gp.tileSize * 2;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 4;
        int frameHeight = gp.tileSize * 8;
        ut.drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);
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
