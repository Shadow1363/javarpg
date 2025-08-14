package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    private BufferedImage tileSheet; 

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50]; // allow more tiles if needed
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        try {
            // Load the sheet only once
            tileSheet = ImageIO.read(new File("./res/tiles/tileSheet.png"));
            getTileImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadMap("./res/maps/world01.txt");
    }

    public void addTileFromSheet(int col, int row, boolean collision) {
        int tileWidth = gp.originalTileSize;
        int tileHeight = gp.originalTileSize;

        int x = col * tileWidth;
        int y = row * tileHeight;

        Tile t = new Tile();
        t.image = tileSheet.getSubimage(x, y, tileWidth, tileHeight);
        t.collision = collision;

        // store in first empty slot
        for (int i = 0; i < tile.length; i++) {
            if (tile[i] == null) {
                tile[i] = t;
                break;
            }
        }
    }

    public void getTileImage() {
        addTileFromSheet(0, 0, false); // grass
        addTileFromSheet(1, 0, true); // wall
        addTileFromSheet(2, 0, true); // water
        addTileFromSheet(3, 0, false); // earth
        addTileFromSheet(4, 0, true); // tree
        addTileFromSheet(5, 0, false); // sand
    }

    public void loadMap(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
