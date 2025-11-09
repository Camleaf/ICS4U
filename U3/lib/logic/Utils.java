package lib.logic;
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

    public static boolean equivalentSign(int x, int y){
        if (x < 0 && y < 0){
            return true;
        } else if (x >0 && y >= 0){
            return true;
        }
        return false;
    }
}
