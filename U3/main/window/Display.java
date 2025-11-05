package main.window;
import lib.window.GraphicsPanel;
import lib.Window;
import java.awt.Color;

public class Display {
    /*
     * Utilises the rendering methods from the Screen class to render game-specific elements and objects. No interfacing with swing should be done from this class
     */
    public Window window;
    GraphicsPanel xp;

    public Display(){
        window = new Window("Chess", 800,800);
        /*
         * Screen wil be 800 x 800
         */
        xp = new GraphicsPanel(300, 300);
        xp.setBounds(250,250,300,300);
        window.add(xp,Integer.valueOf(1));
        
    }


    public void show(){
        window.setVisible(true);
    }

    public void testServe(int x, int y){
        xp.flushBuffer();
        xp.drawEllipse(x, y, 50, 80, Color.BLUE);
        xp.drawCircle(x, 250-y, 25, Color.RED);
        xp.setLocation(x,y);

        window.repaint();
    }
}
