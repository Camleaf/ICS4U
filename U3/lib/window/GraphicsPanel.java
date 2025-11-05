package lib.window;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Color;
import java.awt.image.BufferStrategy;


public class GraphicsPanel extends JPanel{
    /*
     * Individual graphics panel which abstracts modifying pixels individually into direct operations like bliting rasterized images and drawing geometric objects
     */
    private BufferedImage buffer;
    private BufferStrategy bufferStrategy;
    private int[] pixels;
    private int width;
    private int height;
    
    public GraphicsPanel(int width, int height){
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
     *  getWidth   :     Returns the integer width of the panel
     *  getHeight  :     Returns the integer height of the panel
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
        Graphics g = buffer.getGraphics();
        g.drawString(string,x,y);
        g.dispose();
    }

    public void drawRect(int x, int y, int w, int h, Color colour){
        int colourInt = colour.getRGB(); 
        for (int col = x;col<x+w;col++){
            for (int row = y;row<y+h;row++){
                if (col >= this.width || row >= this.height || col <0 || row < 0){continue;}
                pixels[col + row*this.width] = colourInt;
            }
        }
    }
    public int getWidth(){return this.width;};

    public int getHeight(){return this.height;};



}
