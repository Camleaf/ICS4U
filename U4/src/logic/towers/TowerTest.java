package src.logic.towers;
import src.logic.Tower;
import src.logic.Enemy;
import src.render.EnemyRenderBox;
import java.util.ArrayList;
import java.awt.Point;


public class TowerTest extends Tower{
    
    public static String name = "Tester";
    
    public TowerTest(){
        super(); 
        type = Type.TEST;
        attackDelay = 110; // In milliseconds. Time between each attack
        baseCost = 1234; //the price of the tower
        damage = 198;
        upgradeLevel = 0;
        textureIndex = 11;
        attackInterval.setInterval(attackDelay);
    }
    //default public Tower(Type t, int a, int b, int d, int i);
     /**
     *Finds the closest enemy/enemies to target
     * @param ArrayList of all the enemies on the board
     */
    public ArrayList<Integer> doAttack(Enemy[] enemies, Point curPosition){
        return null;
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