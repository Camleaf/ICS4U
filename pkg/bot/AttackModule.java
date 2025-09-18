package pkg.bot;
import pkg.board.Board;
import pkg.utils.Utils;

import java.util.Arrays;
import java.util.Random;
import java.awt.Point;

public class AttackModule {
    public static int[] getAttackCoordinate(Board board, int difficulty){
        switch (difficulty){
            case 1:
                return randomAttack(board);
            case 2:

                break;
            case 3:
                return impossibleAttack(board);
        }
        return new int[]{};
        
    }

    public static int[] randomAttack(Board board){
        int[][] legalMoves = board.getLegalMoves();
        Random rand = new Random();
        return legalMoves[rand.nextInt(legalMoves.length)].clone();
    }

    public static int[] impossibleAttack(Board board){
        int[][] legalMoves = board.getAllShipSquares();
        int[][] hits = board.getAllShipHits();
        for (int[] query : legalMoves){
            if (Arrays.asList(hits).contains(query)){
                continue;
            }
            return query.clone();
        }
        // If nothing can be found then use random attack. This should never trigger but its here in case it does.
        return randomAttack(board);   
    }

    public static int[] normalAttack(Board board){
        int[][] legalMoves = board.getLegalMoves();
        int[][] hits = Utils.exclude(board.getAllShipHits(), board.getSunkenCoordinates());

        Rating[] ratings = new Rating[legalMoves.length];
        for (int index = 0;index<ratings.length;index++){
            ratings[index] = new Rating(new Point(legalMoves[index][0], legalMoves[index][1]),0);
        }

        for (Rating rating: ratings){
            for (int length: board.getAliveShipLengths()){
                                
            }
        }



        return new int[]{};
    }
}

class Rating {
    Point coordinate;
    int score;
    public Rating(Point coordinate, int score){
        this.coordinate = coordinate;
        this.score = score;
    }

}