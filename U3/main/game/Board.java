package main.game;
import main.game.board.Piece;
import main.window.Colours;
import main.window.panels.BoardPanel;

/**
 * Contains the data for the chess game, and all methods which can mutate that data
 * @author Camleaf
 */
public class Board extends BoardPanel {

    public Board(int gridSize){
        super(gridSize);
        paintBackground();
        highlightSquare(5, 3, Colours.selectHighlight);
        paintPiece(Piece.Type.QUEEN,Piece.Colour.BLACK,5,3);
        highlightSquare(5, 2);
        paintPiece(Piece.Type.KING,Piece.Colour.WHITE,5,2);
    }
}
