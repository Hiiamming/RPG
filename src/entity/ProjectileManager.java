package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectileManager {
    private List<Projectile> projectiles = new ArrayList<>();
    private GamePanel gp;

    public ProjectileManager(GamePanel gp) {
        this.gp = gp;
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void updateProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while(iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.update();
            if(!projectile.isActive()) {
                iterator.remove();
            }
        }
    }

    public void drawProjectiles(Graphics2D g2) {
        for(Projectile projectile : projectiles) {
            projectile.draw(g2);
        }
    }
    
    public void clearProjectiles() {
        projectiles.clear();
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }
}
