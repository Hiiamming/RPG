package main;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;

public class UI {

    GamePanel gp;
    Font arial_40;
    long startTime; // Track the start time of the game
    long elapsedTime; // Track elapsed time in milliseconds

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        startTime = System.currentTimeMillis(); // Initialize start time
    }

    public void draw(Graphics2D g2) {
        if (gp.player == null) {
            return; // Don't draw UI if the player is not initialized
        }

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        // Calculate elapsed time in seconds
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        // Draw time
        g2.drawString("Play Time: " + elapsedTime + "s", 100, 150);

        // Draw health
        g2.drawString("Health: " + gp.player.getLife() + "/" + gp.player.getMaxLife(), 100, 200);

        // Draw mana
        g2.drawString("Mana: " + gp.player.getMana() + "/" + gp.player.getMaxMana(), 100, 250);

        // Draw experience and level
        g2.drawString("Exp: " + gp.player.getExp() + "/" + gp.player.getMaxExp() + " Level " + gp.player.getLevel(), 100, 300);
    }
}