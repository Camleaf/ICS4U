package pkg.bot;
import pkg.board.Board;
import java.util.Random;
import java.awt.Point;

public class DefenseModule {
    public static void createShip(int shipLength, Board board, int shipNumber ){
       //Highest points is best rating
        Rating[] ratings = new Rating[100];

        // Add a random salt to the initial position ratings
        Random rand = new Random();

        int vertSalt = rand.nextInt(5);
        int horizSalt = rand.nextInt(5);

        int idx = 0;
        for (int row = 0;row<10;row++){
            for (int col = 0;col<10;col++){
                int up = rand.nextInt(15) - vertSalt;
                int down = rand.nextInt(15) - vertSalt;
                int left = rand.nextInt(15) - horizSalt;
                int right = rand.nextInt(15) - horizSalt;
                int[] miniRatingArr = {up,down,left,right};
                ratings[idx] = new Rating(row,col,miniRatingArr);
                idx++;
            }
        }


        int[][] existingShips = board.getAllShipSquares();
        for (Rating rating : ratings){
            idx = 0;
            for (Point direction : Board.DIRECTIONS){
                // Create the ship to test everything against
                Point[] testShip = new Point[shipLength];
                for (int index = 0;index<shipLength;index++){
                    testShip[index] = new Point(direction.x*index+rating.col, direction.y*index+rating.row);
                }

                for (int index = 0;index<shipLength;index++){
                    Point point = testShip[index];
                    if (point.x < 0 || point.y < 0 || point.x > 9 || point.y > 9){ // Hard limits
                        rating.rating[idx] -= 1000000;
                    } 

                    // If the ship is a two i want it to be closer to the edge. Not optimal but a personal preference
                    if (shipLength == 2 && !point.equals(new Point(9,9)) && !point.equals(new Point(0,0)) && !point.equals(new Point(0,9)) && !point.equals(new Point(9,0))){
                        rating.rating[idx] += 10;
                    } else if (point.x == 0 || point.y == 0 || point.x == 9 || point.y == 9) {
                        rating.rating[idx] -= 20;
                    }
                    


                    // Grabs distances from position to all currently existing other ship piecesx
                    int minDist = 21; // 1 greater than maximum possible distance on my grid
                    for (int[] pos : existingShips){
                        int taxicabDist = Math.abs(pos[0]-point.y) + Math.abs(pos[1]-point.x);
                        if (taxicabDist < minDist){minDist = taxicabDist;}
                    }

                    if (minDist < 2){
                        rating.rating[idx] -= 100;
                    } else {
                        rating.rating[idx] += minDist;
                    }

                }
                idx++;


            }
        }

        // Choose best rating
        int currentDir = 0;
        int currentScore = -100000000;
        int[] currentPos = new int[2];
        for (Rating rating : ratings){
        // I think theres a bug with direction so I will need to loop over each rt
            int i = 0;
            for (int score : rating.rating){
                if (score > currentScore){
                    currentScore = score;
                    currentDir = i;
                    currentPos = new int[]{rating.row,rating.col};
                }
                i++;
            }
        }

        // create ship at position
        int[][] testShip = new int[shipLength][];
        for (int index = 0;index<shipLength;index++){
            testShip[index] = new int[]{currentPos[0]+(Board.DIRECTIONS[currentDir].y*index), currentPos[1]+(Board.DIRECTIONS[currentDir].x*index)};
        }
        board.createShip(testShip, shipLength, shipNumber);


    }
}

class Rating {
    int row;
    int col;
    int[] rating;

    public Rating(int row,int col, int[] rating){
        this.row = row;
        this.col = col;
        this.rating = rating;
    }
}