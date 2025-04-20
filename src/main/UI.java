package main;

import object.ObjKey;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font tNewRoman;
    BufferedImage imgKey;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public UI(GamePanel gp) {
        this.gp = gp;
        tNewRoman = new Font("TimesRoman", Font.PLAIN, 30);
        ObjKey key = new ObjKey();
        imgKey = key.image;
    }

    public void draw(Graphics2D g2){
        g2.setFont(tNewRoman);
        g2.setColor(Color.PINK);
        g2.drawImage(imgKey, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.keyCounter, 74, 60);

        if(messageOn){
            g2.setFont(g2.getFont().deriveFont(20f));//change size of a font
            g2.drawString(message, gp.tileSize/2, gp.tileSize*7);
            messageCounter++;

            if(messageCounter == 120){//120frames - 60FPS = 2 seconds
                messageOn = false;
                messageCounter = 0;
            }
        }
    }
    public void printMessage(String text){
        message = text;
        messageOn = true;
    }
}
