package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Monster extends Entity {
    protected int attackDamage;
    protected int attackRange;
    protected int attackCooldown;
    protected int attackCounter = 0;

    // AI Parameters
    protected int chaseRange = 200; // Pixels within which the monster starts chasing the hero
    protected int aiCounter = 0;
    protected int aiInterval = 1; // Recalculate direction every 60 frames (~1 second at 60 FPS)

    public Monster(GamePanel gp) {
        super(gp);
        setDefaultValues();
        getMonsterImage();
        setupSolidArea();
    }

    // Abstract methods for subclasses to implement
    protected abstract void setDefaultValues();
    protected abstract void getMonsterImage();

    // Setup the solid area for collision detection
    private void setupSolidArea() {
        solidArea = new Rectangle(16, 32, 64, 64); // Adjust based on monster size
    }

    @Override
    public void update() {
        if (!isDead) {
            aiCounter++;
            if (aiCounter >= aiInterval) {
                decideNextAction();
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
    }

    // Decide the next action based on hero's position
    protected void decideNextAction() {
        double distance = getDistance(gp.player.getWorldX(), gp.player.getWorldY(), this.getWorldX(), this.getWorldY());
        if (distance < chaseRange) {
            if (distance < attackRange) {
                // Attempt to attack if cooldown has elapsed
                if (attackCounter == 0) {
                    attack(gp.player);
                    attackCounter = attackCooldown;
                }
            } else {
                // Chase the hero
                chaseHero();
            }
        } else {
            // Implement patrol or idle behavior
            patrol();
        }
    }

    // Chase the hero by setting direction towards the hero
    protected void chaseHero() {
        int playerX = gp.player.getWorldX();
        int playerY = gp.player.getWorldY();

        int diffX = playerX - this.getWorldX();
        int diffY = playerY - this.getWorldY();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX < 0) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
        } else {
            if (diffY < 0) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN;
            }
        }

        move();
    }

    // Patrol behavior (random movement)
    protected void patrol() {
        // Randomly change direction occasionally
        if (Math.random() < 0.1) { // 10% chance to change direction each update
            direction = getRandomDirection();
        }
        move();
    }

    // Move in the current direction if no collision
    protected void move() {
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
            switch (direction) {
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
        }
    }

    // Get a random direction
    protected Direction getRandomDirection() {
        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return Direction.DOWN;
        }
    }

    // Calculate distance between two points
    protected double getDistance(int x1, int y1, int x2, int y2) {
        double dx = (double) (x1 - x2);
        double dy = (double) (y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Attack method implementation
    @Override
    public void attack(Entity target) {
        if (target != null && !target.isDead()) {
            int damage = this.atk - target.getDef();
            if (damage < 0) damage = 0; // Ensure damage is not negative
            target.receiveDamage(damage);
            System.out.println(this.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
        }
    }

    @Override
    public String getName() {
        return "Monster"; // Default name, should be overridden by subclasses
    }

    // Drawing method remains abstract
    @Override
    public abstract void draw(Graphics2D g2);
}
