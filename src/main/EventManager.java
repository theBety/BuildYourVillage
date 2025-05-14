package main;

public class EventManager {
    GamePanel gp;
    EventRect[][][] eventRect;

    public EventManager(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;

        while(map< gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][row][col] = new EventRect();
            eventRect[map][col][row].x = 22;
            eventRect[map][col][row].y = 22;
            eventRect[map][col][row].width = 4;
            eventRect[map][col][row].height = 4;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    public void checkEvent() {
        if (isEventHappening(0,17,25,"any")) {
            teleport(1,25,21);
            teleport(1,25,21);
        }
    }

    /**
     * Basically returns if solid areas of player and tiles are in collision.
     * @param col - colon where the tile is placed
     * @param row - row where the tile is placed
     * @param reqDirection - If I want player to be in specific direction - that's that parameter.
     * @return if there's a collision happening.
     */
    public boolean isEventHappening(int map,int col, int row, String reqDirection) {
        boolean isEventHappening = false;

        if(map == gp.currentMap){
            //Players solid area position
            gp.player.solidArea.x = gp.player.solidArea.x + gp.player.worldX;
            gp.player.solidArea.y = gp.player.solidArea.y + gp.player.worldY;
            //Events solid area position
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row])){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
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

    public void teleport(int mapNum, int col, int row){

    }

}
