package lib.graphics;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * An empty panel with some settings that are nice to have as default for all panels
 * <p>
 * Use this instead of JPanels
 */
public class BasePanel extends JPanel{

    public BasePanel(int width, int height, Color background){
        setLayout(null); 
        setSize(width,height);
        setIgnoreRepaint(true);
        setBackground(background);
        setDoubleBuffered(true);
    }
    
}
