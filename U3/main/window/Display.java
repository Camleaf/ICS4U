package main.window;
import lib.window.GraphicsPanel;
import lib.window.Texture;
import lib.Window;
import java.awt.Color;
/**
    * Utilises the rendering methods from the Screen class to render game-specific elements and objects.
    @author CamLeaf
*/
public class Display {
    // No interfacing with swing should be done from this class
    public Window window;

    public Display(){
        window = new Window("Chess", 800,800);
        /*
         * Screen wil be 800 x 800
         */
    }


    public void show(){
        window.setVisible(true);
        window.repaint();
    }
}
