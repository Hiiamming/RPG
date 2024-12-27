package main;

import java.awt.Rectangle;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Calculate the edges of the entity's solid area in world coordinates
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Determine the column and row of each edge
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                System.out.println("Checking tiles for 'up' direction: " + tileNum1 + ", " + tileNum2);
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                    System.out.println("Collision detected for 'up' direction.");
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                System.out.println("Checking tiles for 'down' direction: " + tileNum1 + ", " + tileNum2);
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                    System.out.println("Collision detected for 'down' direction.");
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                System.out.println("Checking tiles for 'left' direction: " + tileNum1 + ", " + tileNum2);
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                    System.out.println("Collision detected for 'left' direction.");
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                System.out.println("Checking tiles for 'right' direction: " + tileNum1 + ", " + tileNum2);
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                    System.out.println("Collision detected for 'right' direction.");
                }
                break; 
        }
    }
    public boolean checkEntityCollision(Entity entity1, Entity entity2) {
        Rectangle area1 = entity1.solidArea;
        Rectangle area2 = entity2.solidArea;

        area1.x = entity1.worldX + entity1.solidArea.x;
        area1.y = entity1.worldY + entity1.solidArea.y;
        area2.x = entity2.worldX + entity2.solidArea.x;
        area2.y = entity2.worldY + entity2.solidArea.y;

        boolean collision = area1.intersects(area2);

        // Reset areas to default if needed
        entity1.solidArea.x = 16;
        entity1.solidArea.y = 32;
        entity2.solidArea.x = 16;
        entity2.solidArea.y = 32;

        return collision;
    }
}


