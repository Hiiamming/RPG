package entity;

public class Hero {
    public String type;
    public int startX;
    public int startY;
    public int speed;
    public String direction;
    public int maxLife;

    public Hero(String type, int startX, int startY, int speed, String direction, int maxLife) {
        this.type = type;
        this.startX = startX;
        this.startY = startY;
        this.speed = speed;
        this.direction = direction;
        this.maxLife = maxLife;
    }
}
