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
    
    public BoardRenderer(int width, int height){
        super(width,height);
        gct.fill(Color.black);
    }   

    public void drawBackground(int[][] textureArray){
        for (int row  = 0;row<textureArray.length;row++){
            for (int col = 0;col<textureArray[0].length;col++){
                // will be replaced with a gct.drawImage
                gct.drawBufferedImage(
                    indexTexture(textureArray[row][col]),
                    col*squareSize,
                    row*squareSize
                );
            }
        }
    }

    // Piece index will be representative of the spot that it occupies on spritesheet. We will have 256 px sheet with 4x4 grid, so 64px graphics
    // 0, 1, 2, 3 will be first row, 4, 5, 6, 7 wil lbe second, etc.
    public static final int TEXTURE_BACKGROUND_0 = 0; // texture indicators
    public static final int TEXTURE_BACKGROUND_1 = 1;
    public static final int TEXTURE_ROAD_0 = 2;

    public BufferedImage indexTexture(int textureIndex){
        int row = (int) Math.floor(textureIndex/4);
        int col = textureIndex%4;
        return texture.getSubImage(col*spriteSheetSquareSize,row*spriteSheetSquareSize,spriteSheetSquareSize,spriteSheetSquareSize);
        
    }
}
