package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;
import pkg.Board;
import java.awt.Graphics;

public class Display {
    public JFrame frame;
    public JFrame buffer;
    public Camera camera;

    public Display(int startingX, int startingY) {
        frame = new JFrame();
        frame.setTitle("Raycast Sim");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);
        camera = new Camera(startingX, startingY,700,Board.mapScale*4);


        Graphics g = frame.getGraphics();
        buffer.setTitle("Raycast Sim");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);

    }

    public void show(){
        frame.setVisible(true);
    }


    //
    private class Screen extends JFrame{
        public Screen(){

        }
    }


    /*
     * bufferFrame is split from serveFrame so that the buffer can be created during the downtime on the 60hz loop, then serveFrame can be executed right as the clock ticks
     */
    public void bufferFrame(){
        // Reset buffer frame
        // Raycast frame onto buffer JFrame
        // return frame
    }

    public void serveFrame(){
        // Push buffer frame to main frame
    }
}