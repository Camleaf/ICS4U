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
import main.game.board.Attacker;

/**
 * Contains the data for the chess game, and all methods which can mutate that data
 * <p>
 * I hate this class so much it is way too long and not clean enough for my liking
 * @author Camleaf
 */
public class Board extends BoardPanel {

    private Piece[][] board;
    private Point selectedPoint = new Point(-1,-1);
    private Piece.Colour turn = WHITE;
    private Highlight prevMoveHighlight = new Highlight();
    private LegalMoves legalMoves = new LegalMoves();
    private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    // make a different legalmoves list for black and white and use that to determine

    public Board(int gridSize){
        super(gridSize);
        board = generateDefaultBoard();
        paintBackground();
        drawCurrentBoard(board);

        for (Piece[] row : board){
            for (Piece piece : row){
                if (piece.getColour()==WHITE){
                    whitePieces.add(piece);
                } else if (piece.getColour() == BLACK){
                    blackPieces.add(piece);
                }
            }
        }

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

        if (blackPieces.contains(destinationPiece)){blackPieces.remove(destinationPiece);}
        if (whitePieces.contains(destinationPiece)){whitePieces.remove(destinationPiece);}

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
     * Gets a Piece[] containing pieces alive of a specified type and colour
     * @param t The Piece.Type required
     * @param c The colour to filter by
     * @return a Piece[] containing all pieces alive of the specifications
     */
    private Piece[] getPieceType(Piece.Type t, Piece.Colour c){
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        switch (c){
            case BLACK:
                for (Piece piece : blackPieces){
                    if (piece.getType()==t){
                        pieces.add(piece);
                    }
                }
                break;
            case WHITE:
                for (Piece piece : whitePieces){
                    if (piece.getType()==t){
                        pieces.add(piece);
                    }
                }
                break;
        }
        return  pieces.toArray(new Piece[pieces.size()]);
    }
    /**
     * Calculates the legal moves of all pieces on the board and stores them on the board
     * This is a piece of crap function and i want it gone but i can't figure out for the life of me how to refactor it
     */
    private HashMap<Point,Point[]> calculateLegalMoves(Piece.Colour colour){
        // make it so this function has a return value of an arr and a input of the colour i want to calculate the moves for so that I can make like possible moves
        HashMap<Point,Point[]> legalMoves = new HashMap<>();
        Piece king = getPieceType(KING, colour)[0];

        Attacker[] kingAttackers = attackersToPoint(king.getLocation(), colour.getInverse());

        for (int row = 0;row<8;row++){
            for (int col = 0;col<8;col++){
                Piece piece = board[row][col];

                if (!piece.getColour().equals(colour)){
                    continue;
                }
                // Build pieces
                ArrayList<Point> validMoves = new ArrayList<Point>();

                if (kingAttackers.length > 1 && piece.getType()!=KING){ legalMoves.put(new Point(col,row), new Point[0]); continue;} // The idea here is that if there is a double check, nothing the other pieces can do can undo it, so only king can move

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
    
                            for (int i : new int[]{-1,1}){
                                if (col+i <0 || col+i > 7){ continue;}

                                if (getPieceFromBoard(col+i, row+colourAdjust).getType()!=EMPTY){

                                    if (checkPin(king,piece,new Point(col+i, row+colourAdjust))){continue;} // If this move could lead to a pin skip it

                                    validMoves.add(new Point(col+i,row+colourAdjust));
                                }


                                // en passant code here
                                if (getPieceFromBoard(col+i, row).getType()==PAWN){ 
                                    Point[] prevMoves = prevMoveHighlight.getSquares();
                                    // If the previous move by the other team was a pawn jump next to the current pawn, allow en passant
                                    if (prevMoves[0]==null || prevMoves[1]==null){continue;}
                                    
                                    if (prevMoves[1].x == col+i && prevMoves[1].y == row && Math.abs(prevMoves[1].y-prevMoves[0].y) == 2){

                                        if (checkPin(king,piece,new Point(col+i, row+colourAdjust))){continue;} // If this move could lead to a pin skip it

                                        validMoves.add(new Point(col+i,row+colourAdjust));
                                    }

                                }

                                // move forward code
                                if (getPieceFromBoard(col, row+colourAdjust).getType()==EMPTY){ // attacks

                                    if (checkPin(king,piece,new Point(col, row+colourAdjust))){continue;} // If this move could lead to a pin skip it

                                    validMoves.add(new Point(col,row+colourAdjust));
                                }
                            }
                        }

                        break;
                    case KNIGHT:
                        for (int[] pos : new int[][]{{2,1},{-2,1},{2,-1},{-2,-1},{1,2},{-1,2},{1,-2},{-1,-2}}){
                            if (!pieceInBounds(pos[0]+col,pos[1]+row)) continue; // If outside the board

                            if (checkPin(king,piece,new Point(col+pos[0], row+pos[1]))){continue;} // If this move could lead to a pin skip it
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

                                if (checkPin(king,piece,new Point(newX, newY))){break;} // If this move could lead to a pin skip it

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

                                if (checkPin(king,piece,new Point(newX, newY))){break;} // If this move could lead to a pin skip it

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

                                if (checkPin(king,piece,new Point(newX, newY))){break;} // If this move could lead to a pin skip it
                                
                                validMoves.add(new Point(newX, newY));

                                if (getPieceFromBoard(newX, newY).getType()!=EMPTY)break;

                                idx++;
                            }
                        }
                        break;
                    case KING: 
                        // The king logic is scattered around everywhere its a piece of crap i hate it so so so so much theres like 300 lines of code just for it
                        for (int[] pos : new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}}){
                            int newX = pos[0] + piece.x;
                            int newY = pos[1] + piece.y;

                            if (!pieceInBounds(newX, newY)) continue;

                            Piece testPc = getPieceFromBoard(newX, newY);

                            if (testPc.getColour()!=colour && attackersToPoint(new Point(newX,newY), colour.getInverse()).length == 0){
                                validMoves.add(new Point(newX,newY));
                            } else {
                                System.out.println(attackersToPoint(new Point(newX,newY), colour.getInverse()).length);
                            }
                        }

