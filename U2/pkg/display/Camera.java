package pkg.display;
import pkg.display.KeyProcessor;
import pkg.Board;

public class Camera {
    /*
     * The camera class is what the player sees. Responsible for keeping position, handling movement and providing location data to the renderer
     */


    public int x;
    public int y;
    public int width;
    public int renderDist;
    private int speed;
    private double direction;

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
        speed = 4;

        this.width = width;
        this.renderDist = renderDist;
    }


    public void update(KeyProcessor keyboard){
        //Do rotation

        if (keyboard.isKeyPressed(Input.RIGHT_ARROW)){
            direction += 0.5;
        }
        if (keyboard.isKeyPressed(Input.LEFT_ARROW)){
            direction -= 0.5;
        }
        // Movement
        int tempX = x;
        int tempY = y;

        if (keyboard.isKeyPressed(Input.FORWARD)){

            tempX += Math.round(speed * Math.sin(Math.toRadians(direction)));
            tempY += Math.round(speed * Math.cos(Math.toRadians(direction)));


        } else if (keyboard.isKeyPressed(Input.BACKWARD)){

            tempX += Math.round(speed * Math.sin(Math.toRadians(-direction)));
            tempY += Math.round(speed * Math.cos(Math.toRadians(-direction)));

        }


        if (keyboard.isKeyPressed(Input.RIGHT)){

            tempX += Math.round(speed * Math.sin(Math.toRadians(direction+90)));
            tempY += Math.round(speed * Math.cos(Math.toRadians(direction+90)));


        } else if (keyboard.isKeyPressed(Input.LEFT)){
            tempX += Math.round(speed * Math.sin(Math.toRadians(direction-90)));
            tempY += Math.round(speed * Math.cos(Math.toRadians(direction-90)));
        }
        
        // Check collision
        if (!Board.isCollision(tempX, tempY)){
            x = tempX;
            y = tempY;
        }



    }

}
