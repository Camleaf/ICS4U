package pkg;

public class Board {
    /*
     * Board is a class with exclusively static methods which contains the grid/playing field
    */

    // The 2d map. Each square represents a 600 x 600px area
    public static final int[][] map = {
			{1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,1},
            {1,0,1,0,1,1,0,1},
            {1,0,1,0,0,1,0,1},
            {1,0,1,1,0,1,0,1},
            {1,0,1,0,0,1,0,1},
            {1,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1},
    };

    // The map to pixel scale. One tile should take up most of the screen
    public static final int mapScale = 640;

    // The height and width of the map
    public static final int mapLength = 8;

    public static boolean isCollision(int x, int y){

        if (map[(int)x/mapScale][(int)y/mapScale]!=0){
            return true;
        }

        return false;
    }

    public static int getBoardSquare(int x, int y){
        return map[(int)x/mapScale][(int)y/mapScale];
    }

}
