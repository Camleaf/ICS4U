import javax.swing.SwingUtilities;

import lib.interactions.Keyboard;
import main.window.Display;
import java.lang.Thread;
import java.time.Instant;

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
                display.window.requestFocus();

                MainLoop main = new MainLoop(keyboard, display);
            }
        });
    }
}

class MainLoop implements Runnable{
    Thread thread;
    boolean running;
    Keyboard keyboard;
    Display display;

    public MainLoop(Keyboard keyboard, Display display) {
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
        long previousTime = Instant.now().toEpochMilli();
        int idx = 0;
        while (running){

            // This timer runs the loop at 60 fps

            long currentTime = Instant.now().toEpochMilli();
            if (currentTime - previousTime <= 20){
                try{
                    Thread.sleep(Math.abs((currentTime+20)-previousTime));
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            display.testServe(idx%250,idx%250);
            previousTime = currentTime;
            
            idx++;
        }
    }
}

