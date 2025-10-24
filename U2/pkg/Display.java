package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;

public class Display {
    public JFrame frame;

    public Display() {
        frame = new JFrame();
        frame.setTitle("Raycast Sim");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);

    }

    public void show(){
        frame.setVisible(true);
    }


    public void serveFrame(){
        // Raycast new frame
        // Wait for 60hz timer
        // Reset frame
        // Push frame
        // Reset 60hz timer
    }
}