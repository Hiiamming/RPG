package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import entity.Slime;
import entity.SkeletonBoss;
import main.GamePanel;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile; // Array to hold different tile types
    public int mapTileNum[][];
    
    public TileManager(GamePanel gp)  {
        this.gp = gp;
        
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        getTileImage();
        loadMap("/maps/world01.txt");
    }
        
    public void getTileImage()  {
        try {
            // Define tiles with their images and collision properties
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[0].collision = false;
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
            tile[3].collision = false;
            
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
            tile[4].collision = false;
            
            // Define tile[5] as a Slime spawn point
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); // Use grass or any other tile
            tile[5].collision = false; // Monsters can walk on it
            
            // Define tile[6] as a SkeletonBoss spawn point
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); // Use grass or any other tile
            tile[6].collision = false; // Monsters can walk on it
        } catch(IOException e)  {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
            int col = 0;
            int row = 0;
    
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;
    
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
    
    public void draw(Graphics2D g2)  {
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)  {
            
            int tileNum = mapTileNum[worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
            
            // Render tiles within the visible screen area
            if (worldX + gp.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - gp.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + gp.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - gp.tileSize < gp.player.getWorldY() + gp.player.getScreenY())  {
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            
            worldCol++;
            
            if(worldCol == gp.maxWorldCol)  {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
