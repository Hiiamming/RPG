package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sorceress extends Hero {
    
    private int attackCooldown = 60; // Frames between attacks (~1 second at 60 FPS)
    private int attackCounter = 0; // Counter to manage cooldown
    
    public Sorceress(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, "Sorceress");
    }
    
    @Override
    protected void setDefaultValues() {
        this.worldX = gp.tileSize * 3;
        this.worldY = gp.tileSize * 3;
        this.speed = 10;
        this.direction = Direction.LEFT;
        // Hero stats
        this.maxLife = 100;
        this.life = this.maxLife;
        this.atk = 15;
        this.def = 5;
        this.maxMana = 100;
        this.mana = this.maxMana;
        this.maxExp = 50;
        this.exp = 0;
        this.level = 1;
    }
    
    @Override
    protected void getHeroImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void loadSkills() {
        // Initialize Sorceress's special skill: Shoot Projectile
        specialSkill = new ShootProjectileSkill(gp, this);
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
        
        // Handle special skill
        if (keyH.shootPressed && attackCounter == 0 && mana >= 20) { // Mana cost
            activateSpecialSkill(); // Shoot Projectile
            mana -= 20;
            attackCounter = attackCooldown;
        }
        
        if (attackCounter > 0) {
            attackCounter--;
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
    
    @Override
    public void attack(Entity target) {
        super.attack(target);
        // Additional attack logic if needed
    }
    
    @Override
    public String getName() {
        return "Sorceress";
    }
}
