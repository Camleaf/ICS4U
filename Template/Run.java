import javax.swing.SwingUtilities;

import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Clock;
import src.Main;
import java.lang.Thread;
/**
    @author CamLeaf
*/
public class Run {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0"); // https://stackoverflow.com/questions/47613006/how-to-disable-scaling-the-ui-on-windows-for-java-9-applications

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // initialize globals stuff
                Main game = new Main();
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
    Main game;
    Mouse mouse;
    Clock clock;

    public MainLoop(Keyboard keyboard, Main game, Mouse mouse) {
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
        running = false;
        try{
            thread.join();
        } catch (InterruptedException e) {}
    
    }

    public void run(){
        // Mainloop here
        while (running){
            game.update();
            game.refresh();
            mouse.clearStack();
            clock.tick();
        }
    }
}

