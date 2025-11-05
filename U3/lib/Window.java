package lib;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Window extends JFrame{
    /*
    * Is the interface between all custom panels and the main game. No interfacing with game data or objects should be done from this class
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
