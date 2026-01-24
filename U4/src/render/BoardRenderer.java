package src.render;
import lib.graphics.GraphicsComponent;
import lib.graphics.Texture;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Color;
import lib.graphics.Texture;
import src.logic.Tile;
import src.render.Colours;
import src.logic.Tower;

/**
 * Provides functions for board display to render the board
 * @author Alexcedw
 * @author SpencerM
 */
public class BoardRenderer extends GraphicsComponent {
    
    protected Texture texture = new Texture("public/Tile_Spritesheet.png",256,256);
    public static int squareSize = 64;
    public static int spriteSheetSquareSize = 64;
    protected Tile[][] tileArray;
    
    public BoardRenderer(int width, int height){
        super(width,height);
        gct.fill(Color.black); // gct is a Context object inherited from GraphicsComponent
    }   
    
    public Tile[][] getTileArray(){
        return this.tileArray;
    };

    /**
     * Sets the backgrund textures which the renderer uses. And turns the data into tiles
     */
    protected void setBackgroundArray(int[][] textureArray, Point[] path){
        // Add basic textures
        this.tileArray = new Tile[textureArray.length][textureArray[0].length];
        for (int row = 0; row<textureArray.length;row++){
            for (int col = 0; col<textureArray[0].length;col++){
                this.tileArray[row][col] = new Tile(textureArray[row][col], new Point(col,row));
            }
        }
        // Replace path tiles with path
        for (Point p : path){
            this.tileArray[p.y][p.x].textureIndex = 2;
             this.tileArray[p.y][p.x].setType(Tile.Type.ROAD);
        };
        
    } 

    /**
     * Draws the background based on a given textureArray made of integers. No real limit to the size of the background, but 8x8 is what this func will most likely be rendering
     */
    protected void drawBackground(){
        for (int row  = 0;row<tileArray.length;row++){
            for (int col = 0;col<tileArray[row].length;col++){
                // will be replaced with a gct.drawImage
                gct.drawBufferedImage(
                    indexTexture(tileArray[row][col].textureIndex),
                    col*squareSize,
                    row*squareSize
                );

            }
        }
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
     * 0, 1, 2, 3 will be first row, 4, 5, 6, 7 will be second, etc.
     */
    protected BufferedImage indexTexture(int textureIndex){
        int row = (int) Math.floor(textureIndex/4);
        int col = textureIndex%4;
        return texture.getSubImage(col*spriteSheetSquareSize,row*spriteSheetSquareSize,spriteSheetSquareSize,spriteSheetSquareSize);
    }
    
    /** Draws a tile with the default background
     * @param t the Tile object to draw
     */
    protected void drawTile(Tile t){
        drawTile(t,0);
    }
    
    
    public static final int DRAW_TILE_DEFAULT = 0;
    public static final int DRAW_TILE_HIGHLIGHT = 1;
    /** Draws a tile with the default background
     * @param t the Tile object to draw
     * @param OPTION the integer option to pass
     */
    protected void drawTile(Tile t, int OPTION){
        
        // Draws the background based on the option passed
        switch (OPTION){
            case DRAW_TILE_HIGHLIGHT:
                gct.drawRect(t.getX()*squareSize, t.getY()*squareSize,squareSize,squareSize,Colours.selectHighlight);
                break;
            default:
                gct.drawBufferedImage(
                    indexTexture(t.textureIndex),
                    t.getX() * squareSize,
                    t.getY() * squareSize
                );
                break;
        }
        
        // Draws a tower if one exists on the tile
        Tower tower = t.getOccupier();
        if (tower != null){
            gct.drawBufferedImage(
                indexTexture(tower.getTextureIndex()+tower.getUpgradeLevel()),
                t.getX() * squareSize,
                t.getY() * squareSize
            );
        }
        
    }





}