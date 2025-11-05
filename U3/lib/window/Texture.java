package lib.window;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Texture {
    


    public static int[] rasterize(String path, int w, int h){

        int[] raster = new int[w*h];
        try {
            BufferedImage image = ImageIO.read(new File(path));
            Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            BufferedImage scaledBuffer = new BufferedImage(w, h, image.getType());
            Graphics g = scaledBuffer.getGraphics();
            g.drawImage(scaledImage,0,0,null);
            g.dispose();
            scaledBuffer.getRGB(0, 0, w, h, raster, 0, w);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return raster;
    }
}
