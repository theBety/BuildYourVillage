package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, rightPressed, leftPressed, spacePressed, enterPressed, cPressed;
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
        if (gp.gameState.equals(GameState.PLAYING)) gameStatePlaying(code);
        else if (gp.gameState.equals(GameState.PAUSED)) gameStatePaused(code);
        else if (gp.gameState.equals(GameState.DIALOGUE)) gameStateDialogue(code);
        else if (gp.gameState.equals(GameState.TITLE)) gameStateTitle(code);
        else if (gp.gameState.equals(GameState.TUTORIAL)) gameStateTutorial(code);
        else if (gp.gameState.equals(GameState.CHARACTER)) gameStateCharacter(code);
        else if (gp.gameState.equals(GameState.SETTINGS)) gameStateSettings(code);
        else if (gp.gameState.equals(GameState.TRADING)) gameStateTrading(code);
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStatePlaying(int code) {
        if (code == KeyEvent.VK_W) upPressed = true;
        if (code == KeyEvent.VK_S) downPressed = true;
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
        if (code == KeyEvent.VK_ENTER) enterPressed = !enterPressed;
        if (code == KeyEvent.VK_R) gp.gameState = GameState.PAUSED;
        if (code == KeyEvent.VK_SPACE) spacePressed = true;
        if (code == KeyEvent.VK_T) showDebug = !showDebug;
        if (code == KeyEvent.VK_E) gp.gameState = GameState.CHARACTER;
        if (code == KeyEvent.VK_SHIFT) gp.gameState = GameState.SETTINGS;
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateDialogue(int code) {
        if (code == KeyEvent.VK_ESCAPE) gp.gameState = GameState.PLAYING;
        if (code == KeyEvent.VK_ENTER) enterPressed = !enterPressed;
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateTitle(int code) {
        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 1;
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) gp.ui.commandNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) gp.gameState = GameState.PLAYING;
            if (gp.ui.commandNum == 1) gp.gameState = GameState.TUTORIAL;
            enterPressed = false;
        }
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStatePaused(int code) {
        if (code == KeyEvent.VK_R) gp.gameState = GameState.PLAYING;
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateTutorial(int code) {
        if (code == KeyEvent.VK_ENTER) gp.gameState = GameState.PLAYING;
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateCharacter(int code) {
        if (code == KeyEvent.VK_E) gp.gameState = GameState.PLAYING;
        playerInventory(code);
    }

    /**
     * Based on code, something happens - communicates with keyboard.
     *
     * @param code code from keyboard.
     */
    public void gameStateSettings(int code) {
        if (code == KeyEvent.VK_SHIFT) gp.gameState = GameState.PLAYING;
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 4;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 4) gp.ui.commandNum = 0;
        }
        //Volume controls
        if (code == KeyEvent.VK_D) {
            if (gp.ui.commandNum == 0 && gp.soundMusic.volumeScale < 10) {
                gp.soundMusic.volumeScale++;
                gp.soundMusic.volume();
            }
            if (gp.ui.commandNum == 1 && gp.soundEffects.volumeScale < 10) {
                gp.soundEffects.volumeScale++;
                gp.soundMusic.volume();
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.commandNum == 0 && gp.soundMusic.volumeScale > 0) {
                gp.soundMusic.volumeScale--;
                gp.soundMusic.volume();
            }
            if (gp.ui.commandNum == 1 && gp.soundEffects.volumeScale > 0) {
                gp.soundEffects.volumeScale--;
                gp.soundMusic.volume();
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 2) gp.gameState = GameState.TUTORIAL;
            if (gp.ui.commandNum == 3) {
                System.out.println("Game ended successfully");
                gp.endGameTimer();
                System.exit(1);
            }
            if (gp.ui.commandNum == 4) gp.gameState = GameState.PLAYING;
        }

    }

    public void gameStateTrading(int code) {
        if (code == KeyEvent.VK_ENTER) enterPressed = true;
        if (code == KeyEvent.VK_C) cPressed = true;
        if (gp.ui.tradingState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
            }
        }
        if (gp.ui.tradingState == 1) {
            villagerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) gp.ui.tradingState = 0;
        }
        if (gp.ui.tradingState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) gp.ui.tradingState = 0;
        }
    }

    /**
     * Controls of inventory
     *
     * @param code code from keyboard.
     */
    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRowPlayer != 0) {
                gp.ui.slotRowPlayer--;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRowPlayer != 3) {
                gp.ui.slotRowPlayer++;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotColPlayer != 0) {
                gp.ui.slotColPlayer--;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotColPlayer != 4) {
                gp.ui.slotColPlayer++;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    public void villagerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRowVil != 0) {
                gp.ui.slotRowVil--;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRowVil != 3) {
                gp.ui.slotRowVil++;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotColVil != 0) {
                gp.ui.slotColVil--;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotColVil != 4) {
                gp.ui.slotColVil++;
                gp.playSoundEffect(1);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
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
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
    }
}