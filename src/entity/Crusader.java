package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Crusader extends Hero {
    
    private int buffDuration = 300; // Frames (~5 seconds at 60 FPS)
    private int buffCounter = 0;
    private boolean isBuffed = false;
    
    public Crusader(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, "Crusader");
    }
    
    @Override
    protected void setDefaultValues() {
        this.worldX = gp.tileSize * 3;
        this.worldY = gp.tileSize * 3;
        this.speed = 3;
        this.direction = Direction.RIGHT;
        // Hero stats
        this.maxLife = 150;
        this.life = this.maxLife;
        this.atk = 20;
        this.def = 10;
        this.maxMana = 50;
        this.mana = this.maxMana;
        this.maxExp = 50;
        this.exp = 0;
        this.level = 1;
    }
    
    @Override
    protected void getHeroImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/crusader_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void loadSkills() {
        // Initialize Crusader's special skill: Buff Stats
        specialSkill = new BuffStatsSkill(this);
    }
    
    @Override
    public void update() {
        // Handle movement
        boolean isMoving = false;
        
        if (keyH.upPressed) {
            direction = Direction.UP;
            isMoving = true;
        } else if (keyH.downPressed) {
            direction = Direction.DOWN;
            isMoving = true;
        }
        
        if (keyH.leftPressed) {
            direction = Direction.LEFT;
            isMoving = true;
        } else if (keyH.rightPressed) {
            direction = Direction.RIGHT;
            isMoving = true;
        }
        
        if (!isMoving) {
            // Optional: Implement idle state or stop animation
        }

        // Check for collisions before moving
        this.setCollisionOn(false);
        gp.cChecker.checkTile(this);

        // Only move if there is no collision
        if (!this.isCollisionOn() && isMoving) {
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

        // Handle normal attack
        if (keyH.attackPressed) {
            for (Entity monster : gp.monsters) {
                if (!monster.isDead() && gp.cChecker.checkEntityCollision(this, monster)) {
                    attack(monster);
                    break; // Attack only one monster at a time
                }
            }
        }
        
        // Handle special skill activation (buffing)
        if (keyH.buffPressed && !isBuffed && mana >= 30) { // Mana cost
            activateSpecialSkill(); // Buff Stats
            mana -= 30;
            isBuffed = true;
            buffCounter = buffDuration;
        }
        
        if (isBuffed) {
            buffCounter--;
            if (buffCounter <= 0) {
                removeBuff();
                isBuffed = false;
            }
        }
        
        // Sprite animation
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
        
        // Check if dead
        if (life <= 0) {
            die();
        }
    }
    
    @Override
    protected void activateSpecialSkill() {
        if (specialSkill != null) {
            specialSkill.activate();
        }
    }
    
    private void removeBuff() {
        if (specialSkill instanceof BuffStatsSkill) {
            ((BuffStatsSkill) specialSkill).removeBuff();
        }
    }
    
    @Override
    public void attack(Entity target) {
        super.attack(target);
        // Additional attack logic if needed
    }
    
    @Override
    public String getName() {
        return "Crusader";
    }
}
