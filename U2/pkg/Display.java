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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;

public class Display {
    public Screen screen;
    public Camera camera;
    public boolean isHostage;
    public Board board;

    public Display(int startingX, int startingY) {
        screen = new Screen();
        board = new Board();
        camera = new Camera(startingX, startingY,700,Board.mapScale*7, board);
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
        screen.setIgnoreRepaint(true);
        initMenu();
    }

    public void switchToGame(){
        flush();
        screen.setIgnoreRepaint(true);
        screen.requestFocusInWindow();
    }

    public void holdHostage(){
        // keep a infinite loop running at a slow tick rate to hold the thread hostage
        isHostage = true;
        long previousTime = Instant.now().toEpochMilli();
        while (isHostage){
            // System.out.println("ishostage");
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
        Component[] collection = new Component[8];
        JLabel title = new JLabel();
        title.setBounds(100, 0, 200, 100);
        title.setText("Map Editor Menu");
        this.screen.add(title);
        collection[0] = title;

        JButton playGame = new JButton("Enter 3D Environment");
        playGame.setBounds(350, 500, 200, 100);
        this.screen.add(playGame);
        collection[1] = playGame;

        JLabel mapSizeLabel = new JLabel();
        mapSizeLabel.setBounds(100, 35, 150, 100);
        mapSizeLabel.setText(String.format("Map (side length %d):", Board.mapLength));
        this.screen.add(mapSizeLabel);
        collection[2] = mapSizeLabel;


        JTextArea mapText = new JTextArea();
        mapText.append(board.asString());
        mapText.setBounds(100, 100, 150,150);
        mapText.setLineWrap(true);
        this.screen.add(mapText);
        collection[3] = mapText;


        JLabel posLabel = new JLabel();
        posLabel.setBounds(300, 35, 300, 100);
        posLabel.setText("Player starting coordinates [(x,y) as block squares]");
        this.screen.add(posLabel);
        collection[4] = posLabel;


        JTextField posField = new JTextField();
        posField.setText(camera.boardSquareAsString());
        posField.setBounds(300, 100, 50,25);
        this.screen.add(posField);
        collection[5] = posField;


        JButton resetGame = new JButton("Reset Settings");
        resetGame.setBounds(100, 500, 200, 100);
        this.screen.add(resetGame);
        collection[6] = resetGame;

        JLabel errLabel = new JLabel();
        errLabel.setBounds(100, 350, 500, 100);
        errLabel.setText("");
        this.screen.add(errLabel);
        collection[7] = errLabel;


        this.screen.repaint();
        playGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isHostage = false;
            }
        });


        resetGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               camera.setGridCoords(1, 1);
               posField.setText(camera.boardSquareAsString());
               board.resetBoard();
               mapText.setText(board.asString());
               screen.repaint();
               
            }
        });



        while (true){ // Holds a loop in case bad input is given
            holdHostage(); // This holds the thread hostage until the eventlistener is called. acts as a resource-efficient wait until event
            
            boolean repeat = false;
            String[] boardInput = mapText.getText().split("\n");
            int[][] builtMap = new int[8][8];
            if (boardInput.length != 8){
                errLabel.setText("The board must have exactly 8 rows");
                continue;
            }
            
            int rIdx = 0; 
            for (String row : boardInput){ // iterate through rows on the board
                int cIdx = 0;
                String[] rowChars = row.split(",");

                if (rowChars.length != 8){
                    // check length
                    errLabel.setText("The board must have exactly 8 columns in each row");
                    repeat = true;
                    break;
                }

                // Iterate throw individual chars
                for (String Rchr : rowChars){
                    String chr = Rchr.trim();

                    if (!Utils.contains(Utils.numberChars,chr)){
                        errLabel.setText(String.format("Board squares must be a single integer above 0 and at below %d",Texture.textureIndexLimit));
                        repeat = true;
                        break;
                    } else if (Integer.parseInt(chr) < 0 || Integer.parseInt(chr) > Texture.textureIndexLimit){
                        errLabel.setText(String.format("Board squares must be a single integer above 0 and at below %d",Texture.textureIndexLimit));
                        repeat = true;
                        break;
                    }

                    builtMap[rIdx][cIdx] = Integer.parseInt(chr);

                    cIdx++;
                }
                rIdx++;
                if (repeat) break;
            } 
            if (repeat) continue;

            board.setBoard(builtMap); // sent parsed map off to board class


            // Handle player coord input. // put wall checking before this
            String[] inputCoords = posField.getText().split(",");
            if (inputCoords.length != 2){
                errLabel.setText("Input coords must be of length 2 with separator of \',\'");
                continue;
            } else if (!Utils.contains(Utils.numberChars,inputCoords[0].trim()) || !Utils.contains(Utils.numberChars,inputCoords[1].trim())){
                errLabel.setText("Input coords must be integers where 0 <= x < 8");
                continue;
            } else if (Integer.parseInt(inputCoords[0].trim()) > 7 || Integer.parseInt(inputCoords[1].trim()) > 7 || Integer.parseInt(inputCoords[0].trim()) < 0 || Integer.parseInt(inputCoords[1].trim()) < 0){
                errLabel.setText("Input coords must be integers where 0 <= x < 8");
                continue;
            } else if (board.isCollision(Integer.parseInt(inputCoords[0].trim())*Board.mapScale,Integer.parseInt(inputCoords[1].trim())*Board.mapScale)){
                errLabel.setText("Player initial position can not be inside of a wall");
                continue;
            }


            camera.setGridCoords(Integer.parseInt(inputCoords[0].trim()), Integer.parseInt(inputCoords[1].trim()));
            break;
        }
        // remove components i just added
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
            setLayout(null);
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
                    char[] windowText2 = "WSAD to move, Arrow keys to Rotate".toCharArray();
                    g.drawChars(windowText,0,windowText.length,10,65);
                    g.drawChars(windowText2,0,windowText2.length,10,50);
                    g.dispose();
                    
                } catch (Exception e){
                    
                } finally {}
                //push frame to tops

            } while (buffer.contentsLost()); // According to stackoverflow the buffer can be lost periodically so this helps
            if (!(buffer == null)){
                buffer.show();
            }
            buffer.dispose();
        }
        
        private void drawFrame(){
            int SKYBLUE = new Color(135, 206, 235).getRGB();
        //////////////////////////////////////////// Board setup ////////////////////////////////////////////
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = SKYBLUE;
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

                    if (board.isOutOfBounds(tempX, tempY)){
                        break;
                    }

                    
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

                            if (board.isCollision((curSqX+xVelOrient)*Board.mapScale, curSqY*Board.mapScale) || board.isCollision(curSqX*Board.mapScale, (curSqY+yVelOrient)*Board.mapScale)){

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

                                    if (board.isCollisionWith(tempX, tempY, curSqX, curSqY+yVelOrient)){
                                        intersect = 0;
                                        break;
                                    };

                                    if (board.isCollisionWith(tempX, tempY, curSqX+xVelOrient, curSqY)){
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
                    if (board.isCollision(tempX, tempY) || board.isOutOfBounds(tempX, tempY)){

                        // Get the texture data based on texture assigned to collided square
                        int[] rgbData = new int[]{};
                        int boardValue = board.getBoardSquare(tempX, tempY);
                        Wall texture = Texture.WALL.get(boardValue);
                        rgbData = texture.rgbData;

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