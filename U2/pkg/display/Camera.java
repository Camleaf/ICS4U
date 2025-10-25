package pkg.display;
import pkg.display.KeyProcessor;
import pkg.Board;

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

    // Input enum for easier handling of input data
    private class Input {
        public static final int RIGHT_ARROW = 39; // maybe i'll swap out for mouse movements eventually
        public static final int LEFT_ARROW = 37;
        public static final int FORWARD = 87;
        public static final int RIGHT = 68;
        public static final int LEFT = 65;
        public static final int BACKWARD = 83;

    };
    

    public Camera(int x, int y, int width, int renderDist){

        // Initialize
        this.x = x;
        this.y = y;
        direction = 0;
        speed = 2.5;

        this.width = width;
        this.renderDist = renderDist;
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

        if (!Board.isCollision((int)tempX+curXSpeed*4, (int)tempY+curYSpeed*4)){
            x = tempX;
            y = tempY;
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
        if (!Board.isCollision((int)tempX+curXSpeed*4, (int)tempY+curYSpeed*4)){
            x = tempX;
            y = tempY;
        }



    }

}
