package entity;

import main.GamePanel;

public class Hero extends Entity {
    private int mana = 0;
    private int maxMana;

    // Attack variables
    private boolean isAttacking; // Whether the Sorceress is currently attacking
    private int attackRange; // Range of the attack (in pixels)
    private int attackCooldown; // Cooldown between attacks (in frames)
    private int attackCounter; // Counter to manage attack cooldown

    // Skill-related variables
    private boolean skillActive;// Whether the skill is currently active
    private int skillCooldownDuration;// Cooldown duration in frames (5 seconds at 60 FPS)
    private int lastSkillTime;// Time when the skill was last used
    private boolean skillCooldown; // Whether the skill is on cooldown


    public int startX;
    public int startY;
    public Hero(GamePanel gp) {
        super(gp);
    }

    public Hero(String name, int speed, int maxLife, int atk, int def, int level, int maxExp, String direction, int maxMana) {
        super(name, speed, maxLife, atk, def, level, maxExp, direction);
        this.maxMana = maxMana;
    }

    public void gainExp(int amount) {
        this.setExp(this.getExp() + amount);
        if (this.getExp() >= this.getMaxExp()) {
            levelUp();
        }
    }
    public  void levelUp() {
        if (this.getLevel() < 5) {
            this.setLevel(this.getLevel() + 1); 
            this.setExp(0);
            this.setMaxExp((int)(this.getMaxExp() * 1.5));
            this.setMaxLife(this.getMaxLife() + 10); // Increase max life
            this.setLife(getMaxLife()); // Heal to full life
            this.setAtk(this.getAtk() + 10);
            this.setDef(this.getDef() + 10);
            this.setMaxMana(this.getMaxMana() + 10);
            this.setMana(this.getMaxMana());
        }
    }

    
    public boolean canUseUltimate() {
        return mana >= maxMana;
    }
    
    public  void useUltimate() {
    }
    
    //getter and setter 
    public int getMana() {
        return mana;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    public int getAttackRange() {
        return attackRange;
    }
    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }
    public int getAttackCooldown() {
        return attackCooldown;
    }
    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }
    public int getAttackCounter() {
        return attackCounter;
    }
    public void setAttackCounter(int attackCounter) {
        this.attackCounter = attackCounter;
    }
    public boolean isSkillActive() {
        return skillActive;
    }
    public void setSkillActive(boolean skillActive) {
        this.skillActive = skillActive;
    }
    public int getSkillCooldownDuration() {
        return skillCooldownDuration;
    }
    public void setSkillCooldownDuration(int skillCooldownDuration) {
        this.skillCooldownDuration = skillCooldownDuration;
    }
    public int getLastSkillTime() {
        return lastSkillTime;
    }
    public void setLastSkillTime(int lastSkillTime) {
        this.lastSkillTime = lastSkillTime;
    }
    public boolean isSkillCooldown() {
        return skillCooldown;
    }
    public void setSkillCooldown(boolean skillCooldown) {
        this.skillCooldown = skillCooldown;
    }

}
