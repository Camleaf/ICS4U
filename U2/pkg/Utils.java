package pkg;

public class Utils {
    public static String[] numberChars = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    public static int sign(double x){

        return (x == 0) ? 0 : (x < 0) ? -1 : 1;
    }

    public static boolean contains(String[] arr, String query){
        for (String element : arr){

            if (element.equals(query)){
                return true;
            }
        }

        return false;
    }
    public static int[][] deepCopy(int[][] arr) {
        int[][] newArr = new int[arr.length][];
        for (int index = 0; index < arr.length; index++){
            newArr[index] = arr[index].clone();
        }
        return newArr;
    }

}
