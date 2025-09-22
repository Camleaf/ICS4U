package pkg.bot;
import java.util.Random;

import pkg.board.Board;
import pkg.utils.Utils;

import java.awt.Point;

public class AttackModule {
    public static int[] getAttackCoordinate(Board board, int difficulty){
        switch (difficulty){
            case 1:
                return randomAttack(board);
            case 2:
                return normalAttack(board);
            case 3:
                return impossibleAttack(board);
        }
        return new int[]{};
        
    }

    public static int[] randomAttack(Board board){
        // Attacks randomly
        int[][] legalMoves = Utils.deepCopy(board.getLegalMoves());
        Random rand = new Random();
        return legalMoves[rand.nextInt(legalMoves.length)].clone();
    }

    public static int[] impossibleAttack(Board board){
        // Takes positions of enemy ships and attakcs
        int[][] legalMoves = board.getAllShipSquares();
        int[][] hits = board.getAllShipHits();
        for (int[] query : legalMoves){
            if (Utils.contains(hits, query)){
                continue;
            }
            return query.clone();
        }
        // If nothing can be found then use random attack. This should never trigger but its here in case it does.
        return randomAttack(board);   
    }



    public static int[] normalAttack(Board board){
        // Smart attack using a real strategy
        int[][] legalMoves = board.getLegalMoves();
        int[][] hitsArr = Utils.exclude(board.getAllShipHits(), board.getSunkenCoordinates());

        
        IdvRating[] ratings = new IdvRating[legalMoves.length];
        for (int index = 0;index<ratings.length;index++){
            ratings[index] = new IdvRating(new Point(legalMoves[index][1], legalMoves[index][0]),0);
        }

        // Get ratings for each point
        int[] currentCoord = new int[2];
        int currentScore = 0;

        for (IdvRating rating: ratings){
            // Regular attack prediction
            int[] coordinate = {rating.coordinate.y,rating.coordinate.x};
            for (int length: board.getAliveShipLengths()){

                // check for each direction
                for (Point direction : Board.DIRECTIONS ){      // for each direction check whether a ship could fit at this coordinate
                    boolean tr = true;
                    for (int index = 0;index<length;index++){
                        if (!Utils.contains(legalMoves, new int[]{coordinate[0]+(direction.y*index),coordinate[1]+(direction.x * index)})){
                            tr = false;
                            break;
                        }
                    }
                    if (tr){
                        switch (length){
                            // weights
                            case 5:
                                rating.score += 4;
                            case 4:
                                rating.score += 5;
                            case 3:
                                rating.score += 6;
                            case 2:
                                rating.score += 7;
                        }
                    }
                }  
            }
            // Now check for hits
            for (Point direction : Board.DIRECTIONS ){
                int index = 1;
                int accul = 0;
                while (true){
                    if (!Utils.contains(hitsArr, new int[]{coordinate[0]+(direction.y*index),coordinate[1]+(direction.x * index)})){
                        rating.score += accul;
                        break;
                    }
                    // It has to be a hit if the other two don't trigger
                    accul += 1000;
                    index++;
                }
            }
            if (rating.score > currentScore){
                currentScore = rating.score;
                currentCoord = coordinate.clone();
            }
        }


        return currentCoord;
    }
}

class IdvRating {
    Point coordinate;
    int score;
    public IdvRating(Point coordinate, int score){
        this.coordinate = coordinate;
        this.score = score;
    }

}