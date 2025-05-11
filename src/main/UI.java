package main;

import java.awt.*;
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
    public int slotCol = 0;
    public int slotRow = 0;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();


    //for now, unused things.
    public boolean endGame = false;
    public int SettingsInTitleScreen = 0;
    double playTime;
    DecimalFormat formatTime = new DecimalFormat("#0.0");

    public UI(GamePanel gp) {
        this.gp = gp;
        tNewRoman = new Font("TimesRoman", Font.PLAIN, 30);
        bookMan = new Font("Bookman Old Style", Font.PLAIN, 30);
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
                drawInventory();
                drawInfoScreen();
                break;
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
            y = gp.tileSize * (helpInt + 1);
            g2.drawString(text, x, y);
        } catch (IOException i) {
            System.err.println("IO Exception help");
        }
    }

    /**
     * Draws players' inventory including items and cursor.
     */
    public void drawInventory() {
        int frameX = gp.tileSize * 13;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawPopUpWindow(frameX, frameY, frameWidth, frameHeight);

        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        int cursorX = slotXStart + (gp.tileSize * slotCol);
        int cursorY = slotYStart + (gp.tileSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        g2.setColor(new Color(80, 42, 80));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        for (int i = 0; i < gp.player.inventory.size(); i++) {

            if (gp.player.inventory.get(i) == gp.player.currentTool) {
                g2.setColor(new Color(162, 76, 97));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += gp.tileSize;
            if (i == 4 || i == 9 || i == 14) {
                slotY += gp.tileSize;
                slotX = slotXStart;
            }
        }

        int desFrameY = frameY + frameHeight + gp.tileSize;
        int desFrameHeight = gp.tileSize * 3;

        int textX = frameX + 20;
        int textY = desFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));
        int indexOfItem = getItemIndexInInventory();

        if (indexOfItem < gp.player.inventory.size()) {
            drawPopUpWindow(frameX, desFrameY, frameWidth, desFrameHeight);
            for (String line : gp.player.inventory.get(indexOfItem).descriptionOfItem.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += gp.tileSize / 2;
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

    /**
     * When in inventory, slots are drawn 4x5, so this method returns index of item in arraylist.
     *
     * @return index of item in arraylist.
     */
    public int getItemIndexInInventory() {
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
