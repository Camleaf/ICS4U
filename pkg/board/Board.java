package pkg.board;

import java.util.ArrayList;
import pkg.messaging.Response;
import pkg.utils.Utils;
import java.awt.Point;


public class Board {
    private int[][] grid;
    private Ship[] shipArr = new Ship[5];
    public static int[][] emptyIntInt = {};
    public static final String[] LETTERS = {"A","B","C","D","E","F","G","H","I","J"};
    public static final Point[] DIRECTIONS = {new Point(1,0),new Point(0,1),new Point(-1,0),new Point(0,-1)};
    /*
     Grid class.
     On board, 0 represents empty, 1 represents attacked already
     */




    public Board(){
        this.grid = new int[10][10];
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
        for (Ship ship : this.shipArr) {
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
        for (Ship ship : this.shipArr) {
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
        } else if (this.grid[query[0]][query[1]] == 1){
            // logic here is that if the grid has been attacked here but there is no hit it has to be a miss.
            return 2;
        }
        return 0;
    }  

    public int[][] getAllShipSquares(){
        ArrayList<int[]> shipSquares = new ArrayList<int[]>();
        for (Ship ship : this.shipArr) {
            if (ship == null){
                break;
            }
            for (int[] pos : ship.getCoordinates()){
                shipSquares.add(pos.clone());
            }
        }
        return shipSquares.toArray(new int[shipSquares.size()][]);
    }

    public int[][] getAllShipHits(){
        // Gets all hits that have happened up to this point on the board
        ArrayList<int[]> shipHits = new ArrayList<int[]>();
        for (Ship ship : this.shipArr) {
            if (ship == null){
                break;
            }
            for (int[] pos : ship.getHitCoordinates()){
                shipHits.add(pos.clone());
            }
        }
        return shipHits.toArray(new int[shipHits.size()][]);
    }

    public int[] getAliveShipLengths(){
        ArrayList<Integer> shipLengths = new ArrayList<Integer>();
        for (Ship ship : this.shipArr) {
            if (ship == null){
                break;
            }
            if (ship.isSunken()){
                continue;
            }
            shipLengths.add(ship.getLength());
        }
        int[] newArr = new int[shipLengths.size()];
        int idx = 0;
        for (int len : shipLengths){
            newArr[idx] = len;
            idx++;
        }
        return newArr;
    }

    public int[][] getSunkenCoordinates(){
        ArrayList<int[]> shipHits = new ArrayList<int[]>();
        for (Ship ship : this.shipArr) {
            if (ship == null){
                break;
            }
            if (!ship.isSunken()){continue;}

            for (int[] pos : ship.getCoordinates()){
                shipHits.add(pos.clone());
            }
        }
        return shipHits.toArray(new int[shipHits.size()][]);
    }


    public Response attack(int[] query){
        this.shipArr[0].hasHit(query);

        // Check for valid input
        if (!verifyAttackStructure(query)) {
            return new Response("Invalid Attack Structure",50);
        }

        // Send input through grid to check
        int tile = this.grid[query[0]][query[1]];

        if (tile == 1) {
            return new Response("Repeated Attack", 11);
        }

        this.grid[query[0]][query[1]] = 1;

        for (Ship ship : this.shipArr){
                if (ship == null){break;}
                ship.castHit(query);
                // add sink, miss, and hit returns
            }
        return new Response("Success",10);
    }



    public void createShip(int[][] coords, int length, int index){
        this.shipArr[index] = new Ship(length, coords);
    }


    public int[][] getLegalMoves(){
        // figure out how to do this in an efficient way
        ArrayList<int[]> legalMoves = new ArrayList<int[]>();
        for (int row = 0;row<this.grid.length;row++ ){
            for (int col = 0;col<this.grid[0].length;col++){
                if (this.grid[row][col] == 0){
                    int[] pos = {row,col};
                    legalMoves.add(pos);
                }
            }
            
        }

        return legalMoves.toArray(new int[legalMoves.size()][]);
    }



    public void displayDefense(int[][] temporaryDisplay){
        String curRow;
        int[][] temp = temporaryDisplay;

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int row = 0;row < this.grid.length;row++){
            curRow = Board.LETTERS[row];

            for (int col = 0; col < this.grid[row].length;col++){

                
                int[] query = {row,col};
                int hex = getTileStatus(query);

                if (Utils.contains(temp, query)){
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
        for (int row = 0;row < this.grid.length;row++){
            curRow = Board.LETTERS[row];
            
            for (int col = 0; col < this.grid[row].length;col++){
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