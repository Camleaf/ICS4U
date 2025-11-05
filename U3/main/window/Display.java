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
    GraphicsPanel xp;
    Texture test;

    public Display(){
        window = new Window("Chess", 800,800);
        /*
         * Screen wil be 800 x 800
         */

         // I will need a separate function for initializing objects for sure
        xp = new GraphicsPanel(300, 300);
        xp.setBounds(250,250,300,300);
        window.add(xp,Integer.valueOf(1));
        
        test = new Texture("src/testTexture.png", 256, 256);
    }


    public void show(){
        window.setVisible(true);
    }

    public void testServe(int x, int y){
        // any function which runs on the seperate thread and is regularly run such as this function should never contain definitions, only updates
        xp.flushBuffer();
        xp.drawEllipse(x, 250-y, 50, 80, Color.BLUE);
        
        xp.drawRectBorder(250-x,250-y,50,50,Color.RED);
        xp.drawBufferedImage(test.getSlice(0,0,128,128), x, y);
        xp.setLocation(x,y);

        window.repaint();
    }
}
