package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Screen settings
    final int originalTileSize = 16; //16*16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20; //in video 16*12
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol; //960
    public final int screenHeight = tileSize * maxScreenRow; //720

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    int FPS = 60;

    public CheckCollision checkCollision = new CheckCollision(this);
    public TileManager tm = new TileManager(this);
    public Player player = new Player(this, keyH);
    public PlacingSetter plSetter = new PlacingSetter(this);
    public SuperObject[] objects = new SuperObject[10];
    SoundManager soundMusic = new SoundManager();
    SoundManager soundEffects = new SoundManager();
    public UI ui = new UI(this);

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void settingsForGame() {
        //setupGame in video

        plSetter.setObject();

        playSound(0);
    }

    public void startGameTime() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double interval = (double) 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + interval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime / 1000000);
                nextDrawTime += interval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tm.draw(g2); //draw tile
        //draw object
        for (SuperObject object : objects) {
            if (object != null) {
                object.drawObject(g2, this);
            }
        }
        player.draw(g2); //draw player
        ui.draw(g2);
        g2.dispose();

    }

    /**
     * plays something longer (for example background music)
     * @param index index of audio we want to use
     */
    public void playSound(int index) {
        soundMusic.setFile(index);
        soundMusic.play();
        soundMusic.loop();
    }

    public void stopMusic(){
        soundMusic.stopSound();
    }

    public void playSoundEffect(int index){
        soundEffects.setFile(index);
        soundEffects.play();
    }

}
