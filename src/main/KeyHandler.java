package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, rightPressed, leftPressed, spacePressed;
    public GamePanel gp;
    boolean showDebug = false;

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
            gameStatePlaying(code);
        } else if (gp.gameState.equals(GameState.PAUSED)) {
            gameStatePaused(code);
        } else if (gp.gameState.equals(GameState.DIALOGUE)) {
            gameStateDialogue(code);
        } else if (gp.gameState.equals(GameState.TITLE)) {
            gameStateTitle(code);
        } else if (gp.gameState.equals(GameState.TUTORIAL)) {
            gameStateTutorial(code);
        } else if (gp.gameState.equals(GameState.CHARACTER)) {
            gameStateCharacter(code);
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStatePlaying(int code) {
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
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_T) {
            showDebug = !showDebug;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = GameState.CHARACTER;
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateDialogue(int code) {
        if (code == KeyEvent.VK_N) {
            gp.gameState = GameState.PLAYING;
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateTitle(int code) {
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
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = GameState.PLAYING;
            }
            if (gp.ui.commandNum == 1) {
                gp.gameState = GameState.TUTORIAL;
            }
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStatePaused(int code) {
        if (code == KeyEvent.VK_R) {
            gp.gameState = GameState.PLAYING;
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateTutorial(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = GameState.PLAYING;
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateCharacter(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = GameState.PLAYING;
        }
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_S) {
            if(gp.ui.slotRow != 3){
                gp.ui.slotRow++;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_A) {
            if(gp.ui.slotCol != 0){
                gp.ui.slotCol--;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_D) {
            if(gp.ui.slotCol != 4){
                gp.ui.slotCol++;
                gp.playSoundEffect(1);
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
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
