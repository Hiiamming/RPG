package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Slime extends Monster {

    // Chase and Attack Parameters
    private final int chaseRange = 200; // Pixels within which the Slime starts chasing the player
    private final int attackRange = 50; // Pixels within which the Slime can attack
    private final int attackDamage = 100; // Damage inflicted per attack
    private final int attackCooldown = 60; // Frames between attacks (~1 second at 60 FPS)
    private int attackCounter = 0; // Counter to manage cooldown

    // AI counters for optimizing behavior
    private int aiCounter = 0;
    private final int aiInterval = 1; // Recalculate direction every 60 frames (~1 second at 60 FPS)

    public Slime(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.setWorldX(worldX);
        this.setWorldY(worldY);
    }
    public Slime(GamePanel gp, int spawnCol, int spawnRow, int startX, int startY, String direction) {
        super(gp);
        this.setWorldX(startX);
        this.setWorldY(startY);
        setDefaultValues(spawnCol, spawnRow);
        getSlimeImage();
    }

    
    public void setDefaultValues(int spawnCol, int spawnRow) {
        this.setWorldX(gp.tileSize * spawnCol);
        this.setWorldY(gp.tileSize * spawnRow);
        this.setSpeed(2);
        this.setDirection("down");

        // Define the solid area for collision
        solidArea = new Rectangle(16, 32, 64, 64); // Adjust as needed

        // Slime status
        this.setMaxLife(3);
        this.setLife(this.getMaxLife());
        this.setAtk(attackDamage); // Set attack damage
        this.setDef(1);
        this.setMaxExp(10);
    }
    public void getSlimeImage() {
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
        // Determine if the player is within chase range
        double distance = getDistance(gp.getPlayer().getWorldX(), gp.getPlayer().getWorldY(), this.getWorldX(), this.getWorldY());
        if (distance < chaseRange) {
            aiCounter++;
            if (aiCounter >= aiInterval) {
                if (distance < attackRange) {
                    // Player is within attack range
                    if (attackCounter == 0) { // Check if cooldown has passed
                        attackPlayer();
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
        gp.getcChecker().checkTile(this);

        if (!collisionOn) {
            switch (getDirection()) {
                case "up":
                    setWorldY(getWorldY() - this.getSpeed());
                    break;
                case "down":
                    setWorldY(getWorldY() + this.getSpeed());
                    break;
                case "left":
                    setWorldX(getWorldX() - this.getSpeed());
                    break;
                case "right":
                    setWorldX(getWorldX() + this.getSpeed());
                    break;
            }
        } else {
            // Change direction randomly upon collision
            changeDirection();
        }
    }

    private void chasePlayer() {
        int playerX = gp.getPlayer().getWorldX();
        int playerY = gp.getPlayer().getWorldY();

        // Calculate the difference in positions
        int diffX = playerX - getWorldX();
        int diffY = playerY - getWorldY();

        // Determine direction based on the largest difference
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX < 0) {
                setDirection("left");
            } else {
                setDirection("right");
            }
        } else {
            if (diffY < 0) {
                setDirection("up");
            } else {
                setDirection("down");
            }
        }

        // Attempt to move in the calculated direction
        collisionOn = false;
        gp.getcChecker().checkTile(this);

        if (!collisionOn) {
            switch (getDirection()) {
                case "up":
                setWorldY(getWorldY() - this.getSpeed());
                break;
            case "down":
                setWorldY(getWorldY() + this.getSpeed());
                break;
            case "left":
                setWorldX(getWorldX() - this.getSpeed());
                break;
            case "right":
                setWorldX(getWorldX() + this.getSpeed());
                break;
            }
        } else {
            // If collision occurs while chasing, try to change direction
            changeDirection();
        }
    }

    public void attackPlayer() {
        // Implement attack logic (e.g., reduce player's life)
        int actualDamage = attackDamage - gp.getPlayer().getDef();
        System.out.println(actualDamage);
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        gp.getPlayer().setLife(gp.getPlayer().getLife() - actualDamage);
        if (gp.getPlayer().getLife() < 0) {
            gp.getPlayer().setLife(0);
        }
        System.out.println("Slime attacks the player! Player's life: " + gp.getPlayer().getLife());

        // Optionally, add attack animations, sounds, or effects here
    }

    public void changeDirection() {
        int rand = (int) (Math.random() * 4) + 1;
        switch (rand) {
            case 1:
                setDirection("up");
                break;
            case 2:
                setDirection("down");
                break;
            case 3:
                setDirection("left");
                break;
            case 4:
                setDirection("right");
                break;
        }
    }

    public double getDistance(int x1, int y1, int x2, int y2) {
        double dx = (double) (x1 - x2);
        double dy = (double) (y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (getDirection()) {
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
            int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

    // Draw only if within the visible screen
            if (getWorldX() + gp.tileSize > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                getWorldX() - gp.tileSize < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                getWorldY() + gp.tileSize > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY()&&
                getWorldY() - gp.tileSize < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {

                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}   else {
    // Placeholder if image not found
    g2.setColor(Color.GREEN);
    int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
    int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
    g2.fillOval(screenX, screenY, gp.tileSize, gp.tileSize);
}
    }
    
    @Override
    public void die() {
        System.out.println("Slime has died!");
        isDead = true;
        // Remove the slime from the game
        // gp.monsters.remove(this);
    }
}
