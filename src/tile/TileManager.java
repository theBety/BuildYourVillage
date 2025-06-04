package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;
    int count = 0;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[55]; //how many kinds of tiles
        getTileImage();
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        createMaps("/maps/worldMap2.csv", 0);
        createMaps("/maps/house.csv", 1);
    }

    /**
     * "Initializes" all the tiles
     */
    public void getTileImage() {
        try{
            InputStream is = getClass().getResourceAsStream("/files/loadTiles.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
            String line;
            count = 0;
            while ((line = br.readLine()) != null) {
                if(count == 27 || count == 34 || count == 41){
                    count++;
                }
                String[] split = line.split(",");
                setUpImage(count, split[0], Boolean.parseBoolean(split[1]));
                count++;
            }
        }catch(IOException i){
            System.out.println("ups");
        }
    }

    public void setUpImage(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool(gp);
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = utilityTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            throw new Error("Something's wrong in setUpImage");
        }catch (NullPointerException e){
            System.out.println("Null pointer in tileManager");
            System.out.println(count);
            System.out.println(imageName);
        }
    }

    /**
     * Reads a map file and adds the numbers to a map 3D array
     * @param path path to file
     * @param map With what map is the program dealing with
     */
    public void createMaps(String path, int map) {
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
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Something's wrong in create maps method");
            System.out.println(e.getMessage());
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
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