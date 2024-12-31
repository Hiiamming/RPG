package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Projectile extends Entity {

    GamePanel gp;
    public boolean alive = true; // Whether the projectile is active
    public BufferedImage image;

    public Projectile(GamePanel gp, int startX, int startY, String direction) {
        super(gp);
        this.gp = gp;
        this.worldX = startX;
        this.worldY = startY;
        this.direction = direction;
        this.speed = 10; // Speed of the projectile
        loadImage();
        setSolidArea();
    }

    public void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/projectile/projectile.png")); // Load projectile image
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSolidArea() {
        solidArea = new Rectangle(0, 0, 16, 16); // Set the collision area of the projectile
    }

    @Override
    public void update() {
        // Move the projectile based on its direction
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        // Check if the projectile is out of bounds
        if (worldX < 0 || worldX > gp.worldWidth || worldY < 0 || worldY > gp.worldHeight) {
            alive = false; // Deactivate the projectile
        }

            // Check for collisions with monsters
        for (Entity monster : gp.monsters) {
            if (gp.cChecker.checkEntityCollision(this, monster)) {
                monster.receiveDamage(5); // Deal damage to the monster
                alive = false; // Deactivate the projectile
                break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (alive) {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw the projectile if it's within the screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
            }
        }
    }
}