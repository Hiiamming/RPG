package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Projectile extends Entity {
    public boolean isActive = true; // Whether the projectile is active
    public BufferedImage image;

    public Projectile(GamePanel gp, int startX, int startY, String direction) {
        super(gp);
        this.setWorldX(startX);
        this.setWorldY(startY);
        this.setDirection(direction);
        this.setSpeed(10);
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
        switch (getDirection()) {
            case "up":
                setWorldY(getWorldY() - getSpeed());
                break;
            case "down":
                setWorldY(getWorldY() + getSpeed());
                break;
            case "left":
                setWorldX(getWorldX() - getSpeed());
                break;
            case "right":
                setWorldX(getWorldX() + getSpeed());
                break;
        }

        // Check if the projectile is out of bounds
        if (getWorldX() < 0 || getWorldX() > gp.worldWidth || getWorldY() < 0 || getWorldY() > gp.worldHeight) {
            isActive = false; // Deactivate the projectile
        }

            // Check for collisions with monsters
        for (Entity monster : gp.getMonsters()) {
            if (gp.getcChecker().checkEntityCollision(this, monster)) {
                monster.receiveDamage(5); // Deal damage to the monster
                isActive = false; // Deactivate the projectile
                break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (isActive) {
            int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            // Draw the projectile if it's within the screen
            if (getWorldX() + gp.tileSize > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                getWorldX() - gp.tileSize < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                getWorldY() + gp.tileSize > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                getWorldY() - gp.tileSize < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {

                g2.drawImage(image, screenX, screenY, gp.tileSize / 2, gp.tileSize / 2, null);
            }
        }
    }
}