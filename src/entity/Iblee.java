package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Iblee extends Entity {

    // Boss-specific parameters
    private final int chaseRange = 500; // Pixels within which Iblee starts chasing the player
    private final int attackRange = 200; // Pixels within which Iblee can attack
    private final int normalAttackDamage = 50; // Damage inflicted by normal attack
    private final int specialAttackDamage = 100; // Damage inflicted by special attack
    private final int attackCooldown = 10; // Frames between attacks (~2 seconds at 60 FPS)
    private int attackCounter = 0; // Counter to manage cooldown

    // AI counters for optimizing behavior
    private int aiCounter = 0;
    private final int aiInterval = 1; // Recalculate direction every 60 frames (~1 second at 60 FPS)1

    public Iblee(GamePanel gp, int spawnCol, int spawnRow) {
        super(gp);
        setDefaultValues(spawnCol, spawnRow);
        getIbleeImage();
    }

    public void setDefaultValues(int spawnCol, int spawnRow) {
        worldX = gp.tileSize * spawnCol;
        worldY = gp.tileSize * spawnRow;
        speed = 3;
        direction = "down";

        // Define the solid area for collision
        solidArea = new Rectangle(16, 32, 64, 64); // Adjust as needed

        // Iblee status
        maxLife = 1000;
        life = maxLife;
        atk = normalAttackDamage; // Set normal attack damage
        def = 10;
        maxMana = 0;
        Mana = 0;
        maxExp = 500;
        exp = 0;
        level = 10;
    }

    public void getIbleeImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // Determine if the player is within chase range
        double distance = getDistance(gp.player.worldX, gp.player.worldY, this.worldX, this.worldY);
        if (distance < chaseRange) {
            aiCounter++;
            if (aiCounter >= aiInterval) {
                if (distance < attackRange) {
                    // Player is within attack range
                    if (attackCounter == 0) { // Check if cooldown has passed
                        if (Math.random() < 0.3) { // 30% chance to use special attack
                            specialAttack();
                        } else {
                            attackPlayer();
                        }
                        attackCounter = attackCooldown; // Reset cooldown
                    }
                } else {
                    // Player is within chase range but not attack range
                    chasePlayer();
                }
                aiCounter = 0;
            }
        } else {
            // Basic random movement
            basicMovement();
            aiCounter = 0;
        }

        // Handle attack cooldown
        if (attackCounter > 0) {
            attackCounter--;
        }

        // Sprite animation
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    private void basicMovement() {
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
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
        } else {
            // Change direction randomly upon collision
            changeDirection();
        }
    }

    private void chasePlayer() {
        int playerX = gp.player.worldX;
        int playerY = gp.player.worldY;

        // Calculate the difference in positions
        int diffX = playerX - this.worldX;
        int diffY = playerY - this.worldY;

        // Determine direction based on the largest difference
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX < 0) {
                direction = "left";
            } else {
                direction = "right";
            }
        } else {
            if (diffY < 0) {
                direction = "up";
            } else {
                direction = "down";
            }
        }

        // Attempt to move in the calculated direction
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
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
        } else {
            // If collision occurs while chasing, try to change direction
            changeDirection();
        }
    }

    public void attackPlayer() {
        int actualDamage = normalAttackDamage - gp.player.def;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        gp.player.life -= actualDamage;
        if (gp.player.life < 0) {
            gp.player.life = 0;
        }
        System.out.println("Iblee uses Normal Attack! Player's life: " + gp.player.life);
    }

    public void specialAttack() {
        int actualDamage = specialAttackDamage - gp.player.def;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        gp.player.life -= actualDamage;
        if (gp.player.life < 0) {
            gp.player.life = 0;
        }
        System.out.println("Iblee uses Special Attack! Player's life: " + gp.player.life);
    }

    private void changeDirection() {
        int rand = (int) (Math.random() * 4) + 1;
        switch (rand) {
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
        }
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        double dx = (double) (x1 - x2);
        double dy = (double) (y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }

        if (image != null) {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw only if within the visible screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        } else {
            // Placeholder if image not found
            g2.setColor(Color.RED);
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            g2.fillOval(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    @Override
    public void die() {
        System.out.println("Iblee has been defeated!");
        isDead = true;
    }
}