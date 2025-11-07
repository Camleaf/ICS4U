package main.game;
import main.game.board.Piece;
import static main.game.board.Piece.Type.*;
import static main.game.board.Piece.Colour.*;
import main.window.Colours;
import main.window.panels.BoardPanel;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Contains the data for the chess game, and all methods which can mutate that data
 * @author Camleaf
 */
public class Board extends BoardPanel {

    private Piece[][] board;
    private Point selectedPoint = new Point(-1,-1);

    public Board(int gridSize){
        super(gridSize);
        board = generateDefaultBoard();
        paintBackground();
        highlightSquare(5, 3, Colours.selectHighlight);
        highlightSquare(5, 2);
        drawCurrentBoard(board);
    }

    /**
     * Generates a default board
     * @return a Piece[][] containing a standard chess board with black on the top and white on the bottom.
     */
    private Piece[][] generateDefaultBoard(){

        Piece[][] board = new Piece[8][8];
        board[0] = generatePieceLine(new Piece.Type[]{ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK}, 0, BLACK);
        board[1] = generatePieceLine(new Piece.Type[]{PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN}, 1, BLACK);
        for (int idx = 2;idx<6;idx++){
            board[idx] = generatePieceLine(new Piece.Type[]{EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY}, idx, NONE);
        }
        board[6] = generatePieceLine(new Piece.Type[]{PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN}, 1, WHITE);
        board[7] = generatePieceLine(new Piece.Type[]{ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK}, 0, WHITE);
        return board;
    }

    /**
     * Internal method used to generate a line of pieces based on an input of piece Types
     * @param indexes a Piece.Type array that needs to be of length 8
     * @param row an integer with the row of the line being generated
     * @param colour a Piece.Colour being either WHITE, BLACK, or NONE
     * @return a Piece[] of length 8 containing all the pieces given in indexes
     */
    private Piece[] generatePieceLine(Piece.Type[] indexes, int row, Piece.Colour colour){
        if (indexes.length != 8){return new Piece[0];};
        Piece[] line = new Piece[8];
        for (int idx=0;idx<8;idx++){
            line[idx] = new Piece(idx, row, indexes[idx], colour);
        }
        return line;
    }

    /**
     * Handles a click at a given java.awt.Point on the board.
     * <p>
     * @return the acceptance status of the click
     */
    public void handleMouseClick(Point clickPos){
        Rectangle bounds = this.getBounds();
        if (bounds.contains(clickPos)){

            Point clickedSquare = new Point(clickPos.x/squareSize, clickPos.y/squareSize); // squareSize was intialized in the super() call to BoardPanel
            Piece interactedPiece = board[clickedSquare.y][clickedSquare.x];

            if (this.selectedPoint.x == -1 || this.selectedPoint.y == -1){
                selectedPoint.setLocation(interactedPiece.x,interactedPiece.y);
            } else {
                Piece prevPiece = board[selectedPoint.y][selectedPoint.x];
            }


        } else {
            this.selectedPoint.setLocation(-1,-1);
        }
    }
}
