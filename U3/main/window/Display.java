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
    GraphicsPanel px;

    public Display(){
        window = new Window("Chess", 800,800);
        /*
         * Screen wil be 800 x 800
         */
        // fix z-fighting issue
        xp = new GraphicsPanel(300, 300);
        xp.setBounds(250,250,300,300);
        window.add(xp,Integer.valueOf(1));
         

        px = new GraphicsPanel(300, 300);
        px.setBounds(250,250,300,300);
        window.add(px,Integer.valueOf(0));
    }


    public void show(){
        window.setVisible(true);
    }

    public void testServe(int x, int y){
        xp.flushBuffer();
        xp.drawRect(x, y, 50, 50, Color.RED);
        xp.setLocation(x,y);

        px.flushBuffer();
        px.drawRect(0, 0, px.getWidth(), px.getHeight(), Color.darkGray);
        px.drawRect(x, y, 50, 50, Color.BLUE);
        px.setLocation(x,500-y);
        window.repaint();
    }
}
