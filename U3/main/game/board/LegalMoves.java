package main.game.board;
import java.awt.Point;
import java.util.HashMap;
import main.game.board.Piece;
import static main.game.board.Piece.Colour.*;
/**
 * Container for legalmove related stuff. It somehow got complicated enough to warrant this
 */
public class LegalMoves {
    private HashMap<Point,Point[]> whiteLegalMoves = new HashMap<>();
    private HashMap<Point,Point[]> blackLegalMoves = new HashMap<>();


    public void setLegalMoves(Piece.Colour colour, HashMap<Point,Point[]> moveSet){
        switch (colour){
            case WHITE:
                whiteLegalMoves = moveSet;
                break;
            case BLACK:
                blackLegalMoves = moveSet;
                break;
            default:
                break;
        }
    }

    public HashMap<Point,Point[]> getLegalMoves(Piece.Colour colour){
        switch (colour){
            case WHITE:
                return whiteLegalMoves;
            case BLACK:
                return blackLegalMoves;
            default:
                return new HashMap<>();
        }
    }

    public Point[] getOrDefault(Piece.Colour colour, Point query, Point[] defaultRet){

        switch (colour){
            case WHITE:
                return whiteLegalMoves.getOrDefault(query, defaultRet);
            case BLACK:
                return blackLegalMoves.getOrDefault(query, defaultRet);
            default:
                return defaultRet;
        }
    }

    public boolean contains(Piece.Colour colour, Point query){
        switch (colour){
            case WHITE:
                for (Point key : whiteLegalMoves.keySet()){
                    for (Point move : whiteLegalMoves.get(key)){
                        if (move.equals(query)) return true;
                    }
                }
                return false;

            case BLACK:
                for (Point key : blackLegalMoves.keySet()){
                    for (Point move : blackLegalMoves.get(key)){
                        if (move.equals(query)) return true;
                    }
                }
                return false;

            default:
                return false;
        }
    }
}
