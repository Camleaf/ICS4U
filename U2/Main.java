import java.awt.RenderingHints.Key;

import javax.swing.SwingUtilities;
import pkg.Display;
import pkg.display.KeyProcessor;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Display demo = new Display();
                demo.show();
                KeyProcessor kp = new KeyProcessor();
                demo.frame.addKeyListener(kp);

                // I figured out why this doesn't work. I will need to multithread so that the main thread can handle swing and awt request without being blocked
                // while (true){
                //     if (kp.isKeyPressed(37)){
                //         System.out.println("pressed");
                //         break;
                //     }
                // }
            }
        });
    }
}

