package main.game.board;
import main.window.Colours;
import main.window.panels.BoardPanel;
import static main.game.board.Piece.Type.*;
import static main.game.board.Piece.Colour.*;
import java.awt.Rectangle;
import java.awt.Point;

/**
 * The display where the user can choose which piece a pawn promotes to
 */
public class PromoteDisplay extends BoardPanel {

    public boolean isShown;
    private static final int width = 4*64;
    private static final int height = 64;
    public Point pawnLoc;
    
    public PromoteDisplay(){
        super(64, width, height, WHITE);
        setMode(WHITE);
        repaint();
        setBounds(32, 32, width, height); // So that it's not visible when i don't need it
        setVisible(false);
    }

    public void setMode(Piece.Colour c){
        fillBoard(Colours.selectHighlight);
        paintPiece(KNIGHT,c,0,0);
        paintPiece(BISHOP,c,1,0);
        paintPiece(ROOK,c,2,0);
        paintPiece(QUEEN,c,3,0);
    }

    public Piece.Type handleClick(Point clickPos){

        Rectangle bounds = this.getBounds();

        if (!bounds.contains(clickPos)) return null; // null in this case means that the click didn't collide

        int x = Math.floorDiv(clickPos.x - bounds.x,64);
        
        switch (x){
            case 0:
                return KNIGHT;
            case 1:
                return BISHOP;
            case 2:
                return ROOK;
            case 3:
                return QUEEN;
            default:
                return EMPTY; // Just to compile; this case will never be reached
        }

    }   

    public void setPawnLoc(int x, int y, Piece.Colour orientation){
        int displayY = (orientation == WHITE) ? y : 7-y;
        // it by default is int the first spot i want
        if (x > 3 && displayY > 3){
            setLocation(512-width-32+xOffset,512-height-32);
        } else if (x > 3 && displayY <= 3){
            setLocation(512-width-32+xOffset,32);
        } else if (x <= 3 && displayY > 3){
            setLocation(32+xOffset,512-height-32);
        }

        pawnLoc = new Point(x,y);
    }

    



}
