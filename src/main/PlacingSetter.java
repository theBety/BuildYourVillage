package main;

import entity.Npc;
import object.*;

public class PlacingSetter {

    GamePanel gp;

    public PlacingSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        /*gp.objects[0] = new ObjChest(gp);
        gp.objects[0].worldX = 4 * gp.tileSize;
        gp.objects[0].worldY = 20 * gp.tileSize;*/
    }

    public void setNpc() {
        gp.npc[0] = new Npc(gp);
        gp.npc[0].worldX = 22* gp.tileSize;
        gp.npc[0].worldY = 21* gp.tileSize;
    }
}
