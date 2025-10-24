package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;
import pkg.display.Texture;
import pkg.Board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import pkg.display.texture.Wall;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.color.*;
public class Display {
    public Screen screen;
    public Camera camera;

    public Display(int startingX, int startingY) {
        screen = new Screen();
        camera = new Camera(startingX*Board.mapScale, startingY*Board.mapScale,700,Board.mapScale*10);


    }

    public void show(){
        screen.setVisible(true);
        screen.createBufferStrategy(3);
        screen.buffer = screen.getBufferStrategy();
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
            setIgnoreRepaint(true);
            bufferFrame = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
            pixels = (
                (DataBufferInt) // this is a implementation of databuffer that can be used to plot pixels https://docs.oracle.com/javase/8/docs/api/index.html?java/awt/image/DataBufferInt.html
                bufferFrame.getRaster().getDataBuffer()).getData();
        }
        public void serveFrame(){

            // Check if bufferstrategy is disposed
            if (getBufferStrategy() == null){

                createBufferStrategy(3);
            }
            buffer = getBufferStrategy();
            
            // following dowhile loop was taken from stackoverflow
            do {
                try{
                    // get buffer graphics then draw next frame
                    drawFrame();
                    Graphics g =  buffer.getDrawGraphics();
                    g.drawImage(bufferFrame, 0, 0, null);
                    g.dispose();
                } finally {}
                //push frame to tops

            } while (buffer.contentsLost()); // According to stackoverflow the buffer can be lost periodically so this helps
            buffer.show();
        }

        private void drawFrame(){
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = Color.WHITE.getRGB();
            }
            // fov should be 75. Around what the original raycasted programs used

            // Now draw your scene or buffer

            // Initialize the angle system
            
            double rayAngle = camera.direction-camera.FOV / 2.0; //current angle
            double angleIncrease = camera.FOV/(camera.width*1.0); //the increase in ray for each angle to have a 75 deg FOV

            int raySpeed = 2;

            for (int column = 0;column<camera.width;column++){
                if (column > 0) rayAngle += angleIncrease;
                // System.out.println(rayAngle);
                
                
                double startX = camera.x;
                double startY = camera.y;

                double curDist = 0;

                int[] prevSquare = new int[]{(int)startX/Board.mapScale,(int)startY/Board.mapScale};
                int intersect = 0; // Horizontal is 0, vertical is 1
                while(true){
                    
                    // Initialize temporary variables for checking
                    double tempX = startX;
                    double tempY = startY;
                    

                    // add to temp position variables movement normalized for raycast angle

                    tempX += (raySpeed * Math.sin(Math.toRadians(rayAngle)));
                    tempY += (raySpeed * Math.cos(Math.toRadians(rayAngle)));

                    
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

                    if (Board.isCollision((int)tempX, (int)tempY)){



                        // Get the texture data based on texture assigned to collided square
                        int boardValue = Board.getBoardSquare((int)tempX, (int)tempY);
                        Wall texture = Texture.WALL.get(boardValue);
                        int[] rgbData = texture.rgbData;


                         // step backwards until we find exact edge intersect

                        while (true){
                            if (intersect == 0){
                                //find both possible entry point/distance ratio
                                double xDiff1 = tempX - ((int)tempX/Board.mapScale) * Board.mapScale;
                                double xDiff2 = tempX - ((int)tempX/Board.mapScale)* Board.mapScale + Board.mapScale;

                                // select closer wall because that means thats where ray entered
                                double xDiff = (Math.abs(xDiff1) < Math.abs(xDiff2)) ? xDiff1 : xDiff2; 

                                // Apply difference to x directly to find x coordinate of exit
                                tempX -= xDiff; 

                                //get ratio of x default movement to xdiff and use ratio to find the y position at point of intersection

                                double diffRatio = (raySpeed * Math.sin(Math.toRadians(rayAngle))) / xDiff;

                                tempY -= (raySpeed * Math.cos(Math.toRadians(rayAngle))) * diffRatio;
                                


                            } else {
                                //Same code as for x above just for y side instead
                                double yDiff1 = tempY - ((int)tempY/Board.mapScale) * Board.mapScale;
                                double yDiff2 = tempY - ((int)tempY/Board.mapScale)* Board.mapScale + Board.mapScale;


                                double yDiff = (Math.abs(yDiff1) < Math.abs(yDiff2)) ? yDiff1 : yDiff2; 

                                tempY -= yDiff; 
                                double diffRatio = (raySpeed * Math.cos(Math.toRadians(rayAngle))) / yDiff;
                                tempX -= (raySpeed * Math.sin(Math.toRadians(rayAngle))) * diffRatio;


                            }
                            break;
                        }


                        // Set the x-index to grab from texture to x or y depending on collision location
                        int textureIndex = (int)tempX;
                        if (intersect == 1){
                            textureIndex = (int)tempY;
                        }
                        // Textureindex must change from the players pov to the texture pov, so from 640px/wall to 64px/wall
                        textureIndex = (int)(textureIndex / Board.mapScale);
                        textureIndex /= 10;

                        //See how big the wall should be 
                        curDist *= Math.cos(Math.toRadians(rayAngle - camera.direction));
                        double wallCalc;
                        if (curDist != 0){
                            wallCalc = Math.abs(2*camera.width/curDist);
                        } else {
                            wallCalc = camera.width;
                        }
                        int wallHeight = (int) wallCalc;

                        // I have no damn clue how this is working but it does sometimes this library is cooked

                        
                        int[][] pushData = new int[wallHeight][2];
                        int i = 0;
                        for (int y = -wallHeight/2; y < wallHeight/2; y++) {
                            int screenX = column;
                            int screenY = y + (bufferFrame.getHeight()/2);

                            if (screenY < 0 || screenY >= bufferFrame.getHeight()) continue;

                            float texYf = ((float)(y + wallHeight/2) / wallHeight) * 64;
                            int textureY = Math.min(63, (int)texYf);

                            int color = rgbData[textureY*64 + textureIndex];
                            pushData[i] = new int[]{screenX + screenY * bufferFrame.getWidth(),Color.DARK_GRAY.getRGB()};
                            i++;
                        }
                        for (int[] data : pushData){
                            pixels[data[0]] = data[1];
                        }


                                               
                        break;
                    };

                    startX = tempX;
                    startY = tempY;
                    curDist += raySpeed;

                    // Base case where ray is out of render distance to save on optimization purposes
                    if (curDist > camera.renderDist){
                        // Maybe i could make a fog effect thing later
                        break;
                    }
                }
            }
        }
    }

}