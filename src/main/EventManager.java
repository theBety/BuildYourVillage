package main;

public class EventManager {
    GamePanel gp;
    EventRect[][][] eventRect;
    int previousEventX;
    int previousEventY;
    boolean canEventHappen = true;

    public EventManager(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 22;
            eventRect[map][col][row].y = 22;
            eventRect[map][col][row].width = 4;
            eventRect[map][col][row].height = 4;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    /**
     * Checks if player is least one tile away form
     */
    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canEventHappen = true;
        }
        if (canEventHappen) {
            if (isEventHappening(0, 21, 19, "any")) {
                if(gp.plSetter.activePath0) teleport(1, 22, 25);
            } else if (isEventHappening(0, 27, 19, "any")) {
                if(gp.plSetter.activePath1) teleport(0, 22, 25);
            } else if (isEventHappening(0, 12, 18, "any")) {
                if(gp.plSetter.activePath2) teleport(0, 22, 25);
            } else if (isEventHappening(0, 16, 22, "any")) {
                if(gp.plSetter.activePath3) teleport(0, 22, 25);
            } else if (isEventHappening(1, 21, 25, "any")) {
                teleport(0, 21, 19);
            }
        }
    }

    /**
     * Basically returns if solid areas of player and tiles are in collision.
     * @param col          - colon where the tile is placed
     * @param row          - row where the tile is placed
     * @param reqDirection - If I want player to be in specific direction - that's that parameter.
     * @return if there's a collision happening.
     */
    public boolean isEventHappening(int map, int col, int row, String reqDirection) {
        boolean isEventHappening = false;

        if (map == gp.currentMap) {
            //Players solid area position
            gp.player.solidArea.x = gp.player.solidArea.x + gp.player.worldX;
            gp.player.solidArea.y = gp.player.solidArea.y + gp.player.worldY;
            //Events solid area position
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    isEventHappening = true;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

            return isEventHappening;
        }
        return isEventHappening;
    }

    public void teleport(int mapNum, int col, int row) {
        gp.currentMap = mapNum;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        canEventHappen = false;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
    }
}