package pkg;

public class Board {
    /*
     * Board is a class with exclusively static methods which contains the grid/playing field
    */

    // The 2d map. Each square represents a 600 x 600px area
    public static final int[][] map = {
			{2,2,2,2,2,2,2,2},
            {2,0,0,0,0,0,0,2},
            {2,0,2,0,2,2,0,2},
            {2,0,2,0,0,2,0,2},
            {2,0,0,2,0,2,0,2},
            {2,0,2,0,0,2,0,2},
            {2,0,0,0,0,0,0,2},
            {2,2,2,2,2,2,2,2},
    };

    // The map to pixel scale. One tile should take up most of the screen
    public static final int mapScale = 64;

    // The height and width of the map
    public static final int mapLength = 8;

    public static boolean isCollision(int x, int y){

        if (map[(int)y/mapScale][(int)x/mapScale]!=0){
            return true;
        }

        return false;
    }

    public static boolean isCollision(double x, double y){

        if (map[(int)(y/mapScale)][(int)(x/mapScale)]!=0){
            return true;
        }

        return false;
    }

    public static boolean isCollisionWith(double x, double y, int mapX, int mapY){

        int tileX = (int)Math.floor(x / mapScale);
        int tileY = (int)Math.floor(y / mapScale);
        if (tileX == mapX && tileY == mapY) {
            return map[mapY][mapX] != 0;
        }
        return false;

    }


    public static int getBoardSquare(int x, int y){
        return map[(int)y/mapScale][(int)x/mapScale];
    }

    public static int getBoardSquare(double x, double y){
        return map[(int)(y/mapScale)][(int)(x/mapScale)];
    }

    public static String asString(){
        String output = "";
        for (int row = 0; row < mapLength; row++){
            for (int col = 0; col < mapLength; col++){
                output += Integer.toString(map[row][col]) + ((col != mapLength-1) ? ",  " : "");
            }
            if (row != mapLength-1){
                output += "\n";
            }
            
        }
        return output;
    }

}


 ////////////MAPS/////////////////
 /// 
// Default maps:
// Brick Walls
// {2,2,2,2,2,2,2,2},
// {2,0,0,0,0,0,0,2},
// {2,0,2,0,2,2,0,2},
// {2,0,2,0,0,2,0,2},
// {2,0,2,2,0,2,0,2},
// {2,0,2,0,0,2,0,2},
// {2,0,0,0,0,0,0,2},
// {2,2,2,2,2,2,2,2},
//
// Placeholder Walls:
// {1,1,1,1,1,1,1,1},
// {1,0,0,0,0,0,0,1},
// {1,0,1,0,1,1,0,1},
// {1,0,1,0,0,1,0,1},
// {1,0,1,1,0,1,0,1},
// {1,0,1,0,0,1,0,1},
// {1,0,0,0,0,0,0,1},
// {1,1,1,1,1,1,1,1},

