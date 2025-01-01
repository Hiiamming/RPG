package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Slime extends Monster {

    public Slime(GamePanel gp, int spawnCol, int spawnRow) {
        super(gp);
        this.worldX = spawnCol * gp.tileSize;
        this.worldY = spawnRow * gp.tileSize;
        setupStats();
    }

    // Setup Slime-specific stats
    private void setupStats() {
        this.speed = 2;
        this.direction = getRandomDirection();
        this.attackDamage = 10;
        this.attackRange = 50;
        this.attackCooldown = 60; // 1 second at 60 FPS
        this.maxLife = 30;
        this.life = this.maxLife;
        this.def = 2;
        this.atk = this.attackDamage;
    }

    @Override
    protected void setDefaultValues() {
        // Already set in constructor
    }

    @Override
    protected void getMonsterImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update(); // Use the default monster behavior (chase, attack, patrol)
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case UP:
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case DOWN:
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case LEFT:
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case RIGHT:
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }

        if (image != null) {
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

            // Draw only if within the visible screen
            if (worldX + gp.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - gp.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + gp.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - gp.tileSize < gp.player.getWorldY() + gp.player.getScreenY()) {

                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        } else {
            // Placeholder if image not found
            g2.setColor(Color.GREEN);
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
            g2.fillOval(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    @Override
    public String getName() {
        return "Slime";
    }

    @Override
    public void die() {
        super.die();
        // Grant experience to the player
        gp.player.gainExp(this.maxExp);
    }
}