                        break;
                    default:
                        break;
                }
                // Filter for moving onto same colour

                

                ArrayList<Point> filteredValidMoves = new ArrayList<Point>();
                for (Point pt : validMoves){
                    // The idea is that if the
                    if (getPieceFromBoard(pt.x, pt.y).getColour().equals(colour)){continue;}

                    // The idea behind the king movements is that we can see if the given points defend the king or not. If not, we destroy them
                    if (piece.getType()!=KING && kingAttackers.length==1){ // We don't want the king to lose moves based on if it can defend itself that defeats the point
                        // If there is a kingattacker we only want moves which can fix it
                        if (!pt.equals(kingAttackers[0].pc.getLocation())){

                            if (kingAttackers[0].pc.getType()==KNIGHT)continue; // If its a knight you have to eat it
                            // normalize the vector
                            int xDif = pt.x - king.x;
                            int yDif = pt.y - king.y;
                            if (!(Math.abs(xDif) == Math.abs(yDif) || (xDif==0&&yDif!=0) || (xDif!=0&&yDif==0))) continue;

                            if (Math.abs(pt.x) > kingAttackers[0].magnitude || Math.abs(pt.y)>kingAttackers[0].magnitude) continue;

                        }
                    }
                    // End king move checking


                    filteredValidMoves.add(pt);
                }
                
                legalMoves.put(new Point(col,row), filteredValidMoves.toArray(new Point[filteredValidMoves.size()]));
                
            }
        }
        return legalMoves;


    }

    /**
     * Returns a boolean value which represents whether moving a given piece to a given point will cause a pin by enemies on a given king.
     * @param king the king for which the function checks for pins
     * @param pieceToMove the piece for which the moves may cause a pin
     * @param destPoint the point which the pieceToMove is moving
     */
    private boolean checkPin(Piece king, Piece pieceToMove, Point destPoint){
        // See if the pin is even worth checking in the first place

        int xDif = pieceToMove.x - king.x;
        int yDif = pieceToMove.y - king.y;

        // There is only a possibility of a pin if xdif==ydif, or xdif0=0 && ydif !=0, or xdif!=0 && ydif==0

        if (!(Math.abs(xDif) == Math.abs(yDif) || (xDif==0&&yDif!=0) || (xDif!=0&&yDif==0))) return false;

        // Based on the pin logic above, I can normalize the vectors like this and have it be accurate as we are in an edge case
        if (xDif > 0){
            xDif = 1;
        } else if (xDif < 0){
            xDif = -1;
        }

        if (yDif > 0){
            yDif = 1;
        } else if (yDif < 0){
            yDif = -1;
        }

        Point vector = new Point(xDif,yDif);

        int idx = 1;
        while (true){
            int newX = vector.x*idx + king.x;
            int newY = vector.y*idx + king.y;
            if (!pieceInBounds(newX, newY)) break; // If the ray reaches the edge of the board, there is no pin

            if (newX == destPoint.x && newY == destPoint.y){ 
                // If where the piece that is next to the king moves and the ray hits it first, it stayed on its line, therefore no pin can occur
                break;
            }

            if (newX == pieceToMove.x && newY == pieceToMove.y){
                idx++;
                continue;
            } // We want to skip the coordinate that the object is moving from

            if (getPieceFromBoard(newX, newY).getType()!=EMPTY ){

                if (getPieceFromBoard(newX, newY).getColour()==king.getColour()){ // this if statement is here for optimization. This project will need any it can get.
                    // If we run into a piece the same colour as the king, then no pin
                    break;
                }

                if (getPieceFromBoard(newX, newY).getType()==QUEEN){
                    return true;
                } else if (getPieceFromBoard(newX, newY).getType()==BISHOP && (Math.abs(vector.x) == 1 && Math.abs(vector.y)==1)){
                    return true;
                } else if (getPieceFromBoard(newX, newY).getType()==ROOK && ((Math.abs(vector.x) == 0 && Math.abs(vector.y)==1) || (Math.abs(vector.x) == 1 && Math.abs(vector.y)==0))){
                    return true;
                }
                break;
            };

            idx++;
        }

        return false;
    }




    /**
     * Returns an Attacker[] containing all the attackers to a specified point
     * @param p the point to check
     * @param c the colour of the attackers
     */
    private Attacker[] attackersToPoint(Point p, Piece.Colour c){

        ArrayList<Attacker> pieces = new ArrayList<Attacker>();

        for (int[] pos : new int[][]{{2,1},{-2,1},{2,-1},{-2,-1},{1,2},{-1,2},{1,-2},{-1,-2}}){ // check knight moves

            if (!pieceInBounds(p.x+pos[0], p.y+pos[1])) continue ;

            Piece pc = getPieceFromBoard(p.x+pos[0], p.y+pos[1]);
            if (pc.getType()==KNIGHT && pc.getColour()==c){
                pieces.add(new Attacker(pc, 1, new Point(pos[0],pos[1])));
            }
        }

        for (int[] pos : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}){ // check rook
            int idx = 1;
            while (true){
                int newX = pos[0]*idx + p.x;
                int newY = pos[1]*idx + p.y;
                if (!pieceInBounds(newX, newY)) break;

                Piece pc = getPieceFromBoard(newX, newY);

                if (pc.getColour()==c.getInverse()){break;}



                if (pc.getColour()==c){
                    if (!(pc.getType()==ROOK || pc.getType()==QUEEN)) break;
                    pieces.add(new Attacker(pc, idx, new Point(pos[0],pos[1])));
                    break;
                }

                idx++;
            }
        }
        for (int[] pos : new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}}){ // check bishop
            int idx = 1;
            while (true){
                int newX = pos[0]*idx + p.x;
                int newY = pos[1]*idx + p.y;
                if (!pieceInBounds(newX, newY)) break;

                Piece pc = getPieceFromBoard(newX, newY);

                if (pc.getColour()==c.getInverse()){break;}

                if (pc.getColour()==c){
                    if (!(pc.getType()==BISHOP || pc.getType()==QUEEN)) break;
                    pieces.add(new Attacker(pc, idx, new Point(pos[0],pos[1])));
                    break;
                }

                idx++;
            }
        }


        // check pawn

        int colourAdjust = (c.equals(BLACK)) ? -1:1; // adjusting for pawn movement in different directions due to colour

        for (int i : new int[]{-1,1}){
            if (!pieceInBounds(p.x+i, p.y + colourAdjust)) continue;
            
            Piece pc = getPieceFromBoard(p.x+i, p.y + colourAdjust);
            if (pc.getType() == PAWN && pc.getColour()==c){
                pieces.add(new Attacker(pc, 1, new Point(i,colourAdjust)));
            }
        }






        return  pieces.toArray(new Attacker[pieces.size()]);
    }
}
