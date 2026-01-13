package src.display;
import lib.graphics.BasePanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import src.logic.Tile;
import java.awt.Point;
import src.display.menu.*;
import src.display.BoardDisplay;
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
    
    public void handleClick(Point pos, Tile[][] tileArray){
 
        if (currentStored == null) return;
        if (tileArray[currentStored.y][currentStored.x].hasOccupier()){
            towerMenu.handleClick(currentStored, tileArray);
        } else {
            emptyMenu.handleClick(pos,tileArray);
        }
        // Find a way to pass the tile data to here
        // pass to the submenus
        // //this got lost in the codehs purge
    }
    
    public void handleUpdate(Point stateUpdate, Tile[][] tileArray, BoardDisplay board){
        if (stateUpdate == null){ // If no update then no bother updating this
            removeAll();
            currentStored = null;
            return;
        } else if (stateUpdate == currentStored)return;
        
        currentStored = stateUpdate;
        removeAll(); // we want to clear
        towerMenu.removeAll();
        emptyMenu.removeAll();
        if (tileArray[stateUpdate.y][stateUpdate.x].hasOccupier()){
            towerMenu.buildContent(stateUpdate,tileArray);
            add(towerMenu); // will still need to add stuff to customize to the square but this is good start
        } else {
            emptyMenu.buildContent(currentStored,tileArray,board,this);
            add(emptyMenu);
        }
        
        

        

    
        

        // todo handle update
        //
        
    }
}
