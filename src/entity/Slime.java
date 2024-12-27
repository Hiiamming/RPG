package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Slime extends Entity {

    public Slime(GamePanel gp, int spawnCol, int spawnRow) {
        super(gp);
        setDefaultValues(spawnCol, spawnRow);
        getSlimeImage();
    }

    public void setDefaultValues(int spawnCol, int spawnRow) {
        worldX = gp.tileSize * spawnCol;
        worldY = gp.tileSize * spawnRow;
        speed = 2;
        direction = "down";

        // Define the solid area for collision
        solidArea = new Rectangle(16, 32, 64, 64); // Adjust as needed

        // Slime status
        maxLife = 3;
        life = maxLife;
        atk = 1;
        def = 1;
        maxMana = 0;
        Mana = 0;
        maxExp = 10;
        exp = 0;
        level = 1;
    }

    public void getSlimeImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monster/slime_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // Basic movement logic: move in the current direction
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (!collisionOn) {
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
        } else {
            // Change direction randomly upon collision
            changeDirection();
        }

        // Sprite animation
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        // Optional: Implement AI behaviors (e.g., chasing player)
    }

    private void changeDirection() {
        int rand = (int) (Math.random() * 4) + 1;
        switch (rand) {
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
        }
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
            g2.setColor(Color.GREEN);
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            g2.fillOval(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }
}
