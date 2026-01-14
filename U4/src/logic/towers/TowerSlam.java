
package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import java.util.ArrayList;


/** A lot of implementation still needed
 * 
 */
public class TowerSlam extends Tower{
    
    public Type type = Type.SLAM;
    public int attackDelay = 1500; // In milliseconds. Time between each attack
    public int baseCost = 100; //the price of the tower
    public int damage = 2;
    public int range = 1;
    public int maxTargets = 1000;
    public int textureIndex = 7;
    public int upgradeLevel = 0;
    public static String name = "Slam";
    
    
    public TowerSlam(){
         
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
    
    public int getBaseCost(){
        return this.baseCost;
    };
    public int getTextureIndex(){
        return this.textureIndex;
    };
    
    public int getDamage() {
        return this.damage;
    };
    
    public int getRange() {
        return this.range;
    };
    
    public int getMaxTargets() {
        return this.maxTargets;
    };
    
    public int getAttackDelay(){
        return this.attackDelay;
    };
    public int getUpgradeLevel(){
        return this.upgradeLevel;
    }
}
