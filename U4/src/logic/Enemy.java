package src.logic;
import java.awt.Point;
import java.util.Map;
import lib.Interval;
import src.logic.enemies.*;

public abstract class Enemy {
    // Enemies will hop single file.
    
    public static enum Type {
        TEST;
    }

    public static Type[] types = new Type[]{Type.TEST};
    
    public Type type;
    public int jumpDelay = 2000;
    public boolean active = false;
    public int damage; 
    public int health;
    public int pathIndex = 0;
    public int x;
    public int y;
    public Interval jumpInterval = new Interval(jumpDelay);

    public Enemy(Point[] path){
        x = path[0].x;
        y = path[0].y;
    }
    
    public static Enemy getEnemyFromType(Type type, Point[] path) {
        
        switch (type) {
            default:
                return new EnemyTest(path);
        }
        
    }
    
    /** Handles the movement of the enemy itself.
     * @param the Point[] path that the game runs on
     * @return true if at end of path false otherwise
     */
    public Boolean jumpTile(Point[] path) {
        pathIndex += 1;
        if (pathIndex >= path.length) return true;
        
        x = path[pathIndex].x;
        y = path[pathIndex].y;

        return false;
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
