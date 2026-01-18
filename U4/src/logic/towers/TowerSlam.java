package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import src.render.EnemyRenderBox;
import java.util.ArrayList;
import java.awt.Point;

/** A lot of implementation still needed
 * 
 */
public class TowerSlam extends Tower{
    
    public static String name = "Slam";
    
    
    public TowerSlam(){
        super();             
        type = Type.SLAM;
        attackDelay = 1700; // In milliseconds. Time between each attack
        baseCost = 100; //the price of the tower
        damage = 1;
        range = 1;
        maxTargets = 1000;
        textureIndex = 7;
        upgradeLevel = 0;
        attackInterval.setInterval(attackDelay);
    }
    
    //default public Tower(Type t, int a, int b, int d, int i);
    /** 
     *uses the SLAM attack alg. Also handles enemy deletion in-place because 
     * @param ArrayList of all the enemies on the board
     */
    public ArrayList<Integer> doAttack(Enemy[] enemies, Point curPosition){
        ArrayList<Integer> enemiesAttacked = new ArrayList<Integer>();

        for (int i = 0;i<enemies.length;i++){
            Enemy enemy = enemies[i];
            if (!enemy.active) continue;
            
            if (Math.abs(enemy.x - curPosition.x) <= range && Math.abs(enemy.y-curPosition.y)<=range){ // if in range
                enemiesAttacked.add(i);
            }
        }

        return enemiesAttacked;
    }

    
    /**
     * upgrades the towers stats
     */
    public void upgrade() {
    
    if (upgradeLevel > 1)  return;
    if (upgradeLevel == 1) this.range += 1;
    this.damage += 1;
    //this.range += 1;
    this.upgradeLevel += 1;
    };
    
    public String getName(){
        return this.name;
    }
}
