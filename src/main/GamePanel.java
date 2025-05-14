package main;

import entity.Entity;
import entity.Player;
import interactiveTile.InteractiveTile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //Screen settings
    final int originalTileSize = 16; //16*16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public  int currentMap = 0;
    public  final int maxMap = 10;
    public final int screenWidth = tileSize * maxScreenCol; //960
    public final int screenHeight = tileSize * maxScreenRow; //720
    int FPS = 60;
    Thread gameThread;

    public KeyHandler keyH = new KeyHandler(this);
    public CheckCollision checkCollision = new CheckCollision(this);
    public TileManager tm = new TileManager(this);
    public Player player = new Player(this, keyH);
    public PlacingSetter plSetter = new PlacingSetter(this);
    public UI ui = new UI(this);
    public EventManager eventManager = new EventManager(this);
    SoundManager soundMusic = new SoundManager();
    SoundManager soundEffects = new SoundManager();

    public Entity[][] objects = new Entity[maxMap][10];
    public Entity[][] npc = new Entity[maxMap][5];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][500];
    ArrayList<Entity> entitiesAndObjects = new ArrayList<>();

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public GameState gameState = GameState.TITLE;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Starts game. Sets game state (Title screen) and places NPCs on a map.
     */
    public void settingsForGame() {
        //places objects on a map
        plSetter.setObject();
        plSetter.setNpc();
        plSetter.setInteractiveTile();
        gameState = GameState.TITLE;
    }

    public void startGameTime() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Runs the game.
     */
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

    /**
     * Also runs the game
     */
    public void update() {
        if (gameState.equals(GameState.PLAYING)) {
            player.update();

            for (Entity entity : npc[currentMap]) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (InteractiveTile interactiveTile : iTile[currentMap]) {
                if (interactiveTile != null) {
                    interactiveTile.update();
                }
            }
        }
    }

    /**
     * Draws on screen.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState.equals(GameState.TITLE) || gameState.equals(GameState.TUTORIAL)) {
            ui.draw(g2);
        } else {
            //Render order. Why didn't I use treeSet? I want to save duplicates, so I needed something else.
            tm.draw(g2);

            for (InteractiveTile interactiveTile : iTile[currentMap]) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }
            entitiesAndObjects.add(player);
            for (Entity npc : npc[currentMap]) {
                if (npc != null) {
                    entitiesAndObjects.add(npc);
                }
            }
            for (Entity object : objects[currentMap]) {
                if (object != null) {
                    entitiesAndObjects.add(object);
                }
            }
            entitiesAndObjects.sort(Comparator.comparingInt(o -> o.worldY)); //sorts list by worldY
            for (Entity entity : entitiesAndObjects) {
                entity.draw(g2);
            }
            entitiesAndObjects.clear();
            ui.draw(g2);
            if (keyH.showDebug) {
                g2.setColor(Color.white);
                g2.setFont(new Font("Arial", Font.PLAIN, 30));

                g2.drawString("WorldX: " + player.worldX, 10, 400);
                g2.drawString("WorldY: " + player.worldY, 10, 430);
                g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, 10, 460);
                g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, 10, 490);
                g2.drawString("Game state: " + gameState, 10, 520);
                g2.drawString("Level: " + player.level, 10, 550);
                g2.drawString("Exp: " + player.exp, 10, 580);
                g2.drawString("To next LVl: " + player.expToNextLevel, 10, 610);
            }
            g2.dispose();
        }
    }

    /**
     * plays something longer (for example, background music)
     *
     * @param index index of audio we want to use
     */
    public void playSound(int index) {
        soundMusic.setFile(index);
        soundMusic.play();
        soundMusic.loop();
    }

    public void stopMusic() {
        soundMusic.stopSound();
    }

    /**
     * Plays sound effect
     *
     * @param index index of audio we want to use
     */
    public void playSoundEffect(int index) {
        soundEffects.setFile(index);
        soundEffects.play();
    }

}