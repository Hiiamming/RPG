import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    GamePanel gp;

    public int worldX, worldY; // spawn position
    public int speed;

    public BufferedImage up1, up2, up3, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean isDead = false; // Add this flag to check if the entity is dead

    // Player and Monster status
    public int maxLife;
    public int life;
    public int atk;
    public int def;
    public int maxMana;
    public int Mana;
    public int maxExp;
    public int exp;
    public int level;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Method to update the entity (to be overridden by subclasses)
    public void update() {
        // Default behavior (can be overridden)
    }

    // Method to draw the entity (to be overridden by subclasses)
    public void draw(Graphics2D g2) {
        // Default behavior (can be overridden)
    }

    public void attackPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attackPlayer'");
    }

    public void receiveDamage(int damage) {
        int actualDamage = damage - this.def;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        life -= actualDamage;
        if (life < 0) {
            life = 0;
        }
        System.out.println("Entity receives " + actualDamage + " damage! Current life: " + life);

        if (life <= 0) {
            die();
        }
    }

    public void die() {
        System.out.println("Entity has died!");
        isDead = true;
        // Handle death (e.g., remove from game, play death animation, etc.)
    }
}