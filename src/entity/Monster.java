package entity;

import main.GamePanel;

public class Monster extends Entity {
    protected int chaseRange;
    protected int attackRange;
    protected int attackDamage;
    protected int attackCooldown;
    protected int attackCounter = 0;

    public Monster(GamePanel gp) {
        super(gp);
    }   
    public Monster(GamePanel gp, int chaseRange, int attackRange, int attackDamage, int attackCooldown) {
        super(gp);
        this.chaseRange = chaseRange;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        this.attackCooldown = attackCooldown;
    }
}