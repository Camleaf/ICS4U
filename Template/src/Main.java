package src;
import java.awt.Color;

import lib.engine.Window;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;

/**
    * Main intermediary for taking commands from mainloop and distributing to components
    @author CamLeaf
*/
public class Main {
    // No interfacing with swing should be done from this class
    public Window window;
    private Keyboard keyboard;
    private Mouse mouse;

    public Main(){
        window = new Window("Template Window", 800,800);
        window.setBackground(Color.WHITE);
        
        /*
         * Screen will be 800 x 800
         */
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
    }
}
