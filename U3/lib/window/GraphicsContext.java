package lib.window;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Color;

/**
    * Individual graphics context which abstracts modifying pixels individually into direct operations like bliting rasterized images and drawing geometric objects
    * <p>
    @author CamLeaf
*/
public class GraphicsContext{
    private BufferedImage buffer;
    private int[] pixels;
    private int width;
    private int height;
    
    public GraphicsContext(int width, int height){
        this.width = width;
        this.height = height;
        buffer = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData(); // From unit 2 project
        
    }
    /*Internal Methods
     *  paintComponent: Used by classes using this context to draw the custom graphics to screen
     */
    public void paintComponent(Graphics g){
        g.drawImage(buffer,0,0,null);
    }
    
    /*Public
     *  drawText   :     Draws a string to screen at x,y coordinates using the default font
     *  drawRect   :     Draws a rectangle at (int) x, (int) y with dimensions (int) w, (int) h and background (Color) colour
     *  flushBuffer:     Flushes the pixels of the current buffer of type BufferedImage and resets them to AWT's Color.BLACK
     *  drawEllipse:     Draws an ellipse with centre {x-(w/2),y-(h/2)}, and dimensions w,h
     *  drawCircle :     Draws a circle with centre {x-r,y-r}, and radius r
     *  getWidth   :     Returns the integer width of the panel
     *  getHeight  :     Returns the integer height of the panel
     */

    /**
     * Flushes the buffer frame
     */     
    public void flushBuffer(){
        Graphics g = buffer.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
    }

    public void drawText(String string, int x, int y){
        /*
         * This function has inefficient usage of the graphics object's overhead so I may make one to draw multiple lines as well for optimization's sake
         */
        Graphics2D g = buffer.createGraphics();
        g.drawString(string,x,y);
        g.dispose();
    }

    /**
     * Draws the given BufferedImage to the panel with upper-left corner (x,y)
     * @param image the BufferedImage to draw to the screen
     * @param x the x coordinate of the upper-left corner
     * @param y the y coordinate of the upper-left corner
     */

    public void drawBufferedImage(BufferedImage image, int x, int y){
        Graphics2D g = buffer.createGraphics();
        g.drawImage(image, x, y, null);
        g.dispose();
    }


    /**
        * Draws an rect with top-left corner (x,y) and dimensions (w,h)
        * @param x  integer x coordinate of top left corner of rect
        * @param y  integer y coordinate of top left corner of rect
        * @param w  width of rect
        * @param h  height of rect
        * @param colour Colour of the rect
    */
    public void drawRect(int x, int y, int w, int h, Color colour){
        int colourInt = colour.getRGB(); 
        for (int col = x;col<x+w;col++){
            for (int row = y;row<y+h;row++){
                if (col >= this.width || row >= this.height || col <0 || row < 0){continue;}
                pixels[col + row*this.width] = colourInt;
            }
        }
    }
    /**
        * Draws a hollow rect with top-left corner (x,y) and dimensions (w,h)
        * @param x  integer x coordinate of top left corner of rect
        * @param y  integer y coordinate of top left corner of rect
        * @param w  width of rect
        * @param h  height of rect
        * @param colour Colour of the rect
    */
    public void drawRectBorder(int x, int y, int w, int h, Color colour){
        Graphics2D g = buffer.createGraphics();
        g.setColor(colour);
        g.drawRect(x, y, w, h);
        g.dispose();
    }

    /**
        * Draws a hollow rect with top-left corner (x,y) and dimensions (w,h), and corner arcs of width arcWidth, and height arcHeight
        * @param x  integer x coordinate of top left corner of rect
        * @param y  integer y coordinate of top left corner of rect
        * @param w  width of rect
        * @param h  height of rect
        * @param arcWidth horizontal diametre of the arcs at the four corners
        * @param arcHeight vertical diametre o the arcs at the four corners
        * @param colour Colour of the rect
    */
    public void drawRectBorder(int x, int y, int w, int h, int arcWidth, int arcHeight, Color colour){
        Graphics2D g = buffer.createGraphics();
        g.setColor(colour);
        g.drawRoundRect(x,y,w,h,arcWidth,arcHeight);
        g.dispose();
    }

