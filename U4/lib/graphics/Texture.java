
package lib.graphics;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;

/** A class to manage textures and/or spritesheets. Contains functions to work with both native java swing and raw awt graphics.
 * @author SpencerM
 * @author Alexcedw
 */
public class Texture {
    
    private JLabel swingImg;
    private BufferedImage image;
    private int w;
    private int h;
    
    
    public Texture(String path, int w, int h){
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        }
        this.w = (w >= 0) ? w : image.getWidth();
        this.h = (h >= 0) ? h : image.getHeight();
        image = rescale(image,w,h);
        
    }
    
    /** Rescales a BufferedImage to given dimensions w,h.
     * @param image the image to be rescaled
     * @param w the integer width of the scaled image
     * @param h the integer height of the scaled image
     * @return the scaled bufferedImage
     */
    private BufferedImage rescale(BufferedImage image, int w, int h){

        if (image.getWidth() == w && image.getHeight() == h || w < 0 || h < 0){return image;}

        Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
        BufferedImage scaledBuffer = new BufferedImage(w, h, image.getType());
        Graphics g = scaledBuffer.getGraphics();
        g.drawImage(scaledImage,0,0,null);
        g.dispose();
        return scaledBuffer;
    }
    
    /**
     * Takes an image file and returns a JLabel of the image
     * @Params String path: the path of the file
     * @Returns JLabel: the label of the image
     */
    private JLabel loadImage(String path) {
        JLabel swingImage = new JLabel();
        swingImage.setBounds(0, 0, this.w, this.h);
        swingImage.setIcon(getImageIcon());
        return swingImage;
    }
    
    public ImageIcon getImageIcon(){
        return new ImageIcon(image);
    }
    
    /**
     * @returns the texture in BufferedImage format
     */
    public BufferedImage getBufferedImage(){
        return image;
    }
    
    /**
     * @returns the teture in JLabel format
     */
    public JLabel getJLabel(){
        return swingImg;
    }
    
    /** Sets the bounds of the JSwing container which the image can reside in.
     */
    public void setSwingBounds(int x, int y, int w, int h){
        swingImg.setBounds(x,y,w,h);
    }
    
    /**
     * Gets a slice of the texture with top left coordinate x,y and dimensions w,h as a BufferedImage. Typically would be used for a sprite sheet
     * @param x the x coordinate of the upper-left corner of the subregion
     * @param y the y coordinate of the upper-left corner of the subregion
     * @param w the width of the subregion
     * @param h the height of the subregion
     * @return a subimage of the texture with upper-left corner x,y and dimensions w,h
     */
    public BufferedImage getSubImage(int x, int y, int w, int h){
        return image.getSubimage(x,y,w,h);
    }
}














