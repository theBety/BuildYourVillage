package main;

import object.*;

public class PlacingSetter {
    //AssetSetter in video

    GamePanel gp;

    public PlacingSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objects[0] = new ObjChest(gp);
        gp.objects[0].worldX = 4 * gp.tileSize;
        gp.objects[0].worldY = 20 * gp.tileSize;

        gp.objects[1] = new ObjChest(gp);
        gp.objects[1].worldX = 25 * gp.tileSize;
        gp.objects[1].worldY = 16 * gp.tileSize;

        gp.objects[2] = new ObjTrapdoor(gp);
        gp.objects[2].worldX = 35 * gp.tileSize;
        gp.objects[2].worldY = 19 * gp.tileSize;

        gp.objects[3] = new ObjKey(gp);
        gp.objects[3].worldX = 24 * gp.tileSize;
        gp.objects[3].worldY = 12 * gp.tileSize;

        gp.objects[4] = new ObjBoots(gp);
        gp.objects[4].worldX = 39 * gp.tileSize;
        gp.objects[4].worldY = 18 * gp.tileSize;
    }
}
