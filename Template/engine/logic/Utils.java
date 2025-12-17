package engine.logic;
import java.awt.Point;

public class Utils {
    public static boolean contains(Point[] arr, Point query){
        for (Point element : arr){

            if (element.equals(query)){
                return true;
            }
        }

        return false;
    }
}
