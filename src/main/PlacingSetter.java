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
        gp.objects[forMap][counterInObject].worldX = 21 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 25 * gp.tileSize;
        counterInObject++;

        forMap = 1;
        gp.objects[forMap][counterInObject] = new ObjChest(gp);
        gp.objects[forMap][counterInObject].worldX = 23 * gp.tileSize;
        gp.objects[forMap][counterInObject].worldY = 22 * gp.tileSize;
        counterInObject++;
    }

    public void setNpc() {
        int forMap = 0;
        int counter = 0;

        gp.npc[forMap][counter] = new Npc(gp);
        gp.npc[forMap][counter].worldX = 22 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 21 * gp.tileSize;
        counter++;
        gp.npc[forMap][counter] = new Villager(gp, VillagerType.BUILDER);
        gp.npc[forMap][counter].worldX = 13 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 24 * gp.tileSize;
        counter++;
        gp.npc[forMap][counter] = new Villager(gp, VillagerType.SMITH);
        gp.npc[forMap][counter].worldX = 28 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 25 * gp.tileSize;
        counter++;
        gp.npc[forMap][counter] = new Villager(gp, VillagerType.SELLER);
        gp.npc[forMap][counter].worldX = 24 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 32 * gp.tileSize;
        counter++;
        gp.npc[forMap][counter] = new Villager(gp, VillagerType.BUILDER);
        gp.npc[forMap][counter].worldX = 14 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 24 * gp.tileSize;
        counter++;
        gp.npc[forMap][counter] = new Villager(gp, VillagerType.SMITH);
        gp.npc[forMap][counter].worldX = 29 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 25 * gp.tileSize;
        counter++;
        gp.npc[forMap][counter] = new Villager(gp, VillagerType.SELLER);
        gp.npc[forMap][counter].worldX = 25 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 32 * gp.tileSize;
        counter++;

        forMap = 1;
        gp.npc[forMap][counter] = new Npc(gp);
        gp.npc[forMap][counter].worldX = 24 * gp.tileSize;
        gp.npc[forMap][counter].worldY = 25 * gp.tileSize;
        counter++;
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
