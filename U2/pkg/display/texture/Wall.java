package pkg.display.texture;
import pkg.display.texture.TextureUtil;

public class Wall {
    public int[] rgbData;
    public int sideLength;
    public String path;

    /*
     * Walls must have a sidelength of 64
     */
    public Wall(String path, int sideLength){
        if (sideLength != 64){
            System.out.println("Wall length not 64, this could cause errors");
        }
        this.sideLength = sideLength;
        this.path = path;
        rgbData = TextureUtil.get2dArray(path, sideLength).clone();
    }
}
