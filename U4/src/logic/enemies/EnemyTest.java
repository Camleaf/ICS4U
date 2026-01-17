package src.logic.enemies;
import src.logic.Enemy;
import src.logic.Tile;
import java.awt.Point;

public class EnemyTest extends Enemy{
    
    public Type type = Type.TEST;
    
    public EnemyTest(Point[] path){
        super(path);
        jumpDelay = 200;
        damage = 1;
        health = 1;

    } 
}
