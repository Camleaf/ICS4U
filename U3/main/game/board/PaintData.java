package main.game.board;
import java.awt.Point;
import java.util.ArrayList;
import static main.window.panels.BoardPanel.*;

/**
 * A class to transmit data for nonstandard paint points from the board to the board drawer
 */
public class PaintData {

    private ArrayList<Point> paintEmpty = new ArrayList<Point>();
    private ArrayList<Piece> paintDefault = new ArrayList<Piece>();
    private ArrayList<Piece> paintHighlight = new ArrayList<Piece>();
    private ArrayList<Piece> paintHighlightSelect = new ArrayList<Piece>();
    private ArrayList<Piece> paintOverwrite = new ArrayList<Piece>();

    public void add(Piece pc, int method){
        switch (method){
            case PIECE_PAINT_DEFAULT:
                paintDefault.add(
                    pc
                );
                break;
            case PIECE_PAINT_HIGHLIGHT:
                paintHighlight.add(
                    pc
                );
                break;
            case PIECE_PAINT_HIGHLIGHT_SELECT:
                paintHighlightSelect.add(
                    pc
                );
                break;
            case PIECE_PAINT_OVERWRITE:
                paintOverwrite.add(
                    pc
                );
                break;
            
            
        }
    }

    public void add(Piece pc){
        add(pc, PIECE_PAINT_DEFAULT);
    }



    public void addEmpty(Point p){
        paintEmpty.add(p);
    }

    public void addEmpty(int x, int y){
        paintEmpty.add(new Point(x,y));
    }

    public ArrayList<Piece> get(int method){
        switch (method){
            case PIECE_PAINT_DEFAULT:
                return paintDefault;
            case PIECE_PAINT_HIGHLIGHT:
                return paintHighlight;
            case PIECE_PAINT_HIGHLIGHT_SELECT:
                return paintHighlightSelect;
            case PIECE_PAINT_OVERWRITE:
                return paintOverwrite;
            default:
                return new ArrayList<Piece>();
        }
    }

    public ArrayList<Point> getEmpty(){
        return paintEmpty;
    }

}
