package main.game;
import main.game.board.Highlight;
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
    private Piece.Colour turn = WHITE;
    private Highlight prevMoveHighlight = new Highlight();

    public Board(int gridSize){
        super(gridSize);
        board = generateDefaultBoard();
        paintBackground();
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
        board[6] = generatePieceLine(new Piece.Type[]{PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN}, 6, WHITE);
        board[7] = generatePieceLine(new Piece.Type[]{ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK}, 7, WHITE);
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

            Point clickedSquare = new Point(
                (int) Math.round( Math.floor( ( (double)clickPos.x ) / squareSize )), 
                (int) Math.round( Math.floor( ( (double)clickPos.y ) / squareSize ))
            ); // squareSize was intialized in the super() call to BoardPanel
            
            
            Piece interactedPiece = board[clickedSquare.y][clickedSquare.x];

            if (this.selectedPoint.x == -1 || this.selectedPoint.y == -1){ // If the previously clicked square isn't defined
                if (interactedPiece.getType().equals(EMPTY) || !interactedPiece.getColour().equals(turn)){return;} // If the clicked point is an empty we don't want to define that as a clicked piece
                selectedPoint.setLocation(interactedPiece.x,interactedPiece.y);
                paintPiece(interactedPiece,PIECE_PAINT_HIGHLIGHT_SELECT);
                // Will eventually add highlighting here
            } else { // if it is that means there is a piece there
                Piece prevPiece = board[selectedPoint.y][selectedPoint.x];
                boolean accepted = handleMove(prevPiece, interactedPiece);

                if (!accepted){
                    // If the move wasn't accepted that means that either the move wasn't valid (haven't implemented yet) or they tried to click on a friendly piece
                    // Both edge cases are handled inside the move function
                    return;
                }

                this.selectedPoint.setLocation(-1, -1);

            }


        } else { // If the click is out of bounds of the board we want to reset the selectedPoint
            if (selectedPoint.x > 0 && selectedPoint.y != 0) paintPiece(board[selectedPoint.y][selectedPoint.x],PIECE_PAINT_OVERWRITE);

            this.selectedPoint.setLocation(-1,-1);
        }
    }



    /**
     * Handles the act of moving a piece, which includes switching the coords, and painting over parts of the board
     * @param piece the piece being moved
     * @param destinationPiece the piece or empty which is the destination
     */
    private boolean handleMove(Piece piece, Piece destinationPiece){

        if (!isValidMove(piece, destinationPiece)){
            return false;
        }

        //Remove old movement highlighted squares

        for (Point point : prevMoveHighlight.getSquares()){
            if (point == null){continue;}
            paintPiece(board[point.y][point.x],PIECE_PAINT_OVERWRITE);
        }

        // Now just overwrite the piece it lands on and let garbage collector clean it up. Then add a space where the piece used to be referenced
        // Overwrite old squares on display and set them to highlight
        prevMoveHighlight.set(new Point(piece.x,piece.y), new Point(destinationPiece.x,destinationPiece.y));
        paintHighlight(destinationPiece.x,destinationPiece.y);
        paintHighlight(piece.x,piece.y);
        
        // Give desination location and coordinates to moving piece then overwrite it's original spot

        board[destinationPiece.y][destinationPiece.x] = piece;
        board[piece.y][piece.x] = new Piece(piece.x,piece.y,EMPTY,NONE);
        piece.setLocation(destinationPiece.x,destinationPiece.y);

        // Paint piece in new spot
        paintPiece(piece.getType(),piece.getColour(),piece.x,piece.y);

        // Modify the prevMoveHighlight
        

        turn = (turn.equals(WHITE)) ? BLACK : WHITE;

        return true;
    }




    /** Given a piece and destination, determines if the move is valid, and follows Chess's rules.
     * @param piece the piece being moved
     * @param destinationPiece the piece or empty which is the destination
     */
    private boolean isValidMove(Piece piece, Piece destinationPiece){
        
        if ( piece.getColour().equals( destinationPiece.getColour() )){ // If white attacks white, or black attacks black
            paintPiece(piece,PIECE_PAINT_OVERWRITE);
            this.selectedPoint.setLocation(destinationPiece.x,destinationPiece.y);
            paintPiece(destinationPiece,PIECE_PAINT_HIGHLIGHT_SELECT);
            return false;
        }

        // Add actual move checking, en passant checking, if king is in check checking. And turns. That too
        return true;
    }

}
