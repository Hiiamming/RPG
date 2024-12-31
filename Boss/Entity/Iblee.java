package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Iblee extends Entity {

    private boolean passiveUsed = false; // Track if the passive has been used
    private long lastUlockTime = 0; // Cooldown for U-lock
    private long lastCorruptionFormTime = 0; // Cooldown for Corruption Form

    public Iblee(GamePanel gp) {
        super(gp);
        setDefaultValues();
        getBossImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 50; // Spawn position
        worldY = gp.tileSize * 50;
        speed = 2;
        direction = "down";

        // Boss stats
        maxLife = 6272;
        life = maxLife;
        atk = 50; // Physical ATK
        def = 20; // PROT (20% damage reduction)
        maxMana = 100; // Magic ATK
        Mana = maxMana;
        maxExp = 0; // Boss doesn't give exp
        exp = 0;
        level = 99; // Boss level
    }

    public void getBossImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/boss/iblee_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // Passive: Bye, have a great time
        if (life <= maxLife * 0.2 && !passiveUsed) {
            life = maxLife;
            passiveUsed = true;
            System.out.println("Iblee used her passive: Full HP restored!");
        }

        // Skill 1: U-lock (Cooldown: 10 minutes)
        if (gp.gameTime - lastUlockTime >= 600) { // 600 seconds = 10 minutes
            uLock();
            lastUlockTime = gp.gameTime;
        }

        // Skill 2: Fuck it, I ball (No cooldown, can be used anytime)
        if (gp.player.life > 0) {
            fuckItIBall();
        }

        // Skill 3: Corruption Form (Cooldown: 10 minutes)
        if (gp.gameTime - lastCorruptionFormTime >= 600) { // 600 seconds = 10 minutes
            corruptionForm();
            lastCorruptionFormTime = gp.gameTime;
        }

        // Basic movement and collision detection
        super.update();
    }

    private void uLock() {
        gp.player.atk = 0; // Set player's Physical ATK to 0
        gp.player.Mana = 0; // Set player's Magic ATK to 0
        System.out.println("Iblee used U-lock: Player's ATK and Magic ATK reduced to 0 for 1 minute!");
        // Reset after 1 minute (60 seconds)
        gp.scheduler.schedule(() -> {
            gp.player.atk = gp.player.maxLife / 2; // Restore player's ATK
            gp.player.Mana = gp.player.maxMana; // Restore player's Magic ATK
            System.out.println("U-lock effect ended: Player's ATK and Magic ATK restored!");
        }, 60 * 1000); // 60 seconds delay
    }

    private void fuckItIBall() {
        int damage = atk + Mana; // 100% Physical ATK + 100% Magic ATK
        gp.player.receiveDamage(damage);
        System.out.println("Iblee used Fuck it, I ball: Dealt " + damage + " damage to the player!");
    }

    private void corruptionForm() {
        atk += (int) (atk * 0.2); // Gain 20% Physical ATK permanently
        Mana += (int) (Mana * 0.2); // Gain 20% Magic ATK permanently
        System.out.println("Iblee used Corruption Form: Gained 20% Physical and Magic ATK permanently!");
    }

    @Override
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
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw only if within the visible screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        } else {
            // Placeholder if image not found
            g2.setColor(Color.RED);
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            g2.fillOval(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }

    @Override
    public void die() {
        System.out.println("Knightmare Corruptor Iblee has been defeated!");
        isDead = true;
        // Handle boss death (e.g., game win, rewards, etc.)
        gp.gameState = gp.STATE_GAME_WIN;
    }
}