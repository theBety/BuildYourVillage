package main;

import object.ObjKey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font tNewRoman;
    BufferedImage imgKey;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean endGame = false;

    double playTime;
    DecimalFormat formatTime = new DecimalFormat("#0.0");
    public UI(GamePanel gp) {
        this.gp = gp;
        tNewRoman = new Font("TimesRoman", Font.PLAIN, 30);
        ObjKey key = new ObjKey(gp);
        imgKey = key.image;
    }

    public void draw(Graphics2D g2) {
        if (endGame) {
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
            g2.drawImage(imgKey, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.keyCounter, 74, 60);

            playTime += (double) 1/60;
            g2.drawString("Play time: " + formatTime.format(playTime), gp.tileSize*15, 60);

            if (messageOn) {
                g2.setColor(Color.BLACK);
                g2.setFont(g2.getFont().deriveFont(20f));//change size of a font
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 7);
                messageCounter++;

                if (messageCounter == 120) {//120frames - 60FPS = 2 seconds
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }

    public void printMessage(String text) {
        message = text;
        messageOn = true;
    }
}
