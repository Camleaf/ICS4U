package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import java.util.ArrayList;


/** A lot of implementation still needed
 * 
 */
public class TowerShooter extends Tower{
    
    public Type type = Type.TEST;
    public int attackDelay = 100; // In milliseconds. Time between each attack
    public int baseCost = 100; //the price of the tower
    public int damage = 1;
    public int textureIndex = 15;
    
    
    public TowerShooter(){
         
    }
    //default public Tower(Type t, int a, int b, int d, int i);
    public ArrayList<Enemy> getTargeted(ArrayList<Enemy> enemies){return null;};
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
