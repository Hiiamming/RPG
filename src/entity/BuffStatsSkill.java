package entity;

public class BuffStatsSkill implements Skill {
    private Crusader crusader;
    private int buffDuration = 300; // Frames (~5 seconds at 60 FPS)
    private int originalAtk;
    private int originalDef;

    public BuffStatsSkill(Crusader crusader) {
        this.crusader = crusader;
    }

    @Override
    public void activate() {
        // Increase attack and defense
        originalAtk = crusader.getAtk();
        originalDef = crusader.getDef();
        crusader.setAtk(originalAtk + 10);
        crusader.setDef(originalDef + 5);
        System.out.println("Crusader activated Buff Stats skill! ATK and DEF increased.");
    }

    public void removeBuff() {
        // Revert attack and defense to original values
        crusader.setAtk(originalAtk);
        crusader.setDef(originalDef);
        System.out.println("Crusader's Buff Stats skill has ended! ATK and DEF reverted.");
    }
}
