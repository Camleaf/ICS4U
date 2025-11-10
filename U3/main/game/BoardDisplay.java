package main.game;
import main.game.board.Piece;
import main.game.board.PromoteDisplay;
import main.window.panels.BoardPanel;
import static main.game.board.Piece.Type.*;
import static main.game.board.Piece.Colour.*;
import main.game.Board;
import java.awt.Rectangle;
import java.awt.Point;
import main.game.board.PaintData;
import java.util.ArrayList;


public class BoardDisplay extends BoardPanel {
    private Point selectedPoint = new Point(-1,-1);
    private Board board;
    public boolean checkMate = false;
    public PromoteDisplay pawnPromoteDisplay = new PromoteDisplay();


    public BoardDisplay(int gridSize){
        super(gridSize,WHITE); // the colour passed is the orientation of the board, black or white
        board = new Board();
        paintBackground();
        drawCurrentBoard(board.getRawBoard());
    }

    /**
     * Handles a click at a given java.awt.Point on the board.
     * <p>
     * @return the acceptance status of the click
     */
    public void handleMouseClick(Point clickPos){

        // the pawn promotion menu must take priority
        if (pawnPromoteDisplay.isVisible()){
            handlePromoteDisplay(clickPos);
            return;
        }

        if (this.checkMate){
            return;
        }

        if (this.orientation == BLACK){
            clickPos = new Point(clickPos.x,this.gridSize-clickPos.y);
        }

        Rectangle bounds = this.getBounds();
        if (bounds.contains(clickPos)){

            Point clickedSquare = new Point(
                (int) Math.round( Math.floor( ( (double)clickPos.x ) / squareSize )), 
                (int) Math.round( Math.floor( ( (double)clickPos.y ) / squareSize ))
            ); // squareSize was intialized in the super() call to BoardPanel
            
            
            Piece interactedPiece = board.getPieceFromBoard(clickedSquare.x, clickedSquare.y);

            if (this.selectedPoint.x == -1 || this.selectedPoint.y == -1){ // If the previously clicked square isn't defined
                if (interactedPiece.getType().equals(EMPTY) || !interactedPiece.getColour().equals(board.getTurn())){return;} // If the clicked point is an empty we don't want to define that as a clicked piece
                
                selectedPoint.setLocation(interactedPiece.x,interactedPiece.y);
                paintPiece(interactedPiece,PIECE_PAINT_HIGHLIGHT_SELECT);


                // Will eventually add highlighting here
            } else { // if it is that means there is a piece there


                Piece prevPiece = board.getPieceFromBoard(selectedPoint.x, selectedPoint.y);

                if ( prevPiece.getColour().equals( interactedPiece.getColour() )){ // If white attacks white, or black attacks black, change the highlight
                    paintPiece(prevPiece,PIECE_PAINT_OVERWRITE);
                    this.selectedPoint.setLocation(interactedPiece.getLocation());
                    paintPiece(interactedPiece,PIECE_PAINT_HIGHLIGHT_SELECT);
                    return;
                }

                for (Point point : board.getStoredMoves().getSquares()){
                    if (point == null){continue;}
                    paintPiece(board.getRawBoard()[point.y][point.x], PIECE_PAINT_OVERWRITE);
                }

                PaintData paintData = board.handleMove(prevPiece, interactedPiece);

                if (paintData == null){
                    // If the move wasn't accepted that means that either the move wasn't valid 
                    // Both edge cases are handled inside the move function
                    return;
                }
        
                parsePaintData(paintData);

                // Since paintdata isn't null we can check for checkmates and for pawn promotions

                checkCheckMate();// Board.getturn at this point is now the opposite of whatever piece we just moved.

                // if a pawn is on the last or first row it must be promoting
                if (prevPiece.getType() == PAWN && (prevPiece.y==7 || prevPiece.y==0)){
                    pawnPromoteDisplay.setMode(board.getTurn().getInverse());
                    pawnPromoteDisplay.setPawnLoc(prevPiece.x, prevPiece.y);
                    pawnPromoteDisplay.setVisible(true);
                }

                this.selectedPoint.setLocation(-1, -1);

            }


        } else { // If the click is out of bounds of the board we want to reset the selectedPoint
            if (selectedPoint.x > 0 && selectedPoint.y != 0) paintPiece(board.getPieceFromBoard(selectedPoint.x, selectedPoint.y),PIECE_PAINT_OVERWRITE);

            this.selectedPoint.setLocation(-1,-1);
        }
    }



    public void parsePaintData(PaintData paintData){

        // do empties first because idk i may break something otherwise

        for (Point p : paintData.getEmpty()){
            paintEmpty(p.x,p.y);
        }

        // most stuff except empties
        for (int method : new int[]{PIECE_PAINT_DEFAULT,PIECE_PAINT_HIGHLIGHT,PIECE_PAINT_HIGHLIGHT_SELECT,PIECE_PAINT_OVERWRITE}){
            ArrayList<Piece> instructionsList = paintData.get(method);
            for (Piece pc : instructionsList){
                paintPiece(pc,method);
            }

        }


    }   


    private void handlePromoteDisplay(Point clickPos){
        Piece.Type pcT = pawnPromoteDisplay.handleClick(clickPos);
        if (pcT == null) return;

        Piece pawn = board.getRawBoard()[pawnPromoteDisplay.pawnLoc.y][pawnPromoteDisplay.pawnLoc.x];
        pawn.setType(pcT);
        paintPiece(pawn,PIECE_PAINT_OVERWRITE);
        
        board.restoreLegalMoves();
        pawnPromoteDisplay.setVisible(false);
        checkCheckMate();
    }


    private void checkCheckMate(){
         if (board.isCheckMate(board.getTurn())){
            Piece king = board.getPieceType(KING, board.getTurn())[0];
            this.checkMate = true;
            paintPiece(king,PIECE_PAINT_HIGHLIGHT_CHECKMATE);
        };
    }
}
