package pkg;

public class Utils {
    public static int sign(double x){

        return (x == 0) ? 0 : (x < 0) ? -1 : 1;
    }
}
