package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[48]; //how many kinds of tiles
        getTileImage();
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        createMaps("/maps/worldMap2.csv");
    }

    /**
     * "Initializes" all the tiles
     */
    public void getTileImage() {
        setUpImage(0, "grassWithGrass", false);
        setUpImage(1, "leftTopStone", false);
        setUpImage(2, "rightStone", false);
        setUpImage(3, "leftWheatField", false);
        setUpImage(4, "rightTopStone", false);
        setUpImage(5, "tree", true);
        setUpImage(6, "eWater3", true);
        setUpImage(7, "leftBottomClay", false);
        setUpImage(8, "leftBottomWheatField", false);
        setUpImage(9, "leftTopClay", false);
        setUpImage(10, "rightBottomClay", false);
        setUpImage(11, "rightTopWheatField", false);
        setUpImage(12, "wall", true);
        setUpImage(13, "eWater4", true);
        setUpImage(14, "leftClay", false);
        setUpImage(15, "leftDownWater", true);
        setUpImage(16, "leftTopStone", false);
        setUpImage(17, "rightBottomStone", false);
        setUpImage(18, "rightUpWater", true);
        setUpImage(19, "wheatField", false);
        setUpImage(20, "grassWithFlowers", false);
        setUpImage(21, "leftTopWheatField", false);
        setUpImage(22, "leftUpWater", true);
        setUpImage(23, "leftWater", true);
        setUpImage(24, "rightBottomWheatField", false);
        setUpImage(25, "rightWater", true);
        setUpImage(26, "basicGrass", false);
        setUpImage(28, "rightClay", false);
        setUpImage(29, "rightDownWater", true);
        setUpImage(30, "rightStone", false);
        setUpImage(31, "rightTopClay", false);
        setUpImage(32, "rightWheatField", false);
        setUpImage(33, "basicWater", true);
        setUpImage(35, "stone", false);
        setUpImage(36, "topClay", false);
        setUpImage(37, "topStone", false);
        setUpImage(38, "topWater", true);
        setUpImage(39, "topWheatField", false);
        setUpImage(40, "bottomClay", false);
        setUpImage(42, "bottomStone", false);
        setUpImage(43, "bottomWater", true);
        setUpImage(44, "bottomWheatField", false);
        setUpImage(45, "clay", false);
        setUpImage(46, "eWater1", true);
        setUpImage(47, "eWater2", true);
    }

    public void setUpImage(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = utilityTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            throw new Error("Something's wrong");
        }
    }

    public void createMaps(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String text = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = text.split(",");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Something's wrong");
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
