package src.render;
import lib.graphics.GraphicsComponent;
import lib.graphics.Texture;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Color;
import lib.graphics.Texture;

/**
 * Provides functions for board display to render the board
 * @author Alexcedw
 * @author SpencerM
 */
public class BoardRenderer extends GraphicsComponent {
    
    protected Texture texture = new Texture("public/Tile_Spritesheet.png",256,256);
    protected int squareSize = 64;
    protected int spriteSheetSquareSize = 64;
    protected int[][] textureArray;
    protected Point[] path;
    
    public BoardRenderer(int width, int height){
        super(width,height);
        gct.fill(Color.black);
    }   
    

    /**
     * Sets the backgrund textures which the renderer uses.
     */
    protected void setBackgroundArray(int[][] textureArray, Point[] path){
        this.textureArray = textureArray;
        this.path = path;
    } 

    /**
     * Draws the background based on a given textureArray made of integers. No real limit to the size of the background, but 8x8 is what this func will most likely be rendering
     */
    protected void drawBackground(){
        for (int row  = 0;row<textureArray.length;row++){
            for (int col = 0;col<textureArray[row].length;col++){
                // will be replaced with a gct.drawImage
                gct.drawBufferedImage(
                    indexTexture(textureArray[row][col]),
                    col*squareSize,
                    row*squareSize
                );

            }
        }

        for (Point p : path){
            gct.drawBufferedImage(
                indexTexture(2),
                p.x*squareSize,
                p.y*squareSize
            );
        };
    }

    public static final int TEXTURE_BACKGROUND_0 = 0; // texture indicators
    public static final int TEXTURE_BACKGROUND_1 = 1;
    public static final int TEXTURE_ROAD_0 = 2;
    /**
     * Gets a bufferedImage from the global spritesheet using a texture numbering system
     * @param textureIndex the id of a texture, where Id corresponds to position on the spritesheet.
     * <p>
     * Piece index will be representative of the spot that it occupies on spritesheet. We will have 256 px sheet with 4x4 grid, so 64px graphics
     * <p>
     * 0, 1, 2, 3 will be first row, 4, 5, 6, 7 wil lbe second, etc.
     */
    protected BufferedImage indexTexture(int textureIndex){
        int row = (int) Math.floor(textureIndex/4);
        int col = textureIndex%4;
        return texture.getSubImage(col*spriteSheetSquareSize,row*spriteSheetSquareSize,spriteSheetSquareSize,spriteSheetSquareSize);
    }


    /**
     * Returns true if given point is in the stored path, false otherwise
     * @param p The point to check if is in path
     */
    protected boolean isInPath(Point p){
        for (Point pathP : path){
            if (p.x == pathP.x && p.y==pathP.y) return true;
        }
        return false;
    }


    /**
     * Resets the square at the given point to it's starting game state
     * @param p the square to reset
     */
    protected void drawEmpty(Point p){
        gct.drawBufferedImage(
            indexTexture(
                (isInPath(p)) ? 2
                : textureArray[p.y][p.x]
            ),
            p.x,
            p.y
        );
    }






}
