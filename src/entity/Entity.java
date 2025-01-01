package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    GamePanel gp;

    private int worldX, worldY; // spawn position

    public BufferedImage up1, up2, down3, down1, down2, left1, left2, right1, right2;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Collision
    public Rectangle solidArea; 
    public boolean collisionOn = false;

    public boolean isDead = false; // Flag to check if entity is dead

    // Player and Monster status
    private String name;
    private int maxLife;
    private int life;
    private int atk;
    private int def;
    private int level;
    private int speed;
    private int maxExp;
    private int exp;
    private String direction;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public Entity(String name2, int speed2, int maxLife2, int atk2, int def2, int level2,int maxExp2, String direction2) {

    }

    // Method to update the entity (to be overridden by subclasses)
    public void update() {}

    // Method to draw the entity (to be overridden by subclasses)
    public void draw(Graphics2D g2) {}

    public void attackPlayer(Entity target) {
        System.out.println(name + " performs a basic attack on " + target.getName() + ", dealing " + atk + " damage.");
        target.receiveDamage(atk);
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
    }

        // Getters and setters
    public int getScreenX()
    {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        return screenX;
    }
    public int getScreenY()
    {
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
        return screenY;
    }
    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }   
    public void setName(String name) {
        this.name = name;
    }  
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getWorldX() {
        return worldX;
    }
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public int getMaxExp() {
        return maxExp;
    }
    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }

}
