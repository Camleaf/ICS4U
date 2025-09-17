import pkg.board.Board;
import pkg.board.Ship;
import pkg.utils.Utils;
import java.util.Scanner;
import java.lang.Exception;

public class Main {
    Board pBoard; // player board
    Board aBoard; // bot board
    Scanner input;
    public static void Clear(){System.out.print("\033\143");}

    public static void main(String[] args) throws Exception{
        Main main = new Main();
    }

    public Main() throws Exception{
        this.pBoard = new Board();
        this.aBoard = new Board();
        this.input = new Scanner(System.in);
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
    public boolean checkBoundaries(int x,int y){
        return x <= 9 && x >= 0 && y <= 9 && y >= 0;
    }
    public void initializeBoards(){
        int shipNumber = 0;
        for (int length : Ship.lengths){
            boolean playerChoosing = true;
            int[][] curShip = new int[length][2];
            for (int index = 0; index < length; index++){
                curShip[index][1] = index;
            }
            while (playerChoosing){
                // Takes input from player, moves ship accordingly
                this.pBoard.displayDefense(curShip);
                System.out.println("wsad for movement, enter to submit each char. c to confirm, r to rotate");
                char instruction;
                try{
                    instruction = input.nextLine().toLowerCase().charAt(0);
                } catch (IndexOutOfBoundsException e){Clear();continue;}

                // init new ship
                int[][] newShip = Utils.deepCopy(curShip);

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
                    case 'c':
                        boolean isMatch = false;
                        for (int[] pos : newShip){
                            if (pBoard.isShipMatch(pos)){
                                isMatch = true;
                                break;
                            }
                        }
                        if (!isMatch){
                            pBoard.createShip(newShip, newShip.length, shipNumber);
                            playerChoosing = false;
                        }
                        break;
                    case 'r': //Rotation
                        int[] rotationCenter = newShip[0].clone();
                        int[][] rotationPoints = new int[length][2];
                        int[][] antiRotationPoints = new int[length][2];

                        for (int i = 0;i<newShip.length;i++){
                            // center coords around head of ship, then inverse x and y to rotate around new origin, then add back coords of center to get the rotated ship.
                            int[] pos = {newShip[i][1]-rotationCenter[1]+rotationCenter[0],newShip[i][0]-rotationCenter[0]+rotationCenter[1]};
                            int[] antipos = {-(newShip[i][1]-rotationCenter[1])+rotationCenter[0],-(newShip[i][0]-rotationCenter[0])+rotationCenter[1]};
                            rotationPoints[i] = pos.clone();
                            antiRotationPoints[i] = antipos.clone();
                        }

                        boolean f = true;
                        for (int i = 0; i < curShip.length;i++){
                            if (!checkBoundaries(rotationPoints[i][0], rotationPoints[i][1])){
                                f = false;
                                break;
                            }   
                        }
                        if (f){newShip=rotationPoints;break;}
                        f = true;
                        // use antipos for counterclockwise rotation
                        for (int i = 0; i < curShip.length;i++){
                            if (!checkBoundaries(antiRotationPoints[i][0], antiRotationPoints[i][1])){
                                f = false;
                                break;
                            }   
                        }
                        if (f){newShip=Utils.reverseArray(antiRotationPoints);break;}
                        break;




                        // checkBoundaries(newShip[i][0], newShip[i][1]);
                }

                boolean b = true;
                for (int i = 0; i < curShip.length;i++){
                    if (!checkBoundaries(newShip[i][0], newShip[i][1])){
                        b = false;
                        break;
                    }
                }

                if (b){curShip = newShip;}
                Clear();
            }
            shipNumber += 1;

        }
    }
    
}
