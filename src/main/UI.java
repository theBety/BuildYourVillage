package main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font tNewRoman;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean endGame = false;
    public String currentDialogue = "";
    Font bookMan;
    public int commandNum = 0;
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
                break;
            case PAUSED:
                drawPauseScreen();
                break;
            case DIALOGUE:
                drawDialogueScreen();
                break;
            case TITLE:
                drawTitleScreen();
                break;
            case TUTORIAL:
                drawHowToPlayScreen();
                break;
        }

    }

    public void printMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawPauseScreen() {
        String text = "PAUSED GAME";
        int x = xForCenteredText(text);
        int y = gp.screenHeight / 3;
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
            int helpInt = 2;
            int x;
            int y;
            String text;
            while ((text = br.readLine()) != null) {
                if (helpInt == 6) {
                    helpInt += 2;
                    y = gp.tileSize * helpInt;
                    x = gp.tileSize;
                } else if (helpInt >= 9) {
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
            y = gp.tileSize * (helpInt + 2);
            g2.drawString(text, x, y);
        } catch (IOException i) {
            System.err.println("IO Exception help");
        }
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
        g2.setColor(new Color(138, 130, 120));
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
