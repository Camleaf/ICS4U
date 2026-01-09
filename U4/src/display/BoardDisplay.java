package src.display;
import src.render.BoardRenderer;

/** The function which handles instructions given from the gameloop and delegates rendering to BoardRenderer and calculations to BoardLogic
 * @author Alexcdw
 */
public class BoardDisplay extends BoardRenderer {
    
    // A testing map I made to make sure that the graphics work
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
        super(512,512);
       
        drawBackground(testingBoardGraphics);
        
    }
}
