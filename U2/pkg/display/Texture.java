package pkg.display;
import pkg.display.texture.Wall;
import java.util.Map;
import static java.util.Map.entry;

public class Texture {
    //Entry tracker
    public static int BLANK = 1;
    public static int BRICK = 2;
    public static int GRASSWALL = 3;

    public static int textureIndexLimit = 3;


    /*Texture Maps*/
    public static Map<Integer, Wall> WALL = Map.ofEntries(
        entry(BLANK,new Wall("src/empty.png",64)), // simply add comma and new line with entry
        entry(BRICK,new Wall("src/brick.png",64)),
        entry(GRASSWALL, new Wall("src/grassyWall.png", 64))
    );

    public static boolean textureIndexInBounds(int idx){
        return idx < 0 || idx > textureIndexLimit; // update this based on new keys added
    }
    

}
