package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    GamePanel gp;

    public UtilityTool(GamePanel gp) {
        this.gp = gp;
    }
    public BufferedImage scaledImage(BufferedImage imageToScale, int width, int height) {
        // Scaling images - saves time
        BufferedImage scaledImage = new BufferedImage(width, height, imageToScale.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(imageToScale, 0, 0, width, height, null);

        return scaledImage;
    }

    public String firstLetterUppercase(String word){
        if(word == null || word.isEmpty()){
            return "";
        }
        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
    }
    /**
     * Draws "Pop up" windows.
     * Why separated method?
     * Well, we can draw different sizes of windows, so it's practical to have method for all the windows.
     *
     * @param x      coordinate of the upper right corner
     * @param y      coordinate of the upper right corner
     * @param width  width of the window
     * @param height height of the window
     */
    public void drawPopUpWindow(int x, int y, int width, int height) {
        //4. number - alfa - capacity
        gp.ui.g2.setColor(new Color(205, 173, 171, 210));
        gp.ui.g2.fillRoundRect(x, y, width, height, 25, 25);
        gp.ui.g2.setStroke(new BasicStroke(5));
        gp.ui.g2.setColor(new Color(80, 42, 80));
        //draws rectangle, these numbers are there, so it looks better.
        gp.ui.g2.drawRoundRect(x + 2, y + 2, width - 5, height - 5, 25, 25);
    }

    /**
     * Centers text to the middle of screen
     *
     * @param text text we want to center
     * @return centered text
     */
    public int xForCenteredText(String text) {
        int textLength = (int) gp.ui.g2.getFontMetrics().getStringBounds(text, gp.ui.g2).getWidth(); //returns length of text
        return gp.getWidth() / 2 - textLength / 2;
    }

    /**
     * When in inventory, slots are drawn 4x5, so this method returns index of item in arraylist.
     *
     * @return index of item in arraylist.
     */
    public int getItemIndexInInventory(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    /**
     * Searches through players' inventory and tries to find item with requested name.
     *
     * @param itemName - name of searched item.
     * @return - index in inventory of searched item
     */
    public int findItemInInventory(String itemName) {
        int itemIndex = -1;
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            if (gp.player.inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public void drawHintWindow(){
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 10;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        gp.ui.ut.drawPopUpWindow(x, y, width, height);
        gp.ui.g2.drawString("[ESC] -> back", x + 24, y + 25);
        gp.ui.g2.drawString("[C] -> sell", x + 24, y + 60);
        x = gp.tileSize * 12;
        gp.ui.ut.drawPopUpWindow(x, y, width, height);
        gp.ui.g2.drawString("Coins: " + gp.player.coins, x + 24, y + 60);
    }

    /**
     * When player presses button to go back to game, or player just needs to go back to game, this method is called.
     * @param message what player messed up for example. What message will be printed on a screen
     */
    public void goToPlayState(String message){
        gp.ui.commandNum = 0;
        gp.ui.tradingState = 0;
        gp.gameState = GameState.DIALOGUE;
        gp.ui.currentDialogue = message;
    }
}