     /**
        * Draws an rect with top-left corner (x,y) and dimensions (w,h), and corner arcs of width arcWidth, and height arcHeight
        * @param x  integer x coordinate of top left corner of rect
        * @param y  integer y coordinate of top left corner of rect
        * @param w  width of rect
        * @param h  height of rect
        * @param arcWidth horizontal diametre of the arcs at the four corners
        * @param arcHeight vertical diametre o the arcs at the four corners
        * @param colour Colour of the rect
    */
    public void drawRect(int x, int y, int w, int h, float arcWidth, float arcHeight, Color colour){
        Graphics2D g = buffer.createGraphics();
        g.setColor(colour);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //https://stackoverflow.com/questions/1094539/how-to-draw-a-decent-looking-circle-in-java
        g.fill(new RoundRectangle2D.Float(x, y, w, h, arcWidth, arcHeight));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.dispose();
    }

     /**
        * Draws an rect with top-left corner (x,y) and dimensions (w,h), width corners of radius cornerRadius
        * @param x  integer x coordinate of top left corner of rect
        * @param y  integer y coordinate of top left corner of rect
        * @param w  width of rect
        * @param h  height of rect
        * @param cornerRadius the radius in pixels of the corners
        * @param colour Colour of the rect
    */
    public void drawRect(int x, int y, int w, int h, int cornerRadius, Color colour){
        drawRect(x, y, w, h, cornerRadius, cornerRadius, colour);
    }

    /**
        * Draws an ellipse with centre {x-(w/2),y-(h/2)}, and dimensions w,h
        * @param x integer x coordinate of top left of bounding box of ellipse with sidelength w
        * @param y integer y coordinte of top left of bounding box of ellipse with sidelength h
        * @param w Width of the ellipse and its bounding box
        * @param h Height of the ellipse and its bounding box
        * @param colour Colour of the ellipse
    */
    public void drawEllipse(int x, int y, int w, int h, Color colour){
        Graphics2D g = buffer.createGraphics();
        g.setColor(colour);
        g.fillOval(x,y,w,h);
        g.dispose();
    }

    /**
        * Draws a hollow ellipse with centre {x-(w/2),y-(h/2)}, and dimensions w,h
        * @param x integer x coordinate of top left of bounding box of ellipse with sidelength w
        * @param y integer y coordinte of top left of bounding box of ellipse with sidelength h
        * @param w Width of the ellipse and its bounding box
        * @param h Height of the ellipse and its bounding box
        * @param colour Colour of the ellipse border
    */
    public void drawEllipseBorder(int x, int y, int w, int h, Color colour){
        Graphics2D g = buffer.createGraphics();
        g.setColor(colour);
        g.drawOval(x,y,w,h);
        g.dispose();
    }


    /**
        * Draws a circle with centre {x-r,y-r}, and radius r
        * @param x integer x coordinate of top left of bounding box of ellipse with sidelength 2r
        * @param y integer y coordinte of top left of bounding box of ellipse with sidelength 2r
        * @param r Radius of the circle
        * @param colour Colour of the circle
    */

    public void drawCircle(int x, int y, int r, Color colour){
        drawEllipse(x, y, r*2, r*2, colour);
    }

    /**
        * Draws a hollow circle with centre {x-r,y-r}, and radius r
        * @param x integer x coordinate of top left of bounding box of ellipse with sidelength 2r
        * @param y integer y coordinte of top left of bounding box of ellipse with sidelength 2r
        * @param r Radius of the circle
        * @param colour Colour of the circle
    */

    public void drawCircleBorder(int x, int y, int r, Color colour){
        drawEllipseBorder(x, y, r*2, r*2, colour);
    }


    public void fill(Color colour){
        drawRect(0, 0, this.width, this.height, colour);
    }

    public int getWidth(){return this.width;};

    public int getHeight(){return this.height;};

}
