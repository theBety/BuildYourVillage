package main;

import entity.Npc;
import entity.Villager;
import entity.VillagerType;
import interactiveTile.IntTileClay;
import interactiveTile.IntTileStone;
import interactiveTile.IntTileTree;
import interactiveTile.IntTileWheat;
import object.*;

import java.io.*;
import java.util.Objects;

public class PlacingSetter {

    GamePanel gp;
    int counterInObject = 0;

    public PlacingSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int forMap = 0;

        gp.objects[forMap][counterInObject] = new ObjChest(gp);
        gp.objects[forMap][counterInObject].worldX = 21 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 20 * gp.tileSize;
        counterInObject++;

        gp.objects[forMap][counterInObject] = new ObjPurpleHouse(gp);
        gp.objects[forMap][counterInObject].worldX = 22 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 18 * gp.tileSize;
        counterInObject++;

        gp.objects[forMap][counterInObject] = new ObjKey(gp);
        gp.objects[forMap][counterInObject].worldX = 28 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 19 * gp.tileSize;
        counterInObject++;

        gp.objects[forMap][counterInObject] = new ObjKey(gp);
        gp.objects[forMap][counterInObject].worldX = 26 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 17 * gp.tileSize;
        counterInObject++;

        gp.objects[forMap][counterInObject] = new ObjCoin(gp);
        gp.objects[forMap][counterInObject].worldX = 30 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 19 * gp.tileSize;
        counterInObject++;
    }

    public void setNpc() {
        int forMap = 0;

        gp.npc[forMap][0] = new Npc(gp);
        gp.npc[forMap][0].worldX = 22 * gp.tileSize;
        gp.npc[forMap][0].worldY = 21 * gp.tileSize;

        gp.npc[forMap][1] = new Villager(gp, VillagerType.BUILDER);
        gp.npc[forMap][1].worldX = 13 * gp.tileSize;
        gp.npc[forMap][1].worldY = 24 * gp.tileSize;

        gp.npc[forMap][2] = new Villager(gp, VillagerType.SMITH);
        gp.npc[forMap][2].worldX = 28 * gp.tileSize;
        gp.npc[forMap][2].worldY = 25 * gp.tileSize;

        gp.npc[forMap][3] = new Villager(gp, VillagerType.WOOD);
        gp.npc[forMap][3].worldX = 24 * gp.tileSize;
        gp.npc[forMap][3].worldY = 32 * gp.tileSize;
    }

    /**
     * Places trees (leaves) on every tree trunk.
     */
    public void setInteractiveTile() {
        try {
            int forMap = 0;

            InputStream is = getClass().getResourceAsStream("/maps/worldMap2.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            int col = 0;
            int row = 0;
            int counterInITile = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String text = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = text.split(",");
                    int num = Integer.parseInt(numbers[col]);
                    if (num == 5) {
                        gp.iTile[forMap][counterInITile] = new IntTileTree(gp, col, row);
                        counterInITile++;
                    }
                    if (num == 19) {
                        gp.iTile[forMap][counterInITile] = new IntTileWheat(gp, col, row);
                        counterInITile++;
                    }
                    if (num == 35) {
                        gp.iTile[forMap][counterInITile] = new IntTileStone(gp, col, row);
                        counterInITile++;
                    }
                    if (num == 45) {
                        gp.iTile[forMap][counterInITile] = new IntTileClay(gp, col, row);
                        counterInITile++;
                    }
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Something's wrong in placing setter");
        }
    }
}
