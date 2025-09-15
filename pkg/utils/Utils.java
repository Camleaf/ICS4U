package pkg.utils;
import java.util.Arrays;

public class Utils {
    public static boolean containsArray(int[][] arr, int[] query){
        for (int[] element : arr){

            if (Arrays.equals(element,query)){
                return true;
            }
        }

        return false;
    }
}
