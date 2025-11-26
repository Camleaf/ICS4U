package src.window.panels;
import lib.window.base.BaseComponent;
import java.awt.Color;

public class PlayWindow extends BaseComponent {
    public int gridSize; //preferably a multiple of 8
    public final int columns = 10;
    public final int rows = 20;
    private final int squareSize = 40;
    public int width;
    public int height;

    public PlayWindow(int width, int height){
        super(width, height);
        gct.fill(Color.black);
    }


}
