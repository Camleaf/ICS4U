package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;
import pkg.display.Texture;
import pkg.Board;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import pkg.display.texture.Wall;
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

        private void drawFrame(Graphics g){
            // fov should be 75. Around what the original raycasted programs used


            // Initialize the angle system
            double rayAngle = -camera.FOV / 2.0; //current angle
            double angleIncrease = camera.FOV/camera.width; //the increase in ray for each angle to have a 75 deg FOV


            int raySpeed = 2;

            for (int column = 0;column<camera.width;column++){
                if (column > 0) rayAngle += angleIncrease;
                
                
                int startX = camera.x;
                int startY = camera.y;

                int curDist = 0;

    
                while(true){

                    int tempX = startX;
                    int tempY = startY;

                    tempX += Math.round(raySpeed * Math.sin(Math.toRadians(rayAngle)));
                    tempY += Math.round(raySpeed * Math.cos(Math.toRadians(rayAngle)));


                    if (Board.isCollision(tempX, tempY)){
                        // Get the texture data based on texture assigned to collided square
                        int boardValue = Board.getBoardSquare(tempX, tempY);
                        Wall texture = Texture.WALL.get(boardValue);
                        int[][] rgbData = texture.rgbData;
                        
                        // Figure out how to separate down from sides
                                               
                        break;
                    };

                    startX = tempX;
                    startY = tempY;
                    curDist += raySpeed;

                    // Base case where ray is out of render distance to save on optimization purposes
                    if (curDist > camera.renderDist){
                        break;
                        // Maybe i could make a fog effect thing later
                    }
                }



            }
        }
    }

}