package src.main;
import src.main.board.Piece;
import src.main.board.PieceType;
import src.window.panels.PlayWindow;
import java.awt.Color;

public class Queue extends PlayWindow {

    public Queue(){
        super(4*40, 5*3*40); // 40 being squaresize
    }

    public void updateQueue(PieceType[] types){
        gct.fill(Color.BLACK);
        for (int i = 0; i < types.length;i++){
            Piece pc = new Piece(types[i], 0, i*3);
            paintPiece(pc);
        }
    }
}
