package main.window;
import main.window.panels.BoardPanel;
import lib.Window;
import java.awt.Color;
/**
    * Utilises the rendering methods from the Screen class to render game-specific elements and objects. Only contains renderloops and displays the game objects. Does not modify the game objects
    @author CamLeaf
*/
public class Display {
    // No interfacing with swing should be done from this class
    public Window window;

    public Display(){
        window = new Window("Chess", 800,640);
        BoardPanel b = new BoardPanel(512);
        window.add(b);
        /*
         * Screen will be 800 x 800
         */
    }


    public void show(){
        window.setVisible(true);
        window.repaint();
    }
}
