package src.display;
import src.render.BoardRenderer;
import java.awt.Point;
import java.awt.Rectangle;
import src.logic.Tile;
import src.logic.towers.*;
/** The function which handles instructions given from the gameloop and delegates rendering to BoardRenderer and calculations to BoardLogic
 * @author Alexcdw
 */
public class BoardDisplay extends BoardRenderer {
        
    
    private Point selectedPoint = null;
    private int boardCells = 8;

    // A testing map I made to make sure that the graphics work
    public static int[][] testingBoardGraphics = {
        {0, 1, 1, 0, 1, 1, 0, 1},
        {1, 1, 1, 1, 1, 0, 0, 0},
        {0, 0, 1, 1, 1, 1, 1, 0},
        {0, 0, 1, 1, 0, 1, 0, 1},
        {1, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 1 ,0 ,0},
        {0, 1, 0 ,0 ,0, 1, 1, 0},
        {0, 1, 1, 0, 1, 1, 0, 1},
    };

    public static Point[] testingPath = {
        new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0), new Point(3,1), new Point(3,2), new Point(3,3), new Point(3,4), new Point(4,4), new Point(5,4),new Point(6,4),new Point(7,4)
    };

    public BoardDisplay(){
        // Width is 640 x 640 to start
        super(512,512);
         
        setBackgroundArray(testingBoardGraphics, testingPath);    
        drawBackground();
        tileArray[2][0].setOccupier(new TowerTest());
        drawTile(tileArray[2][0],0);
        
    }


    /** Handles click on board and delegates a variety of functions, but in general returns the coordinate of the point modified, or null 
     * @return the coordinate of the point modified, or null 
     */
    public Point handleClick(Point clickPos){
        

        // We need to normalize position with reference to the board, as the click is global but we want in the board
        // Normalize goes from pixel size to 1-8 on the board
        Rectangle bounds = getBounds();

        Point normalizedPosition = new Point(
            (clickPos.x-bounds.x)/squareSize,
            (clickPos.y-bounds.y)/squareSize
        );
        
        if (normalizedPosition.x<0 || normalizedPosition.y <0 || normalizedPosition.x >= boardCells || normalizedPosition.y >= boardCells){
            // remove selected point from the board and set to null since we clicked off
            if (selectedPoint != null) drawTile(tileArray[selectedPoint.y][selectedPoint.x]);
            selectedPoint = null;
            return null;
        }
        
        if (selectedPoint != null){
            if (selectedPoint.x == normalizedPosition.x&& selectedPoint.y == normalizedPosition.y){
                return selectedPoint;
            };
        };
        // If clicked on road we discard the click
        if (tileArray[normalizedPosition.y][normalizedPosition.x].getType() == Tile.Type.ROAD){
            if (selectedPoint != null) drawTile(tileArray[selectedPoint.y][selectedPoint.x]);
            selectedPoint = null;
            return null;
        }
        // Remove old selected point and add new selected point
        
        if (selectedPoint != null) drawTile(tileArray[selectedPoint.y][selectedPoint.x]);
        selectedPoint = new Point(
                            normalizedPosition.x,
                            normalizedPosition.y
                        );
        
        drawTile(tileArray[selectedPoint.y][selectedPoint.x],BoardRenderer.DRAW_TILE_HIGHLIGHT);
                  
        // Todo add handling for clicks too bring up menu
        

        return selectedPoint;

    }
}
