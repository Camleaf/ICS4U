import javax.swing.SwingUtilities;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Clock;
import main.window.Display;
import java.lang.Thread;
import java.time.Instant;
/**
    @author CamLeaf
*/
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // Man i love the jswing and awt docs they make no sense but it works
            @Override
            public void run() {
                // add display stuff
                Display display = new Display();
                display.show();
                Keyboard keyboard = new Keyboard();
                display.window.addKeyListener(keyboard);
                Mouse mouse = new Mouse();
                display.window.addMouseListener(mouse);
                display.window.requestFocus();
                MainLoop main = new MainLoop(keyboard, display, mouse);
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
    Display display;
    Mouse mouse;
    Clock clock;

    public MainLoop(Keyboard keyboard, Display display, Mouse mouse) {
        thread = new Thread(this);
        thread.setDaemon(true);
        this.keyboard = keyboard;
        this.display = display;
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
        int idx = 0;
        while (running){

            // If not bottlenecked by system capacity, this loop will run a negligible amount below 50fps

            System.out.println(idx);

            mouse.clearStack();
            idx++;
            clock.tick();
        }
    }
}

