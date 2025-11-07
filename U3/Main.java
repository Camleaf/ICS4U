import javax.swing.SwingUtilities;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Clock;
import main.Game;
import java.lang.Thread;
/**
    @author CamLeaf
*/
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // Man i love the jswing and awt docs they make no sense but it works
            @Override
            public void run() {
                // initialize globals stuff
                Game game = new Game();
                game.show();
                Keyboard keyboard = new Keyboard();
                game.window.addKeyListener(keyboard);
                Mouse mouse = new Mouse();
                game.window.addMouseListener(mouse);
                game.window.requestFocus();

                game.addListeners(keyboard,mouse);
                MainLoop main = new MainLoop(keyboard, game, mouse);
            }
        });
    }
}
/**
    @author CamLeaf
*/
class MainLoop implements Runnable{
    Thread thread;
    boolean running;
    Keyboard keyboard;
    Game game;
    Mouse mouse;
    Clock clock;

    public MainLoop(Keyboard keyboard, Game game, Mouse mouse) {
        thread = new Thread(this);
        thread.setDaemon(true);
        this.keyboard = keyboard;
        this.game = game;
        this.mouse = mouse;
        this.clock = new Clock(60);
        start();
    }

    public synchronized void start(){
        running = true;
		thread.start();
    }

    public synchronized void end(){
        // thank you to https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html for threading stuff 
        running = false;
        try{
            thread.join();
        } catch (InterruptedException e) {}
    
    }

    public void run(){
        // Mainloop here
        while (running){
            mouse.clearStack();
            clock.tick();
        }
    }
}

