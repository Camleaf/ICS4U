package lib;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * A class to initialize any frames that we use
 */
public class Window extends JFrame{
    public Window(String title, int width, int height) {
        setTitle(title);
        setSize(width,height);
        setIgnoreRepaint(true);
        setLayout(null);
        setContentPane(new JLayeredPane());
    }
}