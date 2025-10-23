package pkg;

public class Board {
    /*
     * Board is a class with exclusively static methods which contains the grid/playing field
    */

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
    public static final int mapScale = 640;

    // The height and width of the map
    public static final int mapLength = 8;

    public static boolean isCollision(int x, int y){

        if (map[x%mapScale][y%mapScale]!=0){
            return true;
        }

        return false;
    }


}
