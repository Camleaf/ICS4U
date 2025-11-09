package main.game;
import main.game.board.Highlight;
import main.game.board.LegalMoves;
import main.game.board.Piece;
import static main.game.board.Piece.Type.*;
import static main.game.board.Piece.Colour.*;
import main.window.panels.BoardPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import lib.logic.Utils;
import java.util.ArrayList;

/**
 * Contains the data for the chess game, and all methods which can mutate that data
 * @author Camleaf
 */
public class Board extends BoardPanel {

    private Piece[][] board;
    private Point selectedPoint = new Point(-1,-1);
    private Piece.Colour turn = WHITE;
    private Highlight prevMoveHighlight = new Highlight();
    private LegalMoves legalMoves = new LegalMoves();
    // make a different legalmoves list for black and white and use that to determine

    public Board(int gridSize){
        super(gridSize);
        board = generateDefaultBoard();
        paintBackground();
        drawCurrentBoard(board);
        legalMoves.setLegalMoves(WHITE, calculateLegalMoves(WHITE));
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
     * Given a coordinate, x and y, determines if it is in the bounds of the board arr
     * @param x the x index to see if it is in the board arr
     * @param y the y index to see if it is in the board arr
     */
    private boolean pieceInBounds(int x, int y){
        return 0 <= x && x < 8 && 0 <= y && y < 8;
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

        // Check for en passant and adjust board accordingly
        Point[] prevMoves = prevMoveHighlight.getSquares();
        if (!(prevMoves[0]==null || prevMoves[1]==null) && piece.getType()==PAWN && destinationPiece.getType()==EMPTY){

            if (Math.abs(prevMoves[0].y-destinationPiece.getLocation().y) == 1 && Math.abs(prevMoves[1].y-destinationPiece.getLocation().y) == 1){
                // this code is a mess it works though. Better just keep an eye to make sure it doesn't obliterate anything else
                int adjust = (prevMoves[1].y-destinationPiece.getLocation().y > 0) ? 1:-1;
                if (board[destinationPiece.y+adjust][destinationPiece.x].getType() == PAWN){
                    board[destinationPiece.y+adjust][destinationPiece.x] = new Piece(destinationPiece.x,destinationPiece.y+adjust,EMPTY,NONE);
                    paintEmpty(destinationPiece.x,destinationPiece.y+adjust);
                }
            }
        }
        //end en passant check

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
        piece.setMoved(true);
        // Modify the prevMoveHighlight

        
        // Screw it i'm going the extremely inefficient route. I'm so happy that chess is a game that can afford massive overheads on functions
        // The idea for updating both the black and white legalmoves is that once one's turn ends, i can use (an albiet flawed) recalculation to at minimum see if there are any new threats to the king
        legalMoves.setLegalMoves(turn, calculateLegalMoves(turn));

        turn = (turn.equals(WHITE)) ? BLACK : WHITE;

        legalMoves.setLegalMoves(turn, calculateLegalMoves(turn));
        // Recalculate legal moves list AFTER turn has been changed

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

        // checks against the current legalmoves list if the move is possible
        if (!Utils.contains( legalMoves.getOrDefault(turn, piece.getLocation(), new Point[0] ),destinationPiece.getLocation())){
            return false;
        }
    
        return true;
    }

    private Piece getPieceFromBoard(int x, int y){
        return board[y][x];
    }

    /**
     * Calculates the legal moves of all pieces on the board and stores them on the board
     * This is a piece of crap function and i want it gone but i can't figure out for the life of me how to refactor it
     */
    private HashMap<Point,Point[]> calculateLegalMoves(Piece.Colour colour){
        // make it so this function has a return value of an arr and a input of the colour i want to calculate the moves for so that I can make like possible moves
        HashMap<Point,Point[]> legalMoves = new HashMap<>();

        for (int row = 0;row<8;row++){
            for (int col = 0;col<8;col++){
                Piece piece = board[row][col];

                if (!piece.getColour().equals(colour)){
                    continue;
                }
                // Build pieces
                ArrayList<Point> validMoves = new ArrayList<Point>();

                switch (piece.getType()){
                    case PAWN: // Note: will need to add en passant checking to here but I don't have the infrastructure to do that yet
                        int colourAdjust = (colour.equals(WHITE)) ? -1:1;

                        if (!piece.hasMoved()&&getPieceFromBoard(col, row+colourAdjust).getType().equals(EMPTY)&&getPieceFromBoard(col, row+(2*colourAdjust)).getType().equals(EMPTY)){ // Add double move forward
                            validMoves.add(
                                new Point(col,row + (2*colourAdjust))
                            );
                        }
                        // document this later
                        if (row+colourAdjust >= 0 && row+colourAdjust <8){
                            if (getPieceFromBoard(col, row+colourAdjust).getType()==EMPTY){
                                validMoves.add(new Point(col,row+colourAdjust));
                            }
                            for (int i : new int[]{-1,1}){
                                if (col+i <0 || col+i > 7){ continue;}

                                if (getPieceFromBoard(col+i, row+colourAdjust).getType()!=EMPTY){
                                    validMoves.add(new Point(col+i,row+colourAdjust));
                                }

                                if (getPieceFromBoard(col+i, row).getType()==PAWN){
                                    Point[] prevMoves = prevMoveHighlight.getSquares();
                                    if (prevMoves[0]==null || prevMoves[1]==null){continue;}
                                    
                                    if (prevMoves[1].x == col+i && prevMoves[1].y == row && Math.abs(prevMoves[1].y-prevMoves[0].y) == 2){
                                        validMoves.add(new Point(col+i,row+colourAdjust));
                                    }

                                }
                                // dont put en passant here put it as a different check in the valid move function
                                // Actually no put en passant here as a move option and *also* add a check on the handlemove function to remove the other piece if it detects en passant
                            }
                        }

                        break;
                    case KNIGHT:
                        for (int[] pos : new int[][]{{2,1},{-2,1},{2,-1},{-2,-1},{1,2},{-1,2},{1,-2},{-1,-2}}){
                            if (!pieceInBounds(pos[0]+col,pos[1]+row)) continue; // If outside the board

                            validMoves.add(new Point(col+pos[0],row+pos[1]));


                        }
                        break;
                    case BISHOP:
                    // The idea here is that we have a movement vector x,y for every single direction it could go in. For each vector, we multiply x,y by the amount of iterations, and check if the current square is valid or not.
                        for (int[] pos : new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}}){
                            int idx = 1;
                            while (true){
                                int newX = pos[0]*idx + col;
                                int newY = pos[1]*idx + row;
                                if (!pieceInBounds(newX, newY)) break;

                                validMoves.add(new Point(newX, newY));

                                if (getPieceFromBoard(newX, newY).getType()!=EMPTY)break;

                                idx++;
                            }
                        }
                        break;
                    case ROOK: //same as bishop but with different vectors
                        for (int[] pos : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}){
                            int idx = 1;
                            while (true){
                                int newX = pos[0]*idx + col;
                                int newY = pos[1]*idx + row;
                                if (!pieceInBounds(newX, newY)) break;

                                validMoves.add(new Point(newX, newY));

                                if (getPieceFromBoard(newX, newY).getType()!=EMPTY)break;

                                idx++;
                            }
                        }
                        break;

                    case QUEEN: //basically just the same as the rook and bishop combined
                        for (int[] pos : new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}}){
                            int idx = 1;
                            while (true){
                                int newX = pos[0]*idx + col;
                                int newY = pos[1]*idx + row;
                                if (!pieceInBounds(newX, newY)) break;

                                validMoves.add(new Point(newX, newY));

                                if (getPieceFromBoard(newX, newY).getType()!=EMPTY)break;

                                idx++;
                            }
                        }
                        break;
                    case KING: 
                    // use the fact that i can grab the other side legal moves from the end of last turn
                        break;
                    default:
                        break;
                }
                // Filter for moving onto same colour

                ArrayList<Point> filteredValidMoves = new ArrayList<Point>();
                for (Point pt : validMoves){
                    // The idea is that if the
                    if (getPieceFromBoard(pt.x, pt.y).getColour().equals(colour)){continue;}
                    filteredValidMoves.add(pt);
                }
                legalMoves.put(new Point(col,row), filteredValidMoves.toArray(new Point[filteredValidMoves.size()]));
                
            }
        }
        return legalMoves;


    }

}
