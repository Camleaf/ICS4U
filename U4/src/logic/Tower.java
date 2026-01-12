package src.logic;
import src.logic.Enemy;
import java.util.ArrayList;

/**Abstract class detailing implementation for the Towers
 * @author SpencerM
 * @author Alexcedw
 */
public abstract class Tower {
    
    public static enum Type {
        TEST;
    };
    
    public Type type;
    public int attackDelay; // In milliseconds. Time between each attack
    public int baseCost; //the price of the tower
    public int damage;
    public int textureIndex;
    
    
     
    //default public Tower(Type t, int a, int b, int d, int i);
    public abstract ArrayList<Enemy> getTargeted(ArrayList<Enemy> enemies);
    /**
     *Finds the closest enemy/enemies to target
     * @param ArrayList of all the enemies on the board
     */
    public int getBaseCost(){
        return this.baseCost;
    };
    public int getTextureIndex(){
        return this.textureIndex;
    };
    
    public int getDamage() {
        return this.damage;
    };
    
    public int getAttackDelay(){
        return this.attackDelay;
    };
    
}
