package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import src.render.EnemyRenderBox;
import java.util.ArrayList;
import java.awt.Point;


/** A lot of implementation still needed
 * 
 */
public class TowerShooter extends Tower{
    
    public static String name = "Shooter";
    
    
    public TowerShooter(){
        super();              
        type = Type.SHOOTER;
        attackDelay = 350; // In milliseconds. Time between each attack
        baseCost = 100; //the price of the tower
        damage = 1;
        range = 2;
        maxTargets = 1;
        textureIndex = 4;
        upgradeLevel = 0;
        attackInterval.setInterval(attackDelay);

    }
    
    //default public Tower(Type t, int a, int b, int d, int i);
    /**
     * Shoots any enemy which is closest to the end of the path and in range
     * @param ArrayList of all the enemies on the board
     */
    public ArrayList<Integer> doAttack(Enemy[] enemies, Point curPosition){
        ArrayList<Integer> enemiesAttacked = new ArrayList<Integer>();

        for (int i = 0;i<enemies.length;i++){
            Enemy enemy = enemies[i]; // iterate from back to front of enemies to get furthest along. Not perfect but should work well enough
            if (!enemy.active) continue;
            
            if (Math.abs(enemy.x - curPosition.x) <= range && Math.abs(enemy.y-curPosition.y)<=range){ // if in range
                enemiesAttacked.add(i);

                if (enemiesAttacked.size()>=maxTargets) break;
            }
        }

        return enemiesAttacked; 
    };
    
    /**
     * upgrades the towers stats
     */
    public void upgrade() {
    
        if (upgradeLevel > 1)  return;
        this.damage += 1;
        this.range += 1;
        this.upgradeLevel += 1;
    };


    public String getName(){
        return this.name;
    }
}
