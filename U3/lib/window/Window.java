package lib.window;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Window extends JFrame{
    /*
    * Interfaces directly with swing, handles buffers and deals with graphics. No interfacing with game data or objects should be done from this class
    */

    public Window(String title, int width, int height){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(width,height);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setLayout(null);
        setContentPane(new JLayeredPane());
    }
}
