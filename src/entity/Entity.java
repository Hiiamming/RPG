package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public abstract class Entity implements Attackable {
    protected GamePanel gp;

    protected int worldX, worldY; // Position
    protected int speed;
    protected Direction direction; // Using Direction enum

    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    protected Rectangle solidArea;
    protected boolean collisionOn = false;
    protected boolean isDead = false;

    // Stats
    protected int maxLife;
    protected int life;
    protected int atk;
    protected int def;
    protected int maxMana;
    protected int mana;
    protected int maxExp;
    protected int exp;
    protected int level;

    // For collision detection
    protected boolean collisionDetected = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // Abstract methods to be implemented by subclasses
    public abstract void update();
    public abstract void draw(Graphics2D g2);
    public abstract String getName();


    
    @Override
    public void receiveDamage(int damage) {
        int actualDamage = damage - this.def;
        if (actualDamage < 0) actualDamage = 0; // Ensure damage is not negative
        life -= actualDamage;
        if (life < 0) life = 0;
        System.out.println(getName() + " receives " + actualDamage + " damage! Current life: " + life);
    
        if (life <= 0) die();
    }

    @Override
    public abstract void attack(Entity target);

    public void die() {
        System.out.println(getName() + " has died!");
        isDead = true;
    }

    // Getters and Setters
    public int getWorldX() { 
        return worldX; 
    }
    public int getWorldY() { 
        return worldY; 
    }
    public void setWorldX(int worldX) { 
        this.worldX = worldX;
    }
    public void setWorldY(int worldY) { 
        this.worldY = worldY;
    }
    public int getSpeed() { 
        return speed; 
    }
    public void setSpeed(int speed) { 
        this.speed = speed; 
    }
    public Direction getDirection() { 
        return direction; 
    }
    public void setDirection(Direction direction) { 
        if(direction != null) {
            this.direction = direction;
        } else {
            System.out.println("Attempted to set null direction. Defaulting to DOWN.");
            this.direction = Direction.DOWN;
        }
    }
    public boolean isDead() { 
        return isDead; 
    }

    // Getters and Setters for Stats
    public int getMaxLife() { return maxLife; }
    public void setMaxLife(int maxLife) { this.maxLife = maxLife; }

    public int getLife() { return life; }
    public void setLife(int life) { this.life = life; }

    public int getAtk() { return atk; }
    public void setAtk(int atk) { this.atk = atk; }

    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }

    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = mana; }

    public int getMaxExp() { return maxExp; }
    public void setMaxExp(int maxExp) { this.maxExp = maxExp; }

    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public boolean isCollisionOn() { return collisionOn; }
    public void setCollisionOn(boolean collisionOn) { this.collisionOn = collisionOn; }

    public Rectangle getSolidArea() {
        return solidArea;
    }
}
