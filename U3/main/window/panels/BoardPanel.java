package main.window.panels;
import lib.window.GraphicsPanel;
import lib.window.Texture;
import main.window.Colours;
import main.game.board.Piece;
import java.awt.Color;
import java.awt.Point;
/**
 * Class inheriting from GraphicsPanel and in turn JPanel. Meant to use the GraphicsPanel abstractions to provide all game-specific chessBoard functions. Doesn't contain critical game logic, just game-specific rendering
 * @author Camleaf
 */
public class BoardPanel extends GraphicsPanel {
    private final Texture spriteSheet = new Texture("src/piecesSpriteSheet.png", -1, -1);
    private final int spriteSize = 64;
    private final int spriteSheetSize = 256;
    private int gridSize; //preferably a multiple of 8
    public int squareSize;
    
    /**
     * @author Camleaf
     */
    public BoardPanel(int gridSize){
        super(gridSize, gridSize);

        this.gridSize = gridSize;
        this.squareSize = this.gridSize/8;

        setBounds(0, 0, gridSize, gridSize);
    } 
    
    /**
     * Paints the background of the grid in the classic checkerboard fashion. Will overwrite everything rendered to the board
     */
    public void paintBackground(){
        flushBuffer();
        for (int row = 0; row < 8; row++){
            for (int col = 0;col<8;col++){
                if ((row%2 + col)%2==0){
                    drawRect(col*squareSize,row*squareSize,squareSize,squareSize,Colours.boardWhite);
                } else {
                    drawRect(col*squareSize,row*squareSize,squareSize,squareSize,Colours.boardBlack);
                }
            }
        }
    }

    /**
     * Highlights a square. Will overwrite any piece currently rendered onto the square
     * @param x the x-value of the coordinate to highlight
     * @param y the y-value of the coordinate to highlight
     */
    public void paintHighlight(int x, int y){
        drawRect(x * squareSize, y*squareSize,squareSize,squareSize,Colours.boardHighlight);
    }

    /**
     * Highlights a square. Will overwrite any piece currently rendered onto the square
     * @param x the x-value of the coordinate to highlight
     * @param y the y-value of the coordinate to highlight
     */
    public void paintHighlight(int x, int y, Color colour){
        drawRect(x * squareSize, y*squareSize,squareSize,squareSize,colour);
    }

    /**
     * Paints a piece onto the board
     * @param piece a class representing a piece from the enum Pieces from class board 
     * @param colour a class representing one of two colours, black and white, from the enum Colour from class board 
     * @param x the x-value of the coordinate of which to render the piece
     * @param y the y-value of the coordinate of which to render the piece
     */
    public void paintPiece(Piece.Type piece, Piece.Colour colour, int x, int y){
        if (piece.equals(Piece.Type.EMPTY)){return;}

        int pieceX = (colour.id == 0) ? 0 : 2*spriteSize;
        int pieceY = piece.id * spriteSize;
        if (pieceY >= spriteSheetSize){
            pieceY %= spriteSheetSize;
            pieceX += spriteSize;
        }
        drawBufferedImage(spriteSheet.getSlice(pieceX,pieceY,spriteSize,spriteSize), x*squareSize, y*squareSize);
    }

    // Variables for the variable paintPiece
    public static final int PIECE_PAINT_DEFAULT = 0;
    public static final int PIECE_PAINT_OVERWRITE = 1;
    public static final int PIECE_PAINT_HIGHLIGHT = 2;
    public static final int PIECE_PAINT_HIGHLIGHT_SELECT = 3;
    /**
     * Paints a piece onto the board with the option to use a specific drawing mode
     * @param piece a class representing a piece from the enum Pieces from class board 
     * @param colour a class representing one of two colours, black and white, from the enum Colour from class board 
     * @param x the x-value of the coordinate of which to render the piece
     * @param y the y-value of the coordinate of which to render the piece
     * @param mode the mode of the piece paint.
     * <ul>
     * <li><b>0</b> default paint</li>
     * <li><b>1</b> overwrites background with regular board colour</li>
     * <li><b>2</b> overwrites background with highlight color of last move</li>
     * <li><b>3</b> overwrites background with highlight color of current selection</li>
     */
    public void paintPiece(Piece.Type piece, Piece.Colour colour, int x, int y, int mode){
        switch (mode){
            case (PIECE_PAINT_OVERWRITE):
                paintEmpty(x, y);
                break;
            case (PIECE_PAINT_HIGHLIGHT):
                paintHighlight(x,y);
                break;
            case (PIECE_PAINT_HIGHLIGHT_SELECT):
                paintHighlight(x,y,Colours.selectHighlight);
                break;
        }   

        paintPiece(piece, colour, x, y);
    }
    /**
     * Paints a piece onto the board based on the piece's internal coordinates, type, and colour
     * @param piece a class representing a piece from the enum Pieces from class board 
    */
    public void paintPiece(Piece piece){
        paintPiece(piece.getType(),piece.getColour(),piece.x,piece.y);
    }


    /**
     * According to a specific mode, Paints a piece onto the board based on the piece's internal coordinates, type, and colour.
     * @param piece a class representing a piece from the enum Pieces from class board 
     * @param mode the mode of the piece paint.
     * <ul>
     * <li><b>0</b> default paint</li>
     * <li><b>1</b> overwrites background with regular board colour</li>
     * <li><b>2</b> overwrites background with highlight color of last move</li>
     * <li><b>3</b> overwrites background with highlight color of current selection</li>
    */
    public void paintPiece(Piece piece, int mode){
        paintPiece(piece.getType(),piece.getColour(),piece.x,piece.y,mode);
    }
    

    /**
     * Paints an empty square at a given coordinate (x,y) overwriting what was there previously
     * @param x the x-value of the coordinate of which to render the square
     * @param y the y-value of the coordinate of which to render the square
     */
    public void paintEmpty(int x, int y){
        if ((y%2 + x)%2==0){
            drawRect(x*squareSize,y*squareSize,squareSize,squareSize,Colours.boardWhite);
        } else {
            drawRect(x*squareSize,y*squareSize,squareSize,squareSize,Colours.boardBlack);
        }
    }

    /**
     * Paints an empty square at a given coordinate (x,y) overwriting what was there previously
     * @param p a Java.awt.Point object representing coordinate (x,y)
     */
    public void paintEmpty(Point p){
        paintEmpty(p.x, p.y);
    }

    /**
     * Draws the current stored board. Will not overwrite the background. 
     * <p>
     * Is inefficient and should only be used after a buffer clear and when loading a completely different board state. Smaller changes should use the  inherited paintPiece and paintEmpty functions directly
     */
    public void drawCurrentBoard(Piece[][] board){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                paintPiece(board[row][col].getType(), board[row][col].getColour(),col,row);
            }
        }
    }


}
