package main;

import java.awt.*;

public class EventManager {
    GamePanel gp;
    EventRect eventRect[][];

    public EventManager(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(true) {
            eventRect[row][col] = new EventRect();
            eventRect[col][row].x = 22;
            eventRect[col][row].y = 22;
            eventRect[col][row].width = 4;
            eventRect[col][row].height = 4;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {
        // video 19
    }

    /**
     * Basically returns if solid areas of player and tiles are in collision.
     * @param col - colon where the tile is placed
     * @param row - row where the tile is placed
     * @param reqDirection - If I want player to be in specific direction - that's that parameter.
     * @return if there's a collision happening.
     */
    public boolean isEventHappening(int col, int row, String reqDirection) {
        boolean isEventHappening = false;
        //Players solid area position
        gp.player.solidArea.x = gp.player.solidArea.x + gp.player.worldX;
        gp.player.solidArea.y = gp.player.solidArea.y + gp.player.worldY;
        //Events solid area position
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row])){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                isEventHappening = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return isEventHappening;
    }

}
