package pkg.display.texture;
import pkg.display.Texture;

public class Wall {
    public int[][] rgbData;
    public int sideLength;
    public String path;

    public Wall(String path, int sideLength){
        this.sideLength = sideLength;
        this.path = path;
        rgbData = Texture.get2dArray(path, sideLength);
    }
}
