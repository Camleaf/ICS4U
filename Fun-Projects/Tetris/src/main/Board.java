package src.main;
import src.window.panels.PlayWindow;
import src.window.Colours;
import java.awt.Color;
import src.main.board.Piece;

public class Board extends PlayWindow{
    private Piece currentPiece;

    public Board(){
        super(400, 800);
        currentPiece = new Piece();
        fillBackground();
        drawSquare(4, 4, Color.RED);
    }
}
