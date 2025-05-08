package main;

import entity.Npc;
import entity.Villager;
import entity.VillagerType;
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
        gp.objects[counterInObject] = new ObjChest(gp);
        gp.objects[counterInObject].worldX = 21 * gp.tileSize;
        gp.objects[counterInObject].worldY = 20 * gp.tileSize;
        counterInObject++;

        gp.objects[counterInObject] = new ObjPurpleHouse(gp);
        gp.objects[counterInObject].worldX = 22 * gp.tileSize;
        gp.objects[counterInObject].worldY = 18 * gp.tileSize;
        counterInObject++;

        gp.objects[counterInObject] = new ObjTree(gp);
        gp.objects[counterInObject].worldX = 11 * gp.tileSize;
        gp.objects[counterInObject].worldY = 19 * gp.tileSize;
        counterInObject++;
    }

    public void setNpc() {
        gp.npc[0] = new Npc(gp);
        gp.npc[0].worldX = 22 * gp.tileSize;
        gp.npc[0].worldY = 21 * gp.tileSize;

        gp.npc[1] = new Villager(gp, VillagerType.BUILDER);
        gp.npc[1].worldX = 13 * gp.tileSize;
        gp.npc[1].worldY = 24 * gp.tileSize;

        gp.npc[2] = new Villager(gp, VillagerType.SMITH);
        gp.npc[2].worldX = 28 * gp.tileSize;
        gp.npc[2].worldY = 25 * gp.tileSize;

        gp.npc[3] = new Villager(gp, VillagerType.WOOD);
        gp.npc[3].worldX = 24 * gp.tileSize;
        gp.npc[3].worldY = 32 * gp.tileSize;
    }

    public void setTrees() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/worldMap2.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String text = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = text.split(",");
                    int num = Integer.parseInt(numbers[col]);
                    if (num == 5) {
                        gp.objects[counterInObject] = new ObjTree(gp);
                        gp.objects[counterInObject].worldX = col * gp.tileSize;
                        gp.objects[counterInObject].worldY = row * gp.tileSize;
                        counterInObject++;
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
