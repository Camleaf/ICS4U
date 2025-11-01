package pkg;

public class Board {
    /*
     * Board is a class with exclusively static methods which contains the grid/playing field
    */

    // The 2d map. Each square represents a 600 x 600px area

    // predefined map by me
    public static final int[][] defaultMap = {
			{2,2,2,2,2,2,2,2},
            {2,0,0,0,0,0,0,2},
            {2,0,1,0,2,2,0,2},
            {2,0,1,0,0,2,0,2},
            {2,0,0,1,0,3,0,3},
            {2,0,2,0,0,3,0,3},
            {2,0,0,0,0,0,0,3},
            {2,2,2,2,2,3,3,3},
    };


    // mutable map by user. default set to my predefined map
    public int[][] map = Utils.deepCopy(defaultMap);
    // The map to pixel scale. One tile should take up most of the screen
    public static final int mapScale = 64;

    // The height and width of the map
    public static final int mapLength = 8;

    public boolean isCollision(int x, int y){

        if (map[(int)y/mapScale][(int)x/mapScale]!=0){
            return true;
        }

        return false;
    }

    public boolean isCollision(double x, double y){
        if (isOutOfBounds(x,y)){
            return true;
        }
        if (map[(int)(y/mapScale)][(int)(x/mapScale)]!=0){
            return true;
        }

        return false;
    }

    public boolean isCollisionWith(double x, double y, int mapX, int mapY){

        int tileX = (int)Math.floor(x / mapScale);
        int tileY = (int)Math.floor(y / mapScale);
        if (tileX == mapX && tileY == mapY) {
            return map[mapY][mapX] != 0;
        }
        return false;

    }


    public int getBoardSquare(int x, int y){
        return map[(int)y/mapScale][(int)x/mapScale];
    }

    public int getBoardSquare(double x, double y){
        return map[(int)(y/mapScale)][(int)(x/mapScale)];
    }

    public void setBoard(int[][] map){
        this.map = Utils.deepCopy(map);
    }

    public boolean isOutOfBounds(double x, double y){
        return y < 0 || x < 0 || y >= mapLength*mapScale || x >= mapLength*mapScale;
    }

    public void resetBoard(){
        setBoard(defaultMap);
    }

    public String asString(){
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

