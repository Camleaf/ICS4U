package src;
import java.awt.Color;
import java.awt.Point;
import lib.Window;
import lib.Mouse;
import lib.Keyboard;
import src.display.BoardDisplay;

/**
 * @author SpencerM
 * @author Alexcedw
 */
public class Game {
    public Window window;
    private Mouse mouse;
    private Keyboard keyboard;
    private BoardDisplay boardDisplay;
    
    public Game() {
        window = new Window("Tower Defense", 640, 640);
        window.setBackground(Color.white);
        boardDisplay = new BoardDisplay();
        window.add(boardDisplay, Integer.valueOf(0));
        
        refresh();
    }
    public void show(){
        window.setVisible(true);
        window.repaint();
    }
    public void refresh(){
        window.repaint();
    }

    public void addEventListeners(Mouse mouse, Keyboard keyboard){
        this.mouse = mouse;
        this.keyboard = keyboard;
    }
    
    public void update(){
        
        Point event = mouse.pollEvent();
        // Run some code idk
        refresh();
    }
}
