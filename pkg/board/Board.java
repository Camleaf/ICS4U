package pkg.board;
import pkg.messaging.Response;

public class Board {
    private int[][] grid;
    static final String[] letters = {"A","B","C","D","E","F","G","H","I","J"};
    /*
     Grid class.
     On board, 0 represents empty, 1 represents ship, 2 represents miss, and 3 represents hit
     */
    public Board(){
        makeGrid();
    }

    public void makeGrid(){
        /*
        Creates a 10x10 2d integer array filled with zeros
         */
        grid = new int[10][10];

        for (int row = 0;row<10;row++){
            for (int col = 0; col<10;col++){
                grid[row][col] = 0;
            }
        }

        
    }

    static boolean verifyAttackStructure(int[] target){
        // Add verification logic to make sure it is in bounds
        if (target.length != 2){
            return false;
        } 

        if (10 <= target[0] || target[0] < 0 || 10 <= target[1] || target[1] < 0){
            return false;
        }


        return true;
    }



    public Response attackBoard(int[] target){
         
        // Check for valid input
        if (!verifyAttackStructure(target)) {
            return new Response("Invalid Attack Structure",50);
        }

        // Send input through grid to check
        int tile = grid[target[0]][target[1]];
        switch (tile) {
            case 0:
                grid[target[0]][target[1]] = 2;
                return new Response("Fail",10);
            case 1:
                grid[target[0]][target[1]] = 3;
                return new Response("Success",20);
            default:
                return new Response("Repeated Attack", 11);
        }
    }

    public void createShip(){

    }

    public void displayBoard(){
        String curRow;

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int row = 0;row < grid.length;row++){
            curRow = Board.letters[row];

            for (int col = 0; col < grid[row].length;col++){

                int hex = grid[row][col];
                if (hex == 0){
                    curRow += " " + "·";
                } else if (hex == 1) {
                    curRow += " " + "▧";
                } else if (hex == 2) {
                    curRow += " " + "/";
                } else if (hex == 3) {
                    curRow += " " + "+";
                }
            }
            System.out.println(curRow);
        }
    }

}