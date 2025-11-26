package src;
import lib.Window;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Interval;
import src.window.panels.PlayWindow;

import java.awt.Color;

/**
    * Main intermediary for taking commands from mainloop and distributing to components
    @author CamLeaf
*/
public class Game {
    // No interfacing with swing should be done from this class
    public Window window;
    private Keyboard keyboard;
    private Mouse mouse;
    private PlayWindow playWindow;

    public Game(){
        window = new Window("Tetris", 800,800);
        window.setBackground(Color.WHITE);
        playWindow = new PlayWindow(400, 800);
        window.add(playWindow,Integer.valueOf(1));
        
    }

    public void show(){
        window.setVisible(true);
        window.repaint();
    }
    public void refresh(){
        window.repaint();
    }

    public void addListeners(Keyboard keyboard, Mouse mouse){
        this.keyboard = keyboard;
        this.mouse = mouse;
    }


    public void update(){
        // Some example functions
        mouse.peekEvent();

        if (keyboard.isKeyPressed(27)){
            System.out.println("hello");
        };
        refresh();
    }
}
