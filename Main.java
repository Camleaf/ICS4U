import pkg.board.Board;
import pkg.messaging.Response;
import java.util.Scanner;

public class Main {
    Board board;
    public static void main(String[] args){
        Main main = new Main();
    }

    public Main(){
        board = new Board();
        board.displayBoard();
    }
}
