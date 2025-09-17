import pkg.board.Board;
import pkg.board.Ship;
import pkg.utils.Utils;
import pkg.utils.Utils.*;
import java.util.Scanner;

public class Main {
    Board pBoard; // player board
    Board aBoard; // bot board
    Scanner input;
    public static void main(String[] args) throws Exception{
        Main main = new Main();
        
    }

    public Main() throws Exception{
        this.pBoard = new Board();
        this.aBoard = new Board();
        this.input = new Scanner(System.in);
        // board.displayDefense(Board.emptyIntInt);
        // int[][] arr = {{0,0},{0,1},{1,1}};
        // board.displayDefense(arr);

        // int[][] newShip = {{6,5},{6,6},{6,7}};
        // board.createShip(newShip, 3, 0);
        // board.displayDefense(arr);
        // int[] cast = {6,5};
        // board.attack(cast);
        // board.displayDefense(Board.emptyIntInt);
        // cast[0] = 3;
        // cast[1] = 3;
        // board.attack(cast);
        // board.displayDefense(Board.emptyIntInt);

        // cast[0] = 9;
        // cast[1] = 9;
        // board.attack(cast);
        // board.displayDefense(Board.emptyIntInt);
        // // System.out.print("\033\143"); screen clear
        this.initializeBoards();

    }
    public void initializeBoards(){
        for (int length : Ship.lengths){
            int[][] curShip = new int[length][2];
            for (int index = 0; index < length; index++){
                curShip[index][1] = index;
            }
            this.pBoard.displayDefense(curShip);
            System.out.println("Press Enter to clear screen");
            input.nextLine();
            System.out.print("\033\143");

        }
    }

    
}
