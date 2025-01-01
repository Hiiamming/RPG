package entity;

public interface Attackable {
    void attack(Entity target);
    void receiveDamage(int damage);
}
