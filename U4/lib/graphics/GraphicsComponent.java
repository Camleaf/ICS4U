package lib.graphics;
import java.awt.Graphics;
import javax.swing.JComponent;
import lib.graphics.Context;
/**
 * Base class for anything with stylized textures
 * @author Alexcedw
 */
public class GraphicsComponent extends JComponent {
    protected Context gct; // gct for graphicsContext
    
    public GraphicsComponent(int width, int height) {
        setLayout(null);
        setSize(width,height);
        setIgnoreRepaint(true);
        setDoubleBuffered(true);
        gct = new Context(width,height);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        gct.paintComponent(g);
        g.dispose();
    }
    
}