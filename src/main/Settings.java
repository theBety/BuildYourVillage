package main;

import javax.swing.*;

public class Settings {

    JFrame frame = new JFrame();

    /**
     * Starts everything in a game. Sets main things about gamePanel and starts the whole game.
     */
    public Settings() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setTitle("Build your Village");
        frame.setVisible(true);
        GamePanel gp = new GamePanel();
        frame.add(gp);
        frame.pack();

        frame.setLocationRelativeTo(null);
        gp.requestFocusInWindow();
        gp.settingsForGame();
        gp.startGameTime();
    }
}
