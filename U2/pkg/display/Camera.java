package pkg.display;
import pkg.display.KeyProcessor;
import pkg.Board;
import pkg.Utils;

public class Camera {
    /*
     * The camera class is what the player sees. Responsible for keeping position, handling movement and providing location data to the renderer
     */


    public double x;
    public double y;
    public int width;
    public int renderDist;
    public int FOV = 75;
    private double speed;
    public double direction;
    private Board board;

    // Input enum for easier handling of input data
    private class Input {
        public static final int RIGHT_ARROW = 39; // maybe i'll swap out for mouse movements eventually
        public static final int LEFT_ARROW = 37;
        public static final int FORWARD = 87;
        public static final int RIGHT = 68;
        public static final int LEFT = 65;
        public static final int BACKWARD = 83;

    };
    

    public Camera(int mapX, int mapY, int width, int renderDist, Board board){
        this.board = board;
        // Initialize
        setGridCoords(mapX, mapY);
        direction = 0;
        speed = 2.5;

        this.width = width;
        this.renderDist = renderDist;
    }

    public void setGridCoords(int mapX,int mapY){
        this.x = mapX * Board.mapScale + Board.mapScale/2;
        this.y = mapY * Board.mapScale + Board.mapScale/2;
    }


    public void update(KeyProcessor keyboard){
        //Do rotation

        if (keyboard.isKeyPressed(Input.RIGHT_ARROW)){
            direction += 3;
        }
        if (keyboard.isKeyPressed(Input.LEFT_ARROW)){
            direction -= 3;
        }
        // Movement
        double tempX = x;
        double tempY = y;

        double curXSpeed = 0;
        double curYSpeed = 0;

        if (keyboard.isKeyPressed(Input.FORWARD)){


            curXSpeed = speed * Math.sin(Math.toRadians(direction));
            curYSpeed = speed * Math.cos(Math.toRadians(direction));
            tempX += curXSpeed;
            tempY += curYSpeed;


        } else if (keyboard.isKeyPressed(Input.BACKWARD)){

            curXSpeed = -speed * Math.sin(Math.toRadians(direction));
            curYSpeed = -speed * Math.cos(Math.toRadians(direction));
            tempX += curXSpeed;
            tempY += curYSpeed;
        }


        /////// left-right movement///////
        if (keyboard.isKeyPressed(Input.RIGHT)){

            curXSpeed = speed * Math.sin(Math.toRadians(direction+90));
            curYSpeed = speed * Math.cos(Math.toRadians(direction+90));
            tempX += curXSpeed;
            tempY += curYSpeed;

        } else if (keyboard.isKeyPressed(Input.LEFT)){
            curXSpeed = -speed * Math.sin(Math.toRadians(direction+90));
            curYSpeed = -speed * Math.cos(Math.toRadians(direction+90));
            tempX += curXSpeed;
            tempY += curYSpeed;
        }
        
        // Check collision
        if (!board.isCollision(tempX+(speed*4), tempY) && !board.isCollision(tempX-(speed*4), tempY)){
            x = tempX;
        }

        if (!board.isCollision(tempX, tempY+(speed*4)) && !board.isCollision(tempX, tempY-(speed*4))){
            y = tempY;
        }


    }

    public String boardSquareAsString(){
        String output = String.format("%d, %d",(int)(x/Board.mapScale), (int)(y/Board.mapScale));
        return output;
    }

}
