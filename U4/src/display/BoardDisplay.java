package src.display;
import src.render.BoardRenderer;

public class BoardDisplay extends BoardRenderer {
    
    public static int[][] testingBoardGraphics = {
        {0, 1, 1, 0, 1, 1, 0, 1},
        {1, 1, 1, 1, 2, 2, 2, 2},
        {0, 0, 1, 1, 2, 1, 1, 0},
        {2, 2, 2, 2, 2, 1, 0, 1},
        {1, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 1 ,0 ,0},
        {0, 1, 0 ,0 ,0, 1, 1, 0},
        {0, 1, 1, 0, 1, 1, 0, 1},
    };

    public BoardDisplay(){
        // Width is 640 x 640 to start
        super(640,640);
       
        drawBackground(testingBoardGraphics);
        
    }
}
