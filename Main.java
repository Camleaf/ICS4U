import pkg.board.Board;
import pkg.board.Ship;
import pkg.bot.Core;
import pkg.utils.Utils;
import java.util.Scanner;
import pkg.messaging.Response;

public class Main {
    Board pBoard; // player board
    Board aBoard; // bot board
    Scanner input;
    Core AI;
    public static void Clear(){System.out.print("\033\143");}

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {
        this.pBoard = new Board();
        this.aBoard = new Board();
        this.input = new Scanner(System.in);
        this.AI = new Core();
        this.initializeBoards();
        this.initializeAI();

        while (true){
            System.out.printf("Ships Remaining: You %d | Bot %d\n", pBoard.getAliveNumber(),aBoard.getAliveNumber());
            System.out.println("Your board");
            this.pBoard.displayDefense(new int[][]{});
            System.out.println("Enemy board");
            this.aBoard.displayOffense();
            System.out.println("Enter Coordinate: ");

            String[] target = input.nextLine().strip().split("");
            if (target.length != 2){
                Clear();
                System.out.println("Target must have length of 2");
                continue;
            }
            if (!Utils.contains(Board.LETTERS,target[0].toUpperCase())){
                Clear();
                System.out.println("First character of coordinate has to be a letter from A-J");
                continue;
            } else if (!Character.isDigit(target[1].toCharArray()[0])){
                //If char is not a digit
                Clear();
                System.out.println("Second character of coordinate has to be an Integer from 0-9");
                continue;
            };
            int[] playerCast = new int[]{Utils.indexOf(Board.LETTERS,target[0].toUpperCase()),Integer.parseInt(target[1])};
            Response response = this.aBoard.attack(playerCast);
            if (response.status == 11){ //Repeated attack
                Clear();
                System.out.println("you have already attacked that square");
                continue;
            }
            // check win
            if (aBoard.getAliveNumber() == 0){
                Clear();
                System.out.println("You Win!");
                break;
            }
            // AI attack
            int[] cast = this.AI.attack(pBoard);
            this.pBoard.attack(cast);
            Clear();
            System.out.printf("The bot attacked {%d, %d}\n", cast[0], cast[1]);
            //chcek AI win
            if (pBoard.getAliveNumber() == 0){
                Clear();
                System.out.println("You Lose...");
                break;
            }
        } 
        System.out.println("Your board");
        pBoard.displayDefense(new int[][]{});
        System.out.println("Enemy board");
        aBoard.displayDefense(new int[][]{});


        

    }
    public boolean checkBoundaries(int x,int y){
        return x <= 9 && x >= 0 && y <= 9 && y >= 0;
    }

    public void initializeAI(){
        this.AI.createShips(aBoard);
        // initialize attack here as well
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
