package src.logic;
import src.logic.Tile;
import java.util.ArrayList;
public abstract class Enemy {
    // Enemies will hop single file.
    
    public static enum Type {
        TEST;
    }
    
    public Type type;
    public int jumpDelay;
    public int damage; 
    public int health;
    
    /**
     * finds the next tile the enemy will jump to
     * @param ArrayList of the tiles on the board
     * @return next tile the enemy will jump to
     */
     //Spencer Note: This is just a placeholder until we have a pathfinding algorithm
    public abstract Tile getNextTile(ArrayList<Tile> tiles);
    
    /**
     * returns the jump delay for this enemy
     */
    public int getJumpDelay() {
        return this.jumpDelay;
    };
    
    /**
     * returns the damage this enemy inflicts
     */
    public int getDamage() {
        return this.damage;
    };
    
    public int getHealth() {
        return this.health;
    };
    
}
