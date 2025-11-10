package main.game.board;
import java.awt.Point;

/**
 * Stores and modifies the information for the highlighted squares
 * @author Camleaf
 */
public class StoredPosition {
    private Point[] positions = new Point[2];

    public void setEmpty(){
        positions = new Point[2];
    }

    public void set(Point pos1, Point pos2){
        positions[0] = new Point(pos1);
        positions[1] = new Point(pos2);
    }
    public Point[] getSquares(){
        return positions;
    }
}
