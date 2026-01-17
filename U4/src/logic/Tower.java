package src.logic;
import src.logic.Enemy;
import java.util.ArrayList;
import src.logic.towers.*;
import java.awt.Point;
import lib.Interval;

/**Abstract class detailing implementation for the Towers
 * @author SpencerM
 * @author Alexcedw
 */
public abstract class Tower {
    
    public static enum Type {
        TEST, SHOOTER, SLAM;
    };
    
    public static final Type[] types = new Type[]{Type.SHOOTER,Type.SLAM};
    
    public Type type;
    public int attackDelay = 1000; // In milliseconds. Time between each attack
    public int baseCost; //the price of the tower
    public int damage;
    public int textureIndex;
    public int maxTargets;
    public int range;
    public static String name;
    public int upgradeLevel;
    public Interval attackInterval = new Interval(attackDelay);
     
    //default public Tower(Type t, int a, int b, int d, int i);
    public abstract void upgrade();
    public abstract ArrayList<Integer> doAttack(Enemy[] enemies, Point curPosition);
    
    /**
     * returns the tower from this tower's type
     * @param: Type type: the tower type
     */
    public static Tower getTowerFromType(Type type){
        switch (type) {
          case Type.SHOOTER:
              return new TowerShooter();
          case Type.SLAM:
              return new TowerSlam();
          
          default:
            return new TowerTest();
        }
    }
    
    /**
     * returns the name of this tower's type
     * @param: Type type: the tower type
     */
    public static String getNameFromType(Type type) {
        
        switch (type) {
            case Type.SHOOTER:
                return TowerShooter.name;
            case Type.SLAM:
                return TowerSlam.name;
            
            default:
                return TowerTest.name;
        }
        
    }
    public abstract String getName(); 
    
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



