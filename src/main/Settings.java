package main;

import javax.swing.*;

public class Settings {

    JFrame window = new JFrame();

    public Settings() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.setTitle("Build your Village");
        window.setVisible(true);
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        gp.requestFocusInWindow();
        gp.settingsForGame();
        gp.startGameTime();
    }
}
