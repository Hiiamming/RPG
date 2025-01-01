package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Projectile {
    private GamePanel gp;
    private Hero shooter;
    private Direction direction;
    private int speed = 10;
    private int worldX, worldY;
    private BufferedImage image;
    private boolean active = true;
    private int range = 300; // Maximum distance in pixels
    private int distanceTravelled = 0;

    // Define projectile dimensions
    private int width = 16;
    private int height = 16;
    private Rectangle solidArea;
    
        public Projectile(GamePanel gp, Hero shooter, Direction direction) {
            this.gp = gp;
            this.shooter = shooter;
            this.direction = direction;
            this.worldX = shooter.getWorldX() + shooter.getSolidArea().x + shooter.getSolidArea().width / 2 - width / 2;
            this.worldY = shooter.getWorldY() + shooter.getSolidArea().y + shooter.getSolidArea().height / 2 - height / 2;
            this.solidArea = new Rectangle(0, 0, 16, 16); // Adjust collision area
            // Ensure solidArea is not null before accessing it
            if (shooter.getSolidArea() != null) {
                this.worldX = shooter.getWorldX() + shooter.getSolidArea().x + shooter.getSolidArea().width / 2 - width / 2;
                this.worldY = shooter.getWorldY() + shooter.getSolidArea().y + shooter.getSolidArea().height / 2 - height / 2;
            } else {
                // Fallback position if solidArea is null
                this.worldX = shooter.getWorldX();
                this.worldY = shooter.getWorldY();
            }
    
        loadImage();
    }

    private void loadImage() {
        try {
            // Assuming projectiles are named based on direction
            String imagePath = "/projectiles/" + direction.toString().toLowerCase() + ".png";
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to a default projectile image if specific one not found
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/projectiles/default.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        switch(direction) {
            case UP:
                worldY -= speed;
                break;
            case DOWN:
                worldY += speed;
                break;
            case LEFT:
                worldX -= speed;
                break;
            case RIGHT:
                worldX += speed;
                break;
        }

        // Clamp position to game world bounds
        worldX = Math.max(0, Math.min(worldX, gp.worldWidth - width));
        worldY = Math.max(0, Math.min(worldY, gp.worldHeight - height));

        distanceTravelled += speed;

        // Check collision with tiles
        gp.cChecker.checkProjectileCollision(this);
        if (collisionDetected()) {
            active = false;
        }

        // Check collision with monsters
        for(Entity monster : gp.monsters) {
            if(monster.isDead()) continue; // Skip dead monsters
            if(gp.cChecker.checkEntityCollision(this, monster)) {
                monster.receiveDamage(shooter.getAtk());
                active = false;
                break;
            }
        }

        // Deactivate if range exceeded
        if(distanceTravelled >= range) {
            active = false;
        }
    }

    public void draw(Graphics2D g2) {
        if(image != null && active) {
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
            g2.drawImage(image, screenX, screenY, width, height, null); // Adjust size as needed
        }
    }

    public boolean isActive() {
        return active;
    }

    // Getters for position and dimensions
    public int getWorldX() { return worldX; }
    public int getWorldY() { return worldY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    // Collision detection flag
    private boolean collisionDetected = false;
    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }
    public boolean collisionDetected() {
        return collisionDetected;
    }

}
