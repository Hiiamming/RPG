package main;

import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.awt.Font;
import java.awt.Color;


public class UI {
    
    private GamePanel gp;
    private Font arial_40;
    private double playTime;

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
        g2.drawString("Health: " + gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife(), 100, 200);
        g2.drawString("Mana: " + gp.getPlayer().getMana() + "/" + gp.getPlayer().getMaxMana(), 100, 250);
        g2.drawString("Exp: " + gp.getPlayer().getExp() + "/" + gp.getPlayer().getMaxExp() + " Level" + gp.getPlayer().getLevel(), 100, 300);
        g2.drawString("Def: "+gp.getPlayer().getDef(), 100, 350);
    }
}
