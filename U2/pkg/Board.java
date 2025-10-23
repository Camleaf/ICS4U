package pkg;

public class Board {


    // The 2d map. Each square represents a 600 x 600px area
    public static final int[][] map = {
			{0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
    };

    // The map to pixel scale. One tile should take up most of the screen
    public static final int mapScale = 600;

    // The height and width of the map
    public static final int mapLength = 8;


    public Board(){
        
    }


}
