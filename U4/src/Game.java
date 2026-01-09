package src;
import java.awt.Color;
import java.awt.Point;
import lib.Window;
import lib.Mouse;
import lib.Keyboard;
import src.display.BoardDisplay;
import src.display.ShopDisplay;

/**
 * @author SpencerM
 * @author Alexcedw
 */
public class Game {
    public Window window;
    private Mouse mouse;
    private Keyboard keyboard;
    private BoardDisplay boardDisplay;
    private ShopDisplay shopDisplay;
    
    public Game() {
        window = new Window("Tower Defense", 832, 512);
        window.setBackground(Color.white);
        
        boardDisplay = new BoardDisplay();
        boardDisplay.setLocation(160,0);
        window.add(boardDisplay, Integer.valueOf(0));
        
        shopDisplay = new ShopDisplay(160,512);
        window.add(shopDisplay);
        
        
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
