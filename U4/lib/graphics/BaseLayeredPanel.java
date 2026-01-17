
package lib.graphics;
import javax.swing.JLayeredPane;
import java.awt.Color;

/**
 * An empty panel with some settings that are nice to have as default for all layered panels
 * <p>
 * Use this instead of JPanels
 */
public class BaseLayeredPanel extends JLayeredPane{

    public BaseLayeredPanel(int width, int height, Color background){
        setLayout(null); 
        setSize(width,height);
        setIgnoreRepaint(true);
        setBackground(background);
        setDoubleBuffered(true);
    }
    
}
