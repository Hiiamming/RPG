package main;

import java.awt.Rectangle;
import entity.Entity;
import entity.Projectile;

public class CollisionChecker {
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Check collision for moving entities (Heroes and Monsters)
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;
    
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
    
        int tileNum1, tileNum2;
    
        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.setCollisionOn(true);

                }
                break;
        }
    }

    // Check collision between two entities (e.g., Hero and Monster)
    public boolean checkEntityCollision(Entity entity1, Entity entity2) {
        Rectangle area1 = entity1.getSolidArea();
        Rectangle area2 = entity2.getSolidArea();

        // Adjust positions based on world coordinates
        area1.setLocation(entity1.getWorldX() + area1.x, entity1.getWorldY() + area1.y);
        area2.setLocation(entity2.getWorldX() + area2.x, entity2.getWorldY() + area2.y);

        boolean collision = area1.intersects(area2);

        return collision;
    }

    // Check collision for projectiles with tiles
    public void checkProjectileCollision(Projectile projectile) {
        int projectileLeftWorldX = projectile.getWorldX();
        int projectileRightWorldX = projectile.getWorldX() + projectile.getWidth();
        int projectileTopWorldY = projectile.getWorldY();
        int projectileBottomWorldY = projectile.getWorldY() + projectile.getHeight();
    
        int projectileLeftCol = projectileLeftWorldX / gp.tileSize;
        int projectileRightCol = projectileRightWorldX / gp.tileSize;
        int projectileTopRow = projectileTopWorldY / gp.tileSize;
        int projectileBottomRow = projectileBottomWorldY / gp.tileSize;
    
        // Ensure indices are within bounds
        projectileLeftCol = Math.max(0, Math.min(projectileLeftCol, gp.maxWorldCol - 1));
        projectileRightCol = Math.max(0, Math.min(projectileRightCol, gp.maxWorldCol - 1));
        projectileTopRow = Math.max(0, Math.min(projectileTopRow, gp.maxWorldRow - 1));
        projectileBottomRow = Math.max(0, Math.min(projectileBottomRow, gp.maxWorldRow - 1));
    
        boolean collisionDetected = false;
        for (int col = projectileLeftCol; col <= projectileRightCol; col++) {
            for (int row = projectileTopRow; row <= projectileBottomRow; row++) {
                if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
                    int tileNum = gp.tileM.mapTileNum[col][row];
                    if (gp.tileM.tile[tileNum].collision) {
                        collisionDetected = true;
                        break;
                    }
                }
            }
            if (collisionDetected) break;
        }
    
        projectile.setCollisionDetected(collisionDetected);
    }

    public boolean checkEntityCollision(Projectile projectile, Entity entity) {
        Rectangle projectileArea = new Rectangle(projectile.getWorldX(), projectile.getWorldY(), projectile.getWidth(), projectile.getHeight());
        Rectangle entityArea = entity.getSolidArea();
    
        // Ensure projectile position is within bounds
        if (projectile.getWorldX() < 0 || projectile.getWorldX() >= gp.worldWidth ||
            projectile.getWorldY() < 0 || projectile.getWorldY() >= gp.worldHeight) {
            return false;
        }
    
        entityArea.setLocation(entity.getWorldX() + entityArea.x, entity.getWorldY() + entityArea.y);
    
        return projectileArea.intersects(entityArea);
    }
}
