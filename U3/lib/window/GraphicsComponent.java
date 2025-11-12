package lib.window;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Color;

/**
    * Individual graphics component which abstracts modifying pixels individually into direct operations like bliting rasterized images and drawing geometric objects
    * <p>
    @author CamLeaf
*/
public class GraphicsComponent extends JPanel{
    private BufferedImage buffer;
    private int[] pixels;
    protected int width;
    protected int height;
    
    public GraphicsComponent(int width, int height){
        setLayout(null); 
        setSize(width,height);
        setIgnoreRepaint(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        this.width = width;
        this.height = height;
        buffer = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData(); // From unit 2 project
        
    }
    /*Internal Methods
     *  paintComponent: Overrides JPanel's paintComponent to paint objects using custom graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(buffer,0,0,null);
        g.dispose();
        repaint();
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
    

    public void refresh(){
        repaint();
    }

    /**
     * Flushes the buffer frame
     */     
    protected void flushBuffer(){
        Graphics g = buffer.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
    }

    protected void drawText(String string, int x, int y){
        /*
         * This function has inefficient usage of the graphics object's overhead so I may make one to draw multiple lines as well for optimization's sake
         */
        Graphics g = buffer.getGraphics();
        g.drawString(string,x,y);
        g.dispose();
    }

    /**
     * Draws the given BufferedImage to the panel with upper-left corner (x,y)
     * @param image the BufferedImage to draw to the screen
     * @param x the x coordinate of the upper-left corner
     * @param y the y coordinate of the upper-left corner
     */

    protected void drawBufferedImage(BufferedImage image, int x, int y){
        Graphics g = buffer.getGraphics();
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
    protected void drawRect(int x, int y, int w, int h, Color colour){
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
    protected void drawRectBorder(int x, int y, int w, int h, Color colour){
        Graphics g = buffer.getGraphics();
        g.setColor(colour);
        g.drawRect(x, y, w, h);
        g.dispose();
    }

    /**
        * Draws an ellipse with centre {x-(w/2),y-(h/2)}, and dimensions w,h
        * @param x integer x coordinate of top left of bounding box of ellipse with sidelength w
        * @param y integer y coordinte of top left of bounding box of ellipse with sidelength h
        * @param w Width of the ellipse and its bounding box
        * @param h Height of the ellipse and its bounding box
        * @param colour Colour of the ellipse
    */
    protected void drawEllipse(int x, int y, int w, int h, Color colour){
        Graphics g = buffer.getGraphics();
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
    protected void drawEllipseBorder(int x, int y, int w, int h, Color colour){
        Graphics g = buffer.getGraphics();
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

    protected void drawCircle(int x, int y, int r, Color colour){
        drawEllipse(x, y, r*2, r*2, colour);
    }

    /**
        * Draws a hollow circle with centre {x-r,y-r}, and radius r
        * @param x integer x coordinate of top left of bounding box of ellipse with sidelength 2r
        * @param y integer y coordinte of top left of bounding box of ellipse with sidelength 2r
        * @param r Radius of the circle
        * @param colour Colour of the circle
    */

    protected void drawCircleBorder(int x, int y, int r, Color colour){
        drawEllipseBorder(x, y, r*2, r*2, colour);
    }

    public int getStoredWidth(){return this.width;};

    public int getStoredHeight(){return this.height;};

}
