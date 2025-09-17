import pkg.board.Board;
import pkg.utils.Utils;

public class Main {
    Board board;
    public static void main(String[] args) throws Exception{
        Main main = new Main();
    }

    public Main() throws Exception{
        board = new Board();
        board.displayDefense(Board.defensePlaceholder);
        int[][] arr = {{0,0},{0,1},{1,1}};
        board.displayDefense(arr);

        int[][] newShip = {{6,5},{6,6},{6,7}};
        board.createShip(newShip, 3, 0);
        board.displayDefense(arr);
        int[] cast = {6,5};
        board.attack(cast);
        board.displayDefense(Board.defensePlaceholder);
        cast[0] = 3;
        cast[1] = 3;
        board.attack(cast);
        board.displayDefense(Board.defensePlaceholder);

        cast[0] = 9;
        cast[1] = 9;
        board.attack(cast);
        board.displayDefense(Board.defensePlaceholder);
        // // System.out.print("\033\143"); screen clear


        // int[][] r = {{-1,-1},{-1,-1},{-1,-1}};
        // int[] x = {0,0};
        // System.out.println(Utils.containsArray(r, x));
        // for (int[] hit : r){
        //     System.out.printf("{%d, %d} : {%d, %d}\n",hit[0],hit[1], x[0], x[1]);
        // }

    }

    
}
