package src.logic.enemies;
import src.logic.Enemy;
import src.logic.Tile;
import java.awt.Point;

public class EnemyDefault extends Enemy{
    
    public Type type = Type.DEFAULT;
    
    public EnemyDefault(Point[] path, int startHealth){
        super(path);
        jumpDelay = 900;
        health = startHealth;
        jumpInterval.setInterval(jumpDelay);
        
    } 

    public int getDamage(){ // some other ones could have like a modifier on them
        return this.health;
    }
}
