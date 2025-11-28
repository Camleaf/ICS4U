package src.main;
import src.main.board.Piece;
import src.main.board.PieceType;
import src.window.panels.PlayWindow;
import java.awt.Color;

public class HoldArea extends PlayWindow {
    private Piece heldPiece;

    public HoldArea(){
        super(4*40, 4*40); // 40 being squaresize
    }

    public void changePiece(PieceType type){
        if (heldPiece!=null){
            paintPiece(heldPiece, Color.BLACK);
        }
        if (type != null){
            heldPiece = new Piece(type,0,0);
            paintPiece(heldPiece);
        } else {
            heldPiece = null;
        }
    }
}
