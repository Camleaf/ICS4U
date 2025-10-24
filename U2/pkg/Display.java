package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;
import pkg.Board;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Display {
    public Screen screen;
    public Camera camera;

    public Display(int startingX, int startingY) {
        screen = new Screen();
        camera = new Camera(startingX, startingY,700,Board.mapScale*4);


    }

    public void show(){
        screen.setVisible(true);
    }


    //
    public class Screen extends JFrame{
        // My bufferstrategy knowledge comes from this amazing stackoverflow article https://stackoverflow.com/questions/13590002/understand-bufferstrategy
        BufferStrategy buffer;

        public Screen(){
            setTitle("Raycast Sim");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(700,700);
            setLocationRelativeTo(null);
            buffer = getBufferStrategy();
        }
        public void serveFrame(){

            // Check if bufferstrategy is disposed
            if (buffer == null){
                createBufferStrategy(3);
            }
            buffer = getBufferStrategy();
            
            // following dowhile loop was taken from stackoverflow
            do {
                try{
                    // get buffer graphics then draw next frame
                    Graphics g =  buffer.getDrawGraphics();
                    drawFrame(g);
                } finally {
                    // clear resources early
                    buffer.dispose();
                }
                //push frame to top
                buffer.show();

            } while (buffer.contentsLost()); // According to stackoverflow the buffer can be lost periodically so this helps

        }
    }

    private void drawFrame(Graphics g){
        // fov should be 75. Around what the original raycasted programs used
    }

}