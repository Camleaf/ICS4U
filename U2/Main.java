import javax.swing.SwingUtilities;
import pkg.Display;
import pkg.display.KeyProcessor;
import java.lang.Thread;



public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // Man i love the jswing and awt docs they make no sense but it works
            @Override
            public void run() {
                // move display to Mainloop dec
                Display display = new Display();
                display.show();
                KeyProcessor keyboard = new KeyProcessor();
                display.frame.addKeyListener(keyboard);
                MainLoop main = new MainLoop(keyboard, display);
                // I figured out why this doesn't work. I will need to multithread so that the main thread can handle swing and awt request without being blocked
            }
        });
    }
}

class MainLoop implements Runnable{
    Thread thread;
    boolean running;
    KeyProcessor keyboard;
    Display display;

    public MainLoop(KeyProcessor keyboard, Display display) {
        thread = new Thread(this);
        thread.setDaemon(true);
        this.keyboard = keyboard;
        this.display = display;
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
            if (keyboard.isKeyPressed(37)){
                // IT WORKSSSSS
                System.out.println("Press");
                this.end();
            }
        }
    }
}

