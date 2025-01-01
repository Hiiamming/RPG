package entity;

import main.GamePanel;
import java.awt.Point;

public class ShootProjectileSkill implements Skill {
    private GamePanel gp;
    private Sorceress sorceress;
    private int manaCost = 20;
    private int cooldown = 60; // Frames
    private int lastUsed = -cooldown; // Initialize so it can be used immediately

    public ShootProjectileSkill(GamePanel gp, Sorceress sorceress) {
        this.gp = gp;
        this.sorceress = sorceress;
    }

    @Override
    public void activate() {
        // Create a new projectile in the direction the Sorceress is facing
        Projectile projectile = new Projectile(gp, sorceress, sorceress.getDirection());
        gp.projectileManager.addProjectile(projectile);
        System.out.println("Sorceress activated Shoot Projectile skill!");
    }
}
