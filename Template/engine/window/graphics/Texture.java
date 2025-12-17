package engine.window.graphics;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;

/**
 * A general class to manage texture paths. Stores given texture in all needed formats, such as BufferedImage, ImageIcon, and JLabel. Also contains slicing functionality for spritesheet usage
 * @author Camleaf
 */
public class Texture {

    private JLabel swingImage;
    private BufferedImage image;
    private int w;
    private int h;

    /**
     * Constructor of the texture class that grabs the image and resizes it to the desired w,h. Also creates a swing JLabel for usage in swing
     * @param path Path of the image from the cwd
     * @param w Width of the image. The image will rescale to this width if it is not already at this size. If w < 0, then the image's native width will be used
     * @param h Height of the image. The image will rescale to thie height if it is not already at this size. If h < 0, then the image's native width will be used
     */
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
        swingImage = makeSwingComponent();
    }
    
    /* 
     * Internal methods
    */


    /**
     * Rescales a BuferedImage to the given w,h
     * @param image a bufferImage to be scaled
     * @param w width to be scaled to
     * @param h height to be scaled to
     * @return scaled bufferImage
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

    private JLabel makeSwingComponent(){
        JLabel swingImage = new JLabel();
        swingImage.setBounds(0, 0, this.w, this.h);
        swingImage.setIcon(getImageIcon());
        return swingImage;
    }
    /*
     * Public methods
     */
    /**
     * Gets a subimage of the texture with top left coordinate x,y and dimensions w,h as a BufferedImage. Typically would be used for a sprite sheet
     * @param x the x coordinate of the upper-left corner of the subregion
     * @param y the y coordinate of the upper-left corner of the subregion
     * @param w the width of the subregion
     * @param h the height of the subregion
     * @return a subimage of the texture with upper-left corner x,y and dimensions w,h
     */
    public BufferedImage getSlice(int x, int y, int w, int h){
        return image.getSubimage(x,y,w,h);
    }

    /**
     * Gets the texture as a BufferedImage
     * @return the texture as a BufferedImage
     */
    public BufferedImage getBufferedImage(){
        return image;
    }


    /**
     * Gets the texture as an IconImage
     * @return the texture as an IconImage
     */
    public ImageIcon getImageIcon(){
        return new ImageIcon(image);
    }

    /**
     * Returns the iconImage version of the texture on a JLabel
     * @return the JLabel version of the texture
     */
    public JLabel getSwingComponent(){
        return swingImage;
    }

    /**
     * Sets the bounds of the texture for when when it is to be added to a swing frame
     * @param x the x coordinate of the upper-left corner of the texture
     * @param y the y coordinate of the upper-left corner of the texture
     * @param w the width of the texture
     * @param h the height of the texture
     */
    public void setSwingBounds(int x, int y, int w, int h){
        swingImage.setBounds(x,y,w,h);
    }

    /**
     * Sets the location of the texture for when it is to be added to a swing frame
     * @param x the x coordinate of the upper-left corner of the texture
     * @param y the y coordinate of the upper-left corner of the texture
     */
    public void setSwingLocation(int x, int y){
        swingImage.setLocation(x, y);
    }

    /**
     * Sets the dimensions of the texture for when when it is to be added to a swing frame
     * @param w the width of the texture
     * @param h the height of the texture
     */
    public void setSwingSize(int w, int h){
        swingImage.setSize(w,h);
    }

}
