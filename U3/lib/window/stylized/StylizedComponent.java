package lib.window.stylized;
import javax.swing.JComponent;
import lib.window.GraphicsContext;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Merges the graphicsContext and jComponent to create a barebones Jcomponent with stylized graphics
 */
public class StylizedComponent extends JComponent{
    protected GraphicsContext gct;

    public StylizedComponent(int width, int height){
        // Intialize graphicsContext
        setLayout(null); 
        setSize(width,height);
        setIgnoreRepaint(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        gct = new GraphicsContext(width, height);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        gct.paintComponent(g);
        repaint();
    }
    
}
