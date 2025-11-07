package main.window.panels;
import lib.window.GraphicsPanel;
import lib.window.Texture;
import main.window.Colours;
import main.game.board.Piece;
import java.awt.Color;
/**
 * Class inheriting from GraphicsPanel and in turn JPanel. Meant to use the GraphicsPanel abstractions to provide all game-specific chessBoard functions. Doesn't contain critical game logic, just game-specific rendering
 * @author Camleaf
 */
public class BoardPanel extends GraphicsPanel {
    private final Texture spriteSheet = new Texture("src/piecesSpriteSheet.png", -1, -1);
    private final int spriteSize = 64;
    private final int spriteSheetSize = 256;
    private int gridSize; //preferably a multiple of 8
    private int squareSize;
    
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
    public void highlightSquare(int x, int y){
        drawRect(x * squareSize, y*squareSize,squareSize,squareSize,Colours.boardHighlight);
    }

    /**
     * Highlights a square. Will overwrite any piece currently rendered onto the square
     * @param x the x-value of the coordinate to highlight
     * @param y the y-value of the coordinate to highlight
     */
    public void highlightSquare(int x, int y, Color colour){
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


    public void paintEmpty(int x, int y){
        if ((y%2 + x)%2==0){
            drawRect(x*squareSize,y*squareSize,squareSize,squareSize,Colours.boardWhite);
        } else {
            drawRect(x*squareSize,y*squareSize,squareSize,squareSize,Colours.boardBlack);
        }
    }

}
