package pkg.display.texture;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;


public class TextureUtil {
    /*
     * Static class containing useful texture parsing functions
     */

    /*Where sideLength is in pixels and assumed walls are squares */
    public static int[][] get2dArray(String path, int sideLength){

        int[][] rgbData = new int[sideLength][sideLength];
        try {
            BufferedImage image = ImageIO.read(new File(path));
            for (int i = 0;i<sideLength;i++){
                image.getRGB(i, 0, 1, sideLength, rgbData[i], 0, sideLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rgbData;


    }

}
