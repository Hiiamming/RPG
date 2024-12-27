package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    private String heroType;

    public Player(GamePanel gp, KeyHandler keyH, String heroType) {
        super(gp);  
        this.keyH = keyH; 
        this.heroType = heroType;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2 );

        solidArea = new Rectangle(0, 0, 96, 96);
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
                speed = 10;
                direction = "left";
                // Player status
                maxLife = 1000000;
                life = maxLife; 
                atk = 3;
                def = 2;
                maxMana = 3;
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

        for (Entity monster : gp.monsters) {
            if (gp.cChecker.checkEntityCollision(this, monster)) {
                // Handle collision (e.g., take damage, attack, etc.)
                System.out.println("Collided with a monster!");
                // Example: Decrease player's life
                this.life -= monster.atk;
                if (this.life <= 0) {
                    this.life = 0;
                    // Handle player death
                }
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

    }
}
