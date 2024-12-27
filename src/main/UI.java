package main;

import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.awt.Font;
import java.awt.Color;


public class UI {
    
    GamePanel gp;
    Font arial_40;
    double playTime;
    DecimalFormat df = new DecimalFormat("0.00"); // Set the format of the decimal

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);


        // Draw time
        playTime += (double)1/60;
        g2.drawString("Play Time: " + df.format(playTime), 100, 150);
        g2.drawString("Health: " + gp.player.life + "/" + gp.player.maxLife, 100, 200);
    }
}
