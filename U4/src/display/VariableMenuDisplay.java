package src.display;
import lib.graphics.BasePanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import src.logic.Tile;
import java.awt.Point;

/** Handles (or will handle) the menus that appear from clicking on a tile
 * @author Alexcedw
 */
 
public class VariableMenuDisplay extends BasePanel {
    private TowerMenu towerMenu;
    private EmptyMenu emptyMenu;
    private Point currentStored = null;

    public VariableMenuDisplay(int width, int height){
        super(width, height, Color.lightGray);
        towerMenu = new TowerMenu(width-20,height-50);
        towerMenu.setLocation(10,60);
        emptyMenu = new EmptyMenu(width-20,height-50);
        emptyMenu.setLocation(10,60);
        // Add none to renderer to start
        
    }
    
    public void handleClick(Point pos){
        // Find a way to pass the tile data to here
        // pass to the submenus
        // //this got lost in the codehs purge
    }
    
    public void handleUpdate(Point stateUpdate, Tile[][] tileArray){
        if (stateUpdate == null){ // If no update then no bother updating this
            removeAll();
            currentStored = null;
            return;
        } else if (stateUpdate == currentStored)return;
        
        
        removeAll(); // we want to clear
        
        if (tileArray[stateUpdate.y][stateUpdate.x].hasOccupier()){
            add(towerMenu); // will still need to add stuff to customize to the square but this is good start
        } else {
            add(emptyMenu);
        }
        
        

        

    
        

        // todo handle update
        //
        
    }
}

// Could move these to new file if gets too long but should be fine for now
/**Submenu that appears when clicking on a tower and contains pertaining options
 * @author Alexcedw
 */
class TowerMenu extends BasePanel {
    

    public TowerMenu(int width, int height){
        super(width,height, Color.DARK_GRAY); // will be another color just placeholder for testing

    }

    /**To Implement
     */
    public void handleClick(){};

    
    /** To implement
     */
    public void buildContent(){};
}




/**Submenu that appears when clicking on an empty tile and contains pertaining options
 * @author Alexcedw
 */
class EmptyMenu extends BasePanel {
    

    public EmptyMenu(int width, int height){
        super(width,height, Color.BLACK); // will be another color just placeholder for testing
    }

    /**To Implement
     */
    public void handleClick(){};

    
    /** To implement
     */
    public void buildContent(){};
}


