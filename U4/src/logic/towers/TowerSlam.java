
package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import java.util.ArrayList;


/** A lot of implementation still needed
 * 
 */
public class TowerSlam extends Tower{
    
    public static String name = "Slam";
    
    
    public TowerSlam(){
        super();             
        type = Type.SLAM;
        attackDelay = 1500; // In milliseconds. Time between each attack
        baseCost = 100; //the price of the tower
        damage = 2;
        range = 1;
        maxTargets = 1000;
        textureIndex = 7;
        upgradeLevel = 0;
    }
    
    //default public Tower(Type t, int a, int b, int d, int i);
    /**
     *Finds the closest enemy/enemies to target
     * @param ArrayList of all the enemies on the board
     */
    public  ArrayList<Enemy> getTargeted(ArrayList<Enemy> enemies){return null;};
    
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
