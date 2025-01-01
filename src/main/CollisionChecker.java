package main;

import java.awt.Rectangle;

import entity.Entity;

public class CollisionChecker{
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Calculate the edges of the entity's solid area in world coordinates
        int entityLeftWorldX = entity.getWorldX() + entity.solidArea.x;
        int entityRightWorldX = entity.getWorldX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldY() + entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldY() + entity.solidArea.y + entity.solidArea.height;

        // Determine the column and row of each edge
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.getTileM().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().mapTileNum[entityRightCol][entityTopRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.getTileM().mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileM().mapTileNum[entityRightCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.getTileM().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.getTileM().mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.getTileM().mapTileNum[entityRightCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break; 
        }
    }
    public boolean checkEntityCollision(Entity entity1, Entity entity2) {
        Rectangle area1 = entity1.solidArea;
        Rectangle area2 = entity2.solidArea;

        area1.x = entity1.getWorldX() + entity1.solidArea.x;
        area1.y = entity1.getWorldY() + entity1.solidArea.y;
        area2.x = entity2.getWorldX() + entity2.solidArea.x;
        area2.y = entity2.getWorldY() + entity2.solidArea.y;

        boolean collision = area1.intersects(area2);

        // Reset areas to default if needed
        entity1.solidArea.x = 16;
        entity1.solidArea.y = 32;
        entity2.solidArea.x = 16;
        entity2.solidArea.y = 32;

        return collision;
    }
}


