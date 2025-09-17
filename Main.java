import pkg.board.Board;
import pkg.board.Ship;
import pkg.utils.Utils;
import pkg.utils.Utils.*;

import java.util.Arrays;
import java.util.Scanner;
import java.lang.Exception;

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
            while (true){
                this.pBoard.displayDefense(curShip);
                System.out.println("wsad for movement, enter to submit each char. c to confirm, r to rotate");
                char instruction;
                try{
                    instruction = input.nextLine().toLowerCase().charAt(0);
                } catch (IndexOutOfBoundsException e){continue;}

                // init new ship
                int[][] newShip = new int[length][2];
                for (int i=0;i<curShip.length;i++){
                    newShip[i] = curShip[i].clone();
                }

                switch (instruction){
                    case 'w':
                        for (int i = 0; i < curShip.length;i++){
                            newShip[i][0] -= 1;
                        }
                        break;
                    case 's':
                        for (int i = 0; i < curShip.length;i++){
                            newShip[i][0] += 1;
                        }
                        break;
                    case 'd':
                        for (int i = 0; i < curShip.length;i++){
                            newShip[i][1] += 1;
                        }
                        break;
                    case 'a':
                        for (int i = 0; i < curShip.length;i++){
                            newShip[i][1] -= 1;
                        }
                        break;
                }
                boolean b = true;
                for (int i = 0; i < curShip.length;i++){
                    if (newShip[i][0] > 9 || newShip[i][0] < 0 || newShip[i][1] > 9 || newShip[i][1] < 0){
                        b = false;
                        break;
                    }
                }
                if (b){curShip = newShip;}
                System.out.print("\033\143");
            }

        }
    }
    
}
