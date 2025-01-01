package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Hero extends Entity {
    protected KeyHandler keyH;
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    
    // Screen position (usually centered)
    protected int screenX;
    protected int screenY;

    // Experience and Leveling
    protected int maxExp;
    protected int exp;
    protected int level;

    // For skill management
    protected Skill specialSkill;
    private String heroType;
    
        public Hero(GamePanel gp, KeyHandler keyH, String heroType) {
            super(gp);
            this.keyH = keyH;
            this.heroType = heroType;
            setDefaultValues();
            calculateScreenPosition();
            getHeroImages();
            loadSkills();
            solidArea = new Rectangle(16, 32, 64, 64); // Adjust values as needed
    }

    // Abstract methods for subclasses to implement
    protected abstract void setDefaultValues();
    protected abstract void getHeroImages();
    protected abstract void loadSkills();

    // Calculate screenX and screenY based on GamePanel's dimensions
    protected void calculateScreenPosition() {
        this.screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        this.screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);
    }


    // Method to gain experience and handle leveling up
    public void gainExp(int gainedExp) {
        this.exp += gainedExp;
        if (this.exp >= this.maxExp) {
            levelUp();
        }
    }

    protected void levelUp() {
        this.level++;
        this.exp -= this.maxExp;
        this.maxExp += 10; // Increment maxExp for next level
        this.maxLife += 5; // Increase stats upon leveling
        this.life = this.maxLife;
        this.atk += 2;
        this.def += 1;
        System.out.println(getName() + " leveled up! Now at level " + this.level);
    }

    // Implement attack method from Attackable interface
    @Override
    public void attack(Entity target) {
        if (target != null && !target.isDead()) {
            int damage = this.atk - target.getDef();
            if (damage < 0) damage = 0; // Ensure damage is not negative
            target.receiveDamage(damage);
            System.out.println(this.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
        }
    }

    // Getter for screen positions
    public int getScreenX() { return screenX; }
    public int getScreenY() { return screenY; }

    // Drawing the hero (common drawing logic)
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
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            // Placeholder if image is missing
            g2.setColor(java.awt.Color.WHITE);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    // Implement special skill activation
    protected abstract void activateSpecialSkill();
}
