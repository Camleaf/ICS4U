package pkg.board;

import pkg.messaging.Response;
import pkg.utils.Utils;


public class Board {
    private int[][] grid;
    private Ship[] shipArr = new Ship[5];
    public static int[][] defensePlaceholder = {};
    public static final String[] letters = {"A","B","C","D","E","F","G","H","I","J"};
    public static final String[] outChars = {};
    /*
     Grid class.
     On board, 0 represents empty, 1 represents attacked already
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

    static boolean verifyAttackStructure(int[] query){
        // Add verification logic to make sure it is in bounds
        if (query.length != 2){
            return false;
        } 

        if (10 <= query[0] || query[0] < 0 || 10 <= query[1] || query[1] < 0){
            return false;
        }

        return true;
    }

    public boolean isShipMatch(int[] query) {
        for (Ship ship : shipArr) {
            if (ship == null){
                return false; 
            }
            if (ship.hasMatch(query)){
                return true;
            }
        }
        return false;
    }

    public boolean isHitMatch(int[] query) {
        for (Ship ship : shipArr) {
            if (ship == null){
                return false; // since i'm adding the ships in linear fashion, if I meet one that doesn't exist yet, it should end the function.
            }
            if (ship.hasHit(query)){
                return true;
            }
        }
        return false;
    }

    public int getTileStatus(int[] query) {
        if (isHitMatch(query)){
            return 3;
        } else if (isShipMatch(query)){
            return 1;
        } else if (grid[query[0]][query[1]] == 1){
            // logic here is that if the grid has been attacked here but there is no hit it has to be a miss.
            return 2;
        }
        return 0;
    }  

    public Response attack(int[] query){

        // Check for valid input
        if (!verifyAttackStructure(query)) {
            return new Response("Invalid Attack Structure",50);
        }

        // Send input through grid to check
        int tile = grid[query[0]][query[1]];

        if (tile == 1) {
            return new Response("Repeated Attack", 11);
        }

        grid[query[0]][query[1]] = 1;

        for (Ship ship : shipArr){
                if (ship == null){break;}
                System.out.println(ship.castHit(query).message);
            }
        return new Response("Success",10);
    }



    public void createShip(int[][] coords, int length, int index){
        shipArr[index] = new Ship(length, coords);
    }



    public void displayDefense(int[][] temporaryDisplay){
        String curRow;
        int[][] temp = temporaryDisplay;

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int row = 0;row < grid.length;row++){
            curRow = Board.letters[row];

            for (int col = 0; col < grid[row].length;col++){

                
                int[] query = {row,col};
                int hex = getTileStatus(query);

                if (Utils.containsArray(temp, query)){
                    //exception case for temporary display 
                    curRow += " □";
                } else if (hex == 0){
                    curRow += " ·";
                } else if (hex == 1) {
                    curRow += " ▧";
                } else if (hex == 2) {
                    curRow += " /";
                } else if (hex == 3) {
                    curRow += " +";
                }

            }
            System.out.println(curRow);
            
        }
    }

    public void displayOffense(){
        String curRow;

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int row = 0;row < grid.length;row++){
            curRow = Board.letters[row];
            
            for (int col = 0; col < grid[row].length;col++){
                int[] query = {row,col};
                int hex = getTileStatus(query);
                if (hex == 0 || hex == 1){
                    curRow += " ·";
                } else if (hex == 2) {
                    curRow += " /";
                } else if (hex == 3) {
                    curRow += " +";
                }
            }
            System.out.println(curRow);
        }
    }

}