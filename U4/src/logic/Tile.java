package src.logic;
import src.logic.Tower;
import java.awt.Point;

/** Class containing some details about each tile, notably the texture index and an occupying tower
 * @author Alexcedw
 * @author SpencerM
 */
public class Tile {
    
    public enum Type {
        DIRT,ROAD;
    };
    
    private Type type;
    public int textureIndex;
    private Tower occupier = null;
    private Point pos;
    
    public Tile(int textureIndex, Point p){
        this.textureIndex = textureIndex;
        this.pos = p;
        this.type = Type.DIRT;
    }
    
    public void setType(Type type){
        this.type = type;
    };
    
    public Type getType(){
        return this.type;
    }
    
    public boolean hasOccupier(){
        return this.occupier != null;
    }
    
    /** Gets the tower placed on this square
     * @return the Tower object placed ont the square
     */
    public Tower getOccupier(){
        return this.occupier;
    }
    
    /** Sets the current occupied tower to a given Tower object
     * @param occupier the given Tower object
     */
    public void setOccupier(Tower occupier){
        this.occupier = occupier;
    }
    
    /** Returns the coordinate of the Tile, relative to the playing grid
     * @return the coordinate of the Tile, in Point form.
     */
    public Point getPosition(){
        return this.pos;
    }
    
    
    public int getX(){
        return this.pos.x;
    }
    
    public int getY(){
        return this.pos.y;
    }
}





