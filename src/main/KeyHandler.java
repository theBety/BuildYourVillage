package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, rightPressed, leftPressed, spacePressed;
    public GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState.equals(GameState.PLAYING)) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_R) {
                gp.gameState = GameState.PAUSED;
            }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
        }
        if (gp.gameState.equals(GameState.PAUSED)) {
            if (code == KeyEvent.VK_R) {
                gp.gameState = GameState.PLAYING;
            }
        }
        if (gp.gameState.equals(GameState.DIALOGUE)) {
            if (code == KeyEvent.VK_N) {
                gp.gameState = GameState.PLAYING;
            }
        }
        if (gp.gameState.equals(GameState.TITLE)) {
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = GameState.PLAYING;
                }
                if(gp.ui.commandNum == 1){
                    gp.gameState = GameState.TUTORIAL;
                }
            }
        } else if(gp.gameState.equals(GameState.TUTORIAL)){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = GameState.PLAYING;
            }
        }
    }

    /**
     * Manages keys when they're released.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
