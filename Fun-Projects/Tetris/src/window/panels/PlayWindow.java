package src.window.panels;
import lib.window.Texture;
import lib.window.base.BaseComponent;
import src.main.board.Piece;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PlayWindow extends BaseComponent {
    public int gridSize; //preferably a multiple of 8
    public final int columns = 10;
    public final int rows = 20;
    private final int squareSize = 40;
    private Texture texture = new Texture("public/Square4.png", squareSize, squareSize);
    private BufferedImage background;
    public int width;
    public int height;

    public PlayWindow(int width, int height){
        super(width, height);
        gct.fill(Color.black);
        createBackgroundTexture();
    }


    private void createBackgroundTexture(){
        background = new BufferedImage(squareSize*columns, squareSize*rows, BufferedImage.TYPE_INT_ARGB);
        // todo init with for loop
        for (int row  = 0; row<rows;row++){
            for (int col = 0; col < columns; col++){
                Graphics2D g = background.createGraphics();
                g.drawImage(texture.getBufferedImage(), col*squareSize, row*squareSize, null);
                g.dispose();

            }
        }
    }

    protected void fillBackground(){
        gct.drawBufferedImage(background, 0, 0);
    }

    protected void drawEmpty(int sqX, int sqY){
        gct.drawBufferedImage(texture.getBufferedImage(), sqX*squareSize, sqY*squareSize);
    }

    protected void drawSquare(int sqX, int sqY, Color c){
        gct.drawRect(sqX*squareSize,sqY*squareSize,squareSize,squareSize,c);
    }


    protected boolean inBounds(int sqX, int sqY){
        return 0 <= sqX && sqX < columns && 0 <= sqY && 0 <= sqY;
    }

    protected void wipePiece(Piece p){
        Point ref = p.getReferencePoint();
        Point[] localPosArr = p.getLocalPos();
        for (Point locPt : localPosArr){
            drawEmpty(locPt.x+ref.x, locPt.y+ref.y);
        }
    }

    protected void displayPiece(Piece p){
        Point ref = p.getReferencePoint();
        Point[] localPosArr = p.getLocalPos();
        for (Point locPt : localPosArr){
            drawSquare(locPt.x+ref.x, locPt.y+ref.y,p.getType().reg);
        }

    }


}
