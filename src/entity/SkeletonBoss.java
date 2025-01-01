package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SkeletonBoss extends Monster {

    // Additional attributes for special abilities
    private int specialAttackCooldown = 300; // Frames (~5 seconds at 60 FPS)
    private int specialAttackCounter = 0;

    public SkeletonBoss(GamePanel gp, int spawnCol, int spawnRow) {
        super(gp);
        this.worldX = spawnCol * gp.tileSize;
        this.worldY = spawnRow * gp.tileSize;
        setupStats();
    }

    // Setup SkeletonBoss-specific stats
    private void setupStats() {
        this.speed = 3;
        this.direction = getRandomDirection();
        this.attackDamage = 20;
        this.attackRange = 70;
        this.attackCooldown = 120; // 2 seconds at 60 FPS
        this.maxLife = 150;
        this.life = this.maxLife;
        this.def = 5;
        this.atk = this.attackDamage;
        this.chaseRange = 300; // Larger chase range
    }

    @Override
    protected void setDefaultValues() {
        // Already set in constructor
    }

    @Override
    protected void getMonsterImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonboss_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update() {
        if (!isDead) {
            super.update();

            // Handle special attack cooldown
            if (specialAttackCounter > 0) {
                specialAttackCounter--;
            }

            // Decide to perform special attack if cooldown has elapsed and within range
            double distance = getDistance(gp.player.getWorldX(), gp.player.getWorldY(), this.getWorldX(), this.getWorldY());
            if (distance < attackRange + 50 && specialAttackCounter == 0) { // Extra range for special attack
                performSpecialAttack();
                specialAttackCounter = specialAttackCooldown;
            }
        }
    }

    // Perform special attack (e.g., area damage)
    private void performSpecialAttack() {
        System.out.println(getName() + " performs a Bone Smash!");
        // Implement area damage around the boss
        for (Entity monster : gp.monsters) {
            double distance = getDistance(this.getWorldX(), this.getWorldY(), monster.getWorldX(), monster.getWorldY());
            if (distance < attackRange + 20 && !monster.equals(this)) { // Adjust radius as needed
                monster.receiveDamage(this.atk + 10); // Extra damage for special attack
            }
        }
        // Optionally, implement visual effects or animations here
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
            g2.setColor(Color.MAGENTA);
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    @Override
    public String getName() {
        return "SkeletonBoss";
    }

    @Override
    public void die() {
        super.die();
        // Grant substantial experience to the player
        gp.player.gainExp(this.maxExp);
        // Optionally, trigger game completion or boss defeat sequence
    }
}
