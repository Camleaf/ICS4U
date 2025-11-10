package main.game.board;
import java.awt.Point;
import java.util.HashMap;
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


    public int getMovesLength(Piece.Colour colour){
        int values = 0;
        switch (colour){
            case WHITE:
                values = 0;
                for (Point[] pList : whiteLegalMoves.values()){
                    values += pList.length;
                }
                return values;

            case BLACK:
                values = 0;
                for (Point[] pList : blackLegalMoves.values()){
                    values += pList.length;
                }
                return values;

            default:
                return 0;
        }
    }

    
}
