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
    public int x;
    public int y;

    public Enemy(Point[] path){
        x = path[0].x;
        y = path[0].y;
    }

    
    public Point jumpTile(Point[] path) {
        pathIndex += 1;
        if (pathIndex >= path.length) return null;
        
        x = path[pathIndex].x;
        y = path[pathIndex].y;

        return path[pathIndex];
    };

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
