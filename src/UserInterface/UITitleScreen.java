package UserInterface;

import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UITitleScreen {
    GamePanel gp;
    UI ui;

    public UITitleScreen(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    /**
     * Draws title screen.
     */
    public void drawTitleScreen() {
        ui.g2.setColor(new Color(202, 153, 171));
        ui.g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        ui.g2.setColor(new Color(65, 21, 40));

        ui.g2.setFont(ui.bookMan.deriveFont(Font.BOLD, 70));
        String text = "BUILD YOUR VILLAGE";
        int x = ui.ut.xForCenteredText(text);
        int y = gp.screenHeight / 4;
        ui.g2.drawString(text, x, y);

        ui.g2.setFont(ui.bookMan.deriveFont(Font.BOLD, 35));
        text = "START";
        x = ui.ut.xForCenteredText(text);
        y += gp.tileSize * 5;
        ui.g2.drawString(text, x, y);
        if (ui.commandNum == 0) {
            ui.g2.drawString("*", x - gp.tileSize, y);
        }

        y += gp.tileSize * 2;
        text = "HOW TO PLAY";
        x = ui.ut.xForCenteredText(text);
        ui.g2.drawString(text, x, y);
        if (ui.commandNum == 1) {
            ui.g2.drawString("*", x - gp.tileSize, y);
        }

        y -= gp.tileSize * 6;
        x = (gp.screenWidth / 2) - gp.tileSize;
        ui.g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    /**
     * Draws screen after user hits "how to play" button.
     */
    public void drawHowToPlayScreen() {
        ui.g2.setColor(new Color(202, 153, 171));
        ui.g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        ui.g2.setColor(new Color(65, 21, 40));

        ui.g2.setFont(ui.bookMan.deriveFont(Font.BOLD, 30));
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
                    x = ui.ut.xForCenteredText(text);
                } else {
                    y = gp.tileSize * helpInt;
                    x = gp.tileSize;
                }
                ui.g2.drawString(text, x, y);
                helpInt++;
            }
            ui.g2.setFont(ui.bookMan.deriveFont(Font.BOLD, 35));
            text = "* PLAY";
            x = gp.screenWidth - gp.tileSize * 3;
            y = gp.tileSize * helpInt;
            ui.g2.drawString(text, x, y);
        } catch (IOException i) {
            System.err.println("IO Exception help");
        }
    }
}