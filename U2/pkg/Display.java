package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;
import pkg.display.Texture;
import pkg.Board;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import pkg.display.texture.Wall;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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
        private BufferStrategy buffer;
        private BufferedImage bufferFrame;
        public int[] pixels;

        public Screen(){
            setTitle("Raycast Sim");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(700,700);
            setLocationRelativeTo(null);
            buffer = getBufferStrategy();
            bufferFrame = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
            pixels = (
                (DataBufferInt) // this is a implementation of databuffer that can be used to plot pixels https://docs.oracle.com/javase/8/docs/api/index.html?java/awt/image/DataBufferInt.html
                bufferFrame.getRaster().getDataBuffer()).getData();
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

                int[] prevSquare = new int[]{(int)startX/Board.mapScale,(int)startY/Board.mapScale};
                int intersect = 0; // Horizontal is 0, vertical is 1
                while(true){
                    
                    // Initialize temporary variables for checking
                    int tempX = startX;
                    int tempY = startY;
                    

                    // add to temp position variables movement normalized for raycast angle

                    tempX += Math.round(raySpeed * Math.sin(Math.toRadians(rayAngle)));
                    tempY += Math.round(raySpeed * Math.cos(Math.toRadians(rayAngle)));

                    
                    // Check if the ray has passed into another square and adjust intersect settings accordingly
                    // This block also has a optimization/shortcut where if it passes diagonally through a square that it will default to horizontal intersect

                    if (tempX>=Board.mapScale*prevSquare[0] || tempY >= Board.mapScale*prevSquare[1]){

                        int curSqX = (int)(tempX/Board.mapScale);
                        int curSqY = (int)(tempY/Board.mapScale);
                        if (Math.abs(curSqX-prevSquare[0]) == 1){ // if passed horizontally
                            intersect = 0;
                        } else if (Math.abs(curSqY-prevSquare[1])==1){ // if passed vertically
                            intersect = 1;
                        }
                        prevSquare = new int[]{curSqX,curSqY};
                    }

                    if (Board.isCollision(tempX, tempY)){
                        // Get the texture data based on texture assigned to collided square
                        int boardValue = Board.getBoardSquare(tempX, tempY);
                        Wall texture = Texture.WALL.get(boardValue);
                        int[] rgbData = texture.rgbData;
                        
                        // Set the x-index to grab from texture to x or y depending on collision location
                        int textureIndex = tempX;
                        if (intersect == 1){
                            textureIndex = tempY;
                        }
                        // Textureindex must change from the players pov to the texture pov, so from 640px/wall to 64px/wall
                        textureIndex = (int)(textureIndex / Board.mapScale);
                        textureIndex /= 10;


                        int[] rgbValues = rgbData;

                        //See how big the wall should be 
                        int wallVerticalAppearance;
                        if (curDist != 0){
                            wallVerticalAppearance = Math.abs(camera.width/curDist);
                        } else {
                            wallVerticalAppearance = camera.width;
                        }
                        



                        for (int y=wallVerticalAppearance/-2;y<wallVerticalAppearance/2;y++){
                            int color;
                            //fix this
                        }

                                               
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
            g.drawImage(bufferFrame, 0, 0, bufferFrame.getWidth(), bufferFrame.getHeight(), null);
        }
    }

}