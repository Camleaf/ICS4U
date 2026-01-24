package src.display.menu;
import lib.graphics.BasePanel;
import lib.graphics.BaseLayeredPanel;
import java.awt.Color;
import src.logic.Tile;
import java.awt.Point;
import src.display.menu.*;
import src.display.BoardDisplay;
import javax.swing.JTextPane;
import java.awt.Font;
import src.display.menu.WaveMenu;
/** Handles (or will handle) the menus that appear from clicking on a tile
 * @author Alexcedw
 */
 
public class VariableMenuDisplay extends BaseLayeredPanel {
    public TowerMenu towerMenu;
    public EmptyMenu emptyMenu;
    public Point currentStored = null;
    public JTextPane infoText;

    public VariableMenuDisplay(int width, int height){
        super(width, height, Color.lightGray);
        towerMenu = new TowerMenu(width-20,height-80);
        towerMenu.setLocation(10,90);
        emptyMenu = new EmptyMenu(width-20,height-80);
        emptyMenu.setLocation(10,90);



        // Add info text about instructions etc
        infoText = new JTextPane();
        infoText.setSize(180,400);
        infoText.setLocation(10,80);
        infoText.setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 13));
        infoText.setEditable(false);
        infoText.setBackground(Color.lightGray);
        add(infoText, Integer.valueOf(-10));
        infoText.setText(new StringBuilder("")
            .append("Welcome to Tower Defense!\n\n")
            .append("How to play:\n\n")
            .append("To buy towers, click on any empty space on the board\n")
            .append("To upgrade or destroy towers, click on that tower.\n\n")
            .append("If you feel ready to start, click the \"Start Wave\" button at the top\n")
            .append("Watch out for your health!")
            .toString()
        );
        infoText.setFocusable(false);
        // Add none to renderer to start
        
    }
    
    public void handleClick(Point pos, Tile[][] tileArray){
        if (currentStored == null) return;
        if (!tileArray[currentStored.y][currentStored.x].hasOccupier()){
            emptyMenu.handleClick(pos,tileArray);
        }
    }
    
    public void handleUpdate(Point stateUpdate, Tile[][] tileArray, BoardDisplay board, WaveMenu waveMenu){
        if (stateUpdate == null){ // If no update then no bother updating this
            removeAll();
            currentStored = null;
            return;
        } else if (stateUpdate == currentStored)return;
        
        currentStored = stateUpdate;
        super.removeAll();
        towerMenu.removeAll();
        emptyMenu.removeAll();
        
        if (tileArray[stateUpdate.y][stateUpdate.x].hasOccupier()){
            towerMenu.buildContent(stateUpdate,tileArray, board, this, waveMenu);
            add(towerMenu,Integer.valueOf(1));
            //setComponentZOrder(towerMenu,0);
        } else {
            emptyMenu.buildContent(currentStored,tileArray,board,this, waveMenu);
            add(emptyMenu,Integer.valueOf(1));
            //setComponentZOrder(emptyMenu,0);
        } 
    }


    @Override
    public void removeAll(){
        super.removeAll();
        add(infoText);
    }
}