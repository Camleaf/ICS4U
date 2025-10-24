package pkg.display;
import pkg.display.texture.Wall;
import java.util.Map;
import static java.util.Map.entry;

public class Texture {
    //Entry tracker
    public static int BLANK = 1;


    /*Texture Maps*/
    public static Map<Integer, Wall> WALL = Map.ofEntries(
        entry(1,new Wall("src/blank.png",64)) // simply add comma and new line with entry
    );


}
