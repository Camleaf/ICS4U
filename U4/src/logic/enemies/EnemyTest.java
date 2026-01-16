package src.logic.enemies;
import src.logic.Enemy;
import src.logic.Tile;
import java.awt.Point;

public class EnemyTest extends Enemy{
    
    public Type type = Type.TEST;
    
    public EnemyTest(){
        super();
        jumpDelay = 200;
        damage = 1;
        health = 1;

    }
    
    public Point jumpTile(Point[] path) {
        pathIndex += 1;
        if (pathIndex >= path.length) return null;

        return path[pathIndex];
    };
   
}
