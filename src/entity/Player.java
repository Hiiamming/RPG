package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    private String heroType;
    public boolean isAttacking = false;
    public int attackRange = 100;

    // mana
    public int manaRegen = 1; // Mana regeneration per second

    // cooldown
    public boolean skillCooldown = false; // Whether the skill is on cooldown
    public int skillCooldownDuration = 60; // Cooldown duration in frames (1 second at 60 FPS)
    public int lastSkillTime = 0; // Time when the skill was last used

    public List<Projectile> projectiles = new ArrayList<>();

    public Player(GamePanel gp, KeyHandler keyH, String heroType) {
        super(gp);  
        this.keyH = keyH; 
        this.heroType = heroType;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2 );

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 32;
        solidArea.width = 64;
        solidArea.height = 64;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        switch (heroType.toLowerCase()) {
            case "sorceress":
                worldX = gp.tileSize * 3;
                worldY = gp.tileSize * 3;
                speed = 15;
                direction = "left";
                // Player status
                maxLife = 100000;
                life = maxLife; 
                atk = 50;
                def = 2;
                maxMana = 100;
                Mana = maxMana;
                maxExp = 10;
                exp = 0;
                level = 1;
                break;
            case "warrior":
                worldX = gp.tileSize * 3;
                worldY = gp.tileSize * 3;
                speed = 3;
                direction = "right";
                // Player status
                maxLife = 8;
                life = maxLife;
                atk = 3;
                def = 2;
                maxMana = 3;
                Mana = maxMana;
                maxExp = 10;
                exp = 0;
                level = 1;
                break;
            // Uncomment and implement for other heroes
            /*
            case "archer":
                worldX = gp.tileSize * 7;
                worldY = gp.tileSize * 7;
                speed = 5;
                direction = "up";
                // Player status
                maxLife = 5;
                life = maxLife;
                break;
            case "rogue":
                worldX = gp.tileSize * 9;
                worldY = gp.tileSize * 9;
                speed = 6;
                direction = "down";
                // Player status
                maxLife = 4;
                life = maxLife;
                break;
            */
            default:
                // Default values if heroType is unrecognized
                worldX = gp.tileSize * 3;
                worldY = gp.tileSize * 3;
                speed = 4;
                direction = "left";
                maxLife = 6;
                life = maxLife;
                break;
        }

    }

    public void getPlayerImage() {

        try {
            switch (heroType.toLowerCase()) {
                case "sorceress":
                    up1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_1.png"));
                    up2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_2.png"));
                    down1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_1.png"));
                    down2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_2.png"));
                    left1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_1.png"));
                    left2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_2.png"));
                    right1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_1.png"));
                    right2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_2.png"));
                    break;
                case "warrior":
                    up1 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_up_1.png"));
                    up2 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_up_2.png"));
                    down1 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_down_1.png"));
                    down2 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_down_2.png"));
                    left1 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_left_1.png"));
                    left2 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_left_2.png"));
                    right1 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_right_1.png"));
                    right2 = ImageIO.read(getClass().getResourceAsStream("/player/warrior_right_2.png"));
                    break;
                // Uncomment and implement for other heroes
                /*
                case "archer":
                    up1 = ImageIO.read(getClass().getResourceAsStream("/player/archer_up_1.png"));
                    up2 = ImageIO.read(getClass().getResourceAsStream("/player/archer_up_2.png"));
                    down1 = ImageIO.read(getClass().getResourceAsStream("/player/archer_down_1.png"));
                    down2 = ImageIO.read(getClass().getResourceAsStream("/player/archer_down_2.png"));
                    left1 = ImageIO.read(getClass().getResourceAsStream("/player/archer_left_1.png"));
                    left2 = ImageIO.read(getClass().getResourceAsStream("/player/archer_left_2.png"));
                    right1 = ImageIO.read(getClass().getResourceAsStream("/player/archer_right_1.png"));
                    right2 = ImageIO.read(getClass().getResourceAsStream("/player/archer_right_2.png"));
                    break;
                case "rogue":
                    up1 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_up_1.png"));
                    up2 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_up_2.png"));
                    down1 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_down_1.png"));
                    down2 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_down_2.png"));
                    left1 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_left_1.png"));
                    left2 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_left_2.png"));
                    right1 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_right_1.png"));
                    right2 = ImageIO.read(getClass().getResourceAsStream("/player/rogue_right_2.png"));
                    break;
                */
                default:
                    // Default images (Sorceress)
                    up1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_1.png"));
                    up2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_2.png"));
                    down1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_1.png"));
                    down2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_2.png"));
                    left1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_1.png"));
                    left2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_2.png"));
                    right1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_1.png"));
                    right2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_2.png"));
                    break;
            }
		} catch (IOException e) {}
        }

    public void update() {

        boolean isStanding = true;

        // Moving in one direction
        if (keyH.upPressed) {
            direction = "up";
            isStanding = false;
        } else if (keyH.downPressed) {
            direction = "down";
            isStanding = false;
        } else if (keyH.leftPressed) {
            direction = "left";
            isStanding = false;
        } else if (keyH.rightPressed) {
            direction = "right";
            isStanding = false;
        }

		// Collision detection
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn && !isStanding) {
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
            
        }

        if (keyH.attackPressed) {
            isAttacking = true;
            attackMonster();
        } else {
            isAttacking = false;
        }

        if (keyH.shootPressed) {
            shootProjectile();
        }

        // Update projectiles
        for (Projectile projectile : projectiles) {
            projectile.update();
        }

        // Remove inactive projectiles
        projectiles.removeIf(projectile -> !projectile.alive);
        
        // Regenerate mana
        regenerateMana();

        updateSkillCooldown();  

        for (Entity monster : gp.monsters) {
            if (gp.cChecker.checkEntityCollision(this, monster)) {
                // Monster attacks the player
                monster.attackPlayer(); 
            }
        }


        // Sprite animation (optional)
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // check if the player is dead
        if (this.life <= 0) {
            die();
        }
    }

    public void shootProjectile() {
        int manaCost = 100; // Mana cost for shooting a projectile

        if (Mana >= manaCost && !skillCooldown) {
            // Create a new projectile at the player's position and direction
            Projectile projectile = new Projectile(gp, worldX, worldY, direction);
            projectiles.add(projectile);

            // Consume mana
            Mana -= manaCost;
            System.out.println("Mana used: " + manaCost + ". Remaining mana: " + Mana);

            // Start cooldown
            startCooldown();
        } else if (skillCooldown) {
            System.out.println("Skill is on cooldown!");
        } else {
            System.out.println("Not enough mana to shoot!");
        }
    }

    public void regenerateMana() {
        if (Mana < maxMana) {
            Mana += manaRegen;
            if (Mana > maxMana) {
                Mana = maxMana;
            }
            System.out.println("Mana regenerated: " + manaRegen + ". Current mana: " + Mana);
        }
    }

    public void startCooldown() {
        skillCooldown = true;
        lastSkillTime = gp.gameTime; // Record the current game time
    }

    public void updateSkillCooldown() {
        if (skillCooldown) {
            // Check if the cooldown duration has passed
            if (gp.gameTime - lastSkillTime >= skillCooldownDuration) {
                skillCooldown = false; // Reset cooldown
                System.out.println("Skill cooldown ended!");
            }
        }
    }

    public void attackMonster() {
        Iterator<Entity> iterator = gp.monsters.iterator();
        while (iterator.hasNext()) {
            Entity monster = iterator.next();
            double distance = getDistance(this.worldX, this.worldY, monster.worldX, monster.worldY);
            if (distance <= attackRange) {
                monster.receiveDamage(this.atk);
                // If the monster dies, it will be removed from the list in its die() method
                if (monster.isDead) {
                    gainExp(monster.maxExp); // Gain experience when monster dies
                }
            }
        }
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        double dx = (double) (x1 - x2);
        double dy = (double) (y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void die() {
        System.out.println("Player has died! Game Over.");
        // Play death sound
        // soundManager.playSound("/sounds/player_death.wav");

        // Stop the game thread
        gp.gameThread = null;

        // Display game over screen
        gp.gameState = gp.STATE_GAME_OVER;
    }

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
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            System.out.println("Image is null for direction: " + direction);
            // Optionally, draw a placeholder (e.g., a rectangle)
            g2.setColor(Color.RED);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }

        // Draw projectiles
        for (Projectile projectile : projectiles) {
            projectile.draw(g2);
        }

    }
    
}
