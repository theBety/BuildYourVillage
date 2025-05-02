package main;

import entity.Npc;
import entity.Villager;
import entity.VillagerType;
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

        gp.npc[1] = new Villager(gp, VillagerType.BUILDER);
        gp.npc[1].worldX = 13* gp.tileSize;
        gp.npc[1].worldY = 24* gp.tileSize;

        gp.npc[2] = new Villager(gp, VillagerType.SMITH);
        gp.npc[2].worldX = 28* gp.tileSize;
        gp.npc[2].worldY = 25* gp.tileSize;

        gp.npc[3] = new Villager(gp, VillagerType.WOOD);
        gp.npc[3].worldX = 24* gp.tileSize;
        gp.npc[3].worldY = 32* gp.tileSize;
    }
}
