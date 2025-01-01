package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sorceress extends Hero {

    // Attack-related variables
    private boolean isAttacking = false; // Whether the Sorceress is currently attacking
    private int attackRange = 50; // Range of the attack (in pixels)
    private int attackDamage = 15; // Damage dealt by the attack
    private int attackCooldown = 30; // Cooldown between attacks (in frames)
    private int attackCounter = 0; // Counter to manage attack cooldown

    // Skill-related variables
    private boolean skillActive = false; // Whether the skill is currently active
    private int skillCooldownDuration = 300; // Cooldown duration in frames (5 seconds at 60 FPS)
    private int lastSkillTime = 0; // Time when the skill was last used
    private boolean skillCooldown = false; // Whether the skill is on cooldown

    // Projectile-related variables
    private List<Projectile> projectiles = new ArrayList<>(); // List of active projectiles
    private int projectileSpeed = 10; // Speed of the projectile
    private int projectileDamage = 20; // Damage dealt by the projectile

    public Sorceress(GamePanel gp) {
        super(gp);
    }

    // Update the Sorceress's state
    @Override
    public void update() {
        super.update(); // Call the parent class's update method
        updateAttack(); // Update attack state
        updateSkill(); // Update skill state and projectiles
    }

    // Handle the Sorceress's attack
    public void updateAttack() {
        if (isAttacking && attackCounter == 0) {
            // Check for monsters within attack range
            Iterator<Entity> iterator = gp.getMonsters().iterator();
            while (iterator.hasNext()) {
                Entity monster = iterator.next();
                double distance = getDistance(getWorldX(), getWorldY(), monster.getWorldX(), monster.getWorldY());
                if (distance <= attackRange) {
                    monster.receiveDamage(attackDamage); // Deal damage to the monster
                    System.out.println("Sorceress attacks! Monster takes " + attackDamage + " damage.");
                }
            }

            // Start attack cooldown
            attackCounter = attackCooldown;
            isAttacking = false;
        }

        // Decrease attack cooldown counter
        if (attackCounter > 0) {
            attackCounter--;
        }
    }

    // Activate the Sorceress's attack
    public void attack() {
        isAttacking = true;
    }

    // Activate the Sorceress's skill (shoot a projectile)
    @Override
    public void useUltimate() {
        if (!skillCooldown && getMana() >= 20) { // Check if skill is not on cooldown and has enough mana
            // Create a new projectile
            Projectile projectile = new Projectile(gp, getWorldX(), getWorldY(), getDirection());
            projectiles.add(projectile);

            // Consume mana
            setMana(getMana() - 20);

            // Start cooldown
            skillCooldown = true;
            lastSkillTime = gp.gameTime;

            System.out.println("Sorceress shoots a projectile!");
        } else if (skillCooldown) {
            System.out.println("Skill is on cooldown!");
        } else {
            System.out.println("Not enough mana to use skill!");
        }
    }

    // Update skill state (cooldown) and projectiles
    public void updateSkill() {
        if (skillCooldown) {
            // Check if the cooldown duration has passed
            if (gp.gameTime - lastSkillTime >= skillCooldownDuration) {
                skillCooldown = false;
                System.out.println("Skill cooldown ended!");
            }
        }

        // Update projectiles
        for (Projectile projectile : projectiles) {
            projectile.update();
        }

        // Remove inactive projectiles
        projectiles.removeIf(projectile -> !projectile.isActive);
    }

    // Draw the Sorceress and projectiles
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2); // Call the parent class's draw method

        // Draw projectiles
        for (Projectile projectile : projectiles) {
            projectile.draw(g2);
        }
    }

    // Calculate the distance between two points
    private double getDistance(int x1, int y1, int x2, int y2) {
        double dx = (double) (x1 - x2);
        double dy = (double) (y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
