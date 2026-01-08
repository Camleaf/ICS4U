package lib.graphics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Color;

/** A context which abstracts the direct modification of pixels into simple operations such as bliting rasterized images and drawing simple geometric objects
 * @author Alexcedw
 * @author SpencerM
 */
public class Context{
    
    private int width;
    private int height;
    private BufferedImage buffer;
    
    public Context(int width, int height){
        this.width = width;
        this.height = height;
        buffer = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
    }
    
    /**
     * Used by components as an alternative to their default paintComponent methods
     */
    public void paintComponent(Graphics g){
        g.drawImage(buffer,0,0,null);
    }
    
    /**
     * Completely flushes the current buffered screen
     */
    public void flushBuffer(){
        Graphics2D g = buffer.createGraphics();
        g.setColor(new Color(0,0,0,0));
        g.fillRect(0, 0, this.width, this.height);
        g.dispose();
    }
    
    
    /**
     * Draws a buffered image to the current buffer
     * @param image BufferedImage of the texture image we are drawing
     * @param x integer x coordinate of the top left of our rectangle
     * @param y integer the y coordinate of the top left of our rectangle
     */
    public void drawBufferedImage(BufferedImage image, int x, int y){
        Graphics2D g = buffer.createGraphics();
        g.drawImage(image, x, y, null);
        g.dispose(); // Always dispose the graphics object after using it to prevent memory leaks
    }
    
    
    /** Draws a rectangle of dimensions w,h pixels at x,y pixels, with colour c
     * @param x integer x coordinate of top left of rectangle
     * @param y integer y coordinate of top left of rectangle
     * @param w integer width of the rectangle
     * @param h integer height of the rectangle
     * @param c colour of the rectangle
     */
    public void drawRect(int x, int y, int w, int h, Color c){
        Graphics2D g = buffer.createGraphics();
        g.setColor(c);
        g.fillRect(x,y,w,h);
        g.dispose();
    }
    
    
    public void fill(Color colour){
        drawRect(0, 0, this.width, this.height, colour);
    }
}










