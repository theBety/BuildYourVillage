package main;

import javax.swing.*;

public class Settings {

    JFrame window = new JFrame();

    public Settings() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Build your Village");

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        gp.requestFocusInWindow();
        gp.startGameTime();
    }
}
