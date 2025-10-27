package pkg;
import javax.swing.JFrame;
import pkg.display.Camera;
import pkg.display.KeyProcessor;
import pkg.display.Texture;
import pkg.Board;
import pkg.Utils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import pkg.display.texture.Wall;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.time.Instant;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Display {
    public Screen screen;
    public Camera camera;
    public boolean isHostage;

    public Display(int startingX, int startingY) {
        screen = new Screen();
        camera = new Camera(startingX*Board.mapScale, startingY*Board.mapScale,700,Board.mapScale*7);
    }

    public void show(){
        screen.setVisible(true);
        screen.createBufferStrategy(2);
        screen.buffer = screen.getBufferStrategy();
    }

    public void flush(){
        screen.repaint();
        screen.createBufferStrategy(2);
        screen.buffer = screen.getBufferStrategy();
    }


    public void switchToMenuRender(boolean hostage){ // holds current thread hostage or not
        flush();
        screen.setIgnoreRepaint(false);
        initMenu();
    }

    public void switchToGame(){
        flush();
        screen.setIgnoreRepaint(true);
    }

    public void holdHostage(){
        // keep a infinite loop running at a slow tick rate to hold the thread hostage
        isHostage = true;
        long previousTime = Instant.now().toEpochMilli();
        while (isHostage){
            System.out.println("ishostage");
            long currentTime = Instant.now().toEpochMilli();
            if (currentTime - previousTime <= 1000){
                try{
                    Thread.sleep(Math.abs((currentTime+1000)-previousTime));
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void initMenu(){
        // Initialize all components on the menu
        //Just manually put in the components because why not
        Component[] collection = new Component[2];
        JLabel j = new JLabel();
        j.setBounds(100, 0, 200, 100);
        j.setText("Map Builder Menu");
        this.screen.add(j);
        collection[0] = j;


        JButton b = new JButton("Enter 3D Environment");
        b.setBounds(250, 500, 200, 100);
        this.screen.add(b);
        collection[1] = b;
        this.screen.repaint();

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isHostage = false;
            }
        });


        holdHostage();
        // remove components i just adde.
        // I'm aware removeall exists but that gets rid of stuff I'd like to keep
        for (Component comp : collection){
            this.screen.remove(comp);
        }
        switchToGame();





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

                createBufferStrategy(2);
            }
            buffer = getBufferStrategy();
            
            // following dowhile loop was taken from stackoverflow
            do {
                try{
                    // get buffer graphics then draw next frame
                    drawFrame();
                    Graphics g =  buffer.getDrawGraphics();
                    g.drawImage(bufferFrame, 0, 0, null);
                    char[] windowText = "Press Esc for map editor".toCharArray();
                    g.drawChars(windowText,0,windowText.length,10,50);
                    g.dispose();
                } finally {}
                //push frame to tops

            } while (buffer.contentsLost()); // According to stackoverflow the buffer can be lost periodically so this helps
            buffer.show();
            buffer.dispose();
        }

        private void drawFrame(){

        //////////////////////////////////////////// Board setup ////////////////////////////////////////////
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = new Color(135, 206, 235).getRGB();
            }
            for (int x = 0; x < bufferFrame.getWidth()-1; x++) {
                for (int y = bufferFrame.getHeight()/2; y < bufferFrame.getHeight()-1;y++){
                    pixels[x+y*bufferFrame.getWidth()] = Color.darkGray.getRGB();
                }
            }
            // fov should be 75. Around what the original raycasted programs used

            // Now draw your scene or buffer

            // Initialize the angle system
            
            double rayAngle = camera.direction-camera.FOV / 2.0; //current angle
            double angleIncrease = camera.FOV/(camera.width*1.0); //the increase in ray for each angle to have a 75 deg FOV


            // At the moment the only thing stopping me from increasing performance by increasing the rayspeed upto a higher level is because of the 
            //                                                                  corner clipping issue which causes server corner definition issues
            // I have a fix which made the corner clipping a lot better. still has a bit that could be better but overall is great

            // increasing rayspeed increases loading speed, however it increases time per operation of smoothing and reduces visual fidelity.
            // increasing backStepSpeed is vice versa, however it should never be touched as unless raySpeed is extremely high its impact on visual fidelity is much greater than that on performance
            double raySpeed = 2;
            double backStepSpeed = 0.1;


        //////////////////////////////////////////// Ray movement ////////////////////////////////////////////
            for (int column = 0;column<camera.width;column++){
                if (column > 0) rayAngle += angleIncrease;
                
                
                double startX = camera.x;
                double startY = camera.y;

                double curDist = 0;

                int[] prevSquare = new int[]{(int)(startX/Board.mapScale),(int)(startY/Board.mapScale)};
                int intersect = 0; // Horizontal is 0, vertical is 1
                while(true){
                    
   
                    // Initialize temporary variables for checking
                    double tempX = startX;
                    double tempY = startY;
                    

                    // add to temp position variables movement normalized for raycast angle
                    double xVel = (raySpeed * Math.sin(Math.toRadians(rayAngle%360)));
                    double yVel = (raySpeed * Math.cos(Math.toRadians(rayAngle%360)));
                    double BackstepXVel = (backStepSpeed * Math.sin(Math.toRadians((rayAngle%360)-180)));
                    double BackstepYVel = (backStepSpeed * Math.cos(Math.toRadians((rayAngle%360)-180)));
                    tempX += xVel;
                    tempY += yVel;

                    
                    // Check if the ray has passed into another square and adjust intersect settings accordingly
                    // This block also has a optimization/shortcut where if it passes diagonally through a square that it will default to horizontal intersect

        //////////////////////////////////////////// Intersect side/corner checking ////////////////////////////////////////////
                    int curSqX = (int)(tempX/Board.mapScale);
                    int curSqY = (int)(tempY/Board.mapScale);
                    if (Math.abs(curSqX-prevSquare[0]) !=0 || Math.abs(curSqY-prevSquare[1])!=0){


                        // so as it stands my diagonal code works a bit but not too much.
                        // New idea as pseudocode (not what is implemented right now)
                        /*
                         * 1.check if ray passed diagonally
                         * 2a. if either of the squares it passed were a hit, backtrack until it hits the square or until the backtrack distance is equal to the regular ray speed
                         *      3a - Update intersection status accordingly
                         * 2b.if no hits, just keep the same intersection status as before because it will have the same
                         * 
                         * 
                         * 
                         */

                        if (Math.abs(curSqX-prevSquare[0]) != 0 && Math.abs(curSqY-prevSquare[1])!=0){ // If passed diagonally

                            // Check if it passed any other squares on the way to being a diagonal
                            int xVelOrient = -Utils.sign(xVel);
                            int yVelOrient = -Utils.sign(yVel);


                            if (Board.isCollision((curSqX+xVelOrient)*Board.mapScale, curSqY*Board.mapScale) || Board.isCollision(curSqX*Board.mapScale, (curSqY+yVelOrient)*Board.mapScale)){

                                double stepAcc = 0; // accumulates backstepspeeds
                                int steps = 0;
                                while (true){

                                    if (stepAcc >= raySpeed){
                                        curDist += stepAcc;
                                        tempX -= BackstepXVel*steps;
                                        tempY -= BackstepYVel*steps;
                                        intersect = (intersect == 0) ? 1 : 0;
                                        break; 
                                    }

                                    if (Board.isCollisionWith(tempX, tempY, curSqX, curSqY+yVelOrient)){
                                        intersect = 0;
                                        break;
                                    };

                                    if (Board.isCollisionWith(tempX, tempY, curSqX+xVelOrient, curSqY)){
                                        intersect = 1;
                                        break;
                                    };


                                    tempX += BackstepXVel;
                                    tempY += BackstepYVel;
                                    curDist -= backStepSpeed;
                                    stepAcc += backStepSpeed;
                                    steps += 1;
                                }
                            }                            
                        }
                        else 
                        if (Math.abs(curSqX-prevSquare[0]) !=0){ // if passed horizontally
                            intersect = 0;
                        } else if (Math.abs(curSqY-prevSquare[1])!=0){ // if passed vertically
                            intersect = 1;
                        }
                        prevSquare = new int[]{curSqX,curSqY};
                    }


        //////////////////////////////////////////// Collision Logic ////////////////////////////////////////////
                    if (Board.isCollision(tempX, tempY)){



                        // Get the texture data based on texture assigned to collided square
                        int boardValue = Board.getBoardSquare(tempX, tempY);
                        Wall texture = Texture.WALL.get(boardValue);
                        int[] rgbData = texture.rgbData;


                         // step backwards until we find exact edge intersect
                         // This is to smooth out the edges of my walls
                         // I found that using my earlier heuristic method where I effectively guessed where a wall is intersected, it would create blocky instead of smooth walls
                         // If i feel like i have a bit of performance to spare, i should introduce checking for rays which pass through walls into other walls so that corners have more definition
                         // also should make a checker for when rays pass through the corners of walls into open space to improve upon that corner definition because that is a sore spot atm
                        if (intersect == 0){

                            double target;
                            if (xVel < 0){
                                
                                target = ((int)(tempX/Board.mapScale)) * Board.mapScale + Board.mapScale;
            
                            } else {
                                target = ((int)(tempX/Board.mapScale)) * Board.mapScale;
                            }

                            while (Math.abs(target-(tempX + BackstepXVel)) > 0.15){
                                tempX += BackstepXVel;
                                tempY += BackstepYVel;
                                curDist -= backStepSpeed;
                            }

                        } else {
                            double target;
                            if (yVel < 0){
                                
                                target = ((int)(tempY/Board.mapScale)) * Board.mapScale + Board.mapScale;
                                
            
                            } else {
                                target = ((int)(tempY/Board.mapScale)) * Board.mapScale;
                            }

                            while (Math.abs(target-(tempY + BackstepYVel)) > 0.15){
                                tempX += BackstepXVel;
                                tempY += BackstepYVel;
                                curDist -= backStepSpeed;
                            }

                        }


                        // Set the x-index to grab from texture to x or y depending on collision location
                        double dTextureIndex = tempY;
                        if (intersect == 1){
                            dTextureIndex = tempX;
                        }

                        // Textureindex must change from the players pov to the texture pov, so from 640px/wall to 64px/wall
                        int textureIndex = (int)((dTextureIndex % Board.mapScale));


                        //See how big the wall should be 
                        curDist *= Math.cos(Math.toRadians(rayAngle - camera.direction));
                        double wallCalc;
                        if (curDist != 0){
                            wallCalc = Math.abs(32*camera.width/curDist);
                        } else {
                            wallCalc = camera.width;
                        }
                        
                        int wallHeight = (wallCalc < bufferFrame.getHeight()*4) ? (int) wallCalc : bufferFrame.getHeight()*4-1;

                        // I have no damn clue how this is working but it does sometimes this library is cooked

                        
                        int[][] pushData = new int[wallHeight][2];
                        int i = 0;
                        for (int y = -wallHeight/2; y < wallHeight/2; y++) {
                            int screenX = column;
                            int screenY = y + (bufferFrame.getHeight()/2);

                            if (screenY < 0 || screenY >= bufferFrame.getHeight()) continue;

                            float texYf = ((float)(y + wallHeight/2) / wallHeight) * 64;
                            int textureY = Math.min(63, (int)texYf);
                            
                            if (textureIndex < 0)textureIndex = 0;
                            else if (textureIndex >= 64)textureIndex = 63;
                            if (textureY < 0)textureY = 0;
                            else if (textureY >= 64)textureY = 63;

                            int color = rgbData[textureY*64 + textureIndex];
                            // I need to figure out why this colouring isn't working
                            pushData[i] = new int[]{screenX + screenY * bufferFrame.getWidth(),color};
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