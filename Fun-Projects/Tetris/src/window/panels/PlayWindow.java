package src.window.panels;
import lib.window.Texture;
import lib.window.base.BaseComponent;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class PlayWindow extends BaseComponent {
    public int gridSize; //preferably a multiple of 8
    public final int columns = 10;
    public final int rows = 20;
    private final int squareSize = 40;
    private Texture texture = new Texture("public/Square.png", squareSize, squareSize);
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
    }

    protected void drawEmpty(int sqX, int sqY){
        gct.drawBufferedImage(texture.getBufferedImage(), sqX*squareSize, sqY*squareSize);
    }

    protected void drawSquare(){

    }


    protected boolean inBounds(int sqX, int sqY){
        return 0 <= sqX && sqX < columns && 0 <= sqY && 0 <= sqY;
    }


}
