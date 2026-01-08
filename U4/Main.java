/**
 * @author Alexcedw
 * @author SpencerM
 */
import src.Game;
import lib.Mouse;
import lib.Keyboard;
import lib.Clock;
import javax.swing.SwingUtilities;
import java.lang.Thread;

public class Main {


    /**
     * Describe your project here.
     *
     * @param args the command line arguments
     */
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                Game game = new Game();
                Keyboard keyboard = new Keyboard();
                Mouse mouse = new Mouse();
                game.window.addMouseListener(mouse);
                game.window.addKeyListener(keyboard);
                game.addEventListeners(mouse,keyboard);
                MainLoop main = new MainLoop(game, mouse, keyboard);
            }
        });
   }
}


class MainLoop implements Runnable {
    Thread thread;
    Game game;
    Clock clock;
    Mouse mouse;
    Keyboard keyboard;
    boolean running;
    
    
    public MainLoop(Game game, Mouse mouse, Keyboard keyboard) {
        this.game = game;
        this.mouse = mouse;
        this.keyboard = keyboard;
        thread = new Thread(this); // Create a thread off of the runnable
        thread.setDaemon(true); // When progrma closed close thread as well
        clock = new Clock(30);
    
        start();
    }
    
    public synchronized void start(){ // open the thread
        running = true;
        thread.start();
    }
    
    public synchronized void end(){ // close the thread
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e){};
    }
    
    
    
    public void run(){
        // Add mainloop
        game.show();
        while (running){
            // nominally runs 30 cycles per second
            game.update();
            mouse.clearStack();
            clock.tick();
        }
    }
}













