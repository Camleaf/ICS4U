import pkg.board.Board;
import pkg.messaging.Response;
import java.util.Scanner;
import java.util.Optional;

public class Main {
    Board board;
    public static void main(String[] args){
        Main main = new Main();
    }

    public Main(){
        board = new Board();
        board.displayDefense(Board.defensePlaceholder);
        int[][] arr = {{0,0},{0,1},{1,1}};
        board.displayDefense(arr);

        int[][] newShip = {{6,5},{6,6},{6,7}};
        board.createShip(newShip, 3, 0);
        board.displayDefense(arr);
        int[] cast = {6,5};
        board.attack(cast);
        board.displayDefense(arr);
    }

    
}
