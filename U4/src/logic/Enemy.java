package src.logic;
import java.awt.Point;

public abstract class Enemy {
    // Enemies will hop single file.
    
    public static enum Type {
        TEST;
    }
    
    public Type type;
    public int jumpDelay;
    public int damage; 
    public int health;
    public int pathIndex = 0;

    public abstract Point jumpTile(Point[] path);    
    // we could just set their damage to their current health 
    
    public boolean takeDamage(int d) {
        this.health -= d;
        return this.health <= 0;
    };

    public int getJumpDelay() {
        return this.jumpDelay;
    };
    
    public int getDamage() {
        return this.damage;
    };
    
    public int getHealth() {
        return this.health;
    };


    public int getPathIndex(){
        return this.pathIndex;
    }
}
