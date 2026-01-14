package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import java.util.ArrayList;


/** A lot of implementation still needed
 * 
 */
public class TowerShooter extends Tower{
    
    public Type type = Type.SHOOTER;
    public int attackDelay = 100; // In milliseconds. Time between each attack
    public int baseCost = 100; //the price of the tower
    public int damage = 1;
    public int range = 3;
    public int maxTargets = 1;
    public int textureIndex = 4;
    public int upgradeLevel = 0;
    public static String name = "Shooter";
    
    
    public TowerShooter(){
         
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
