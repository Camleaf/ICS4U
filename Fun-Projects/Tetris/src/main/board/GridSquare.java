package src.main.board;

import java.awt.Color;

public class GridSquare {
    public boolean filled;
    public Color color;

    public GridSquare(boolean filled, Color c){
        this.filled =filled;
        this.color = c;
    }
}
