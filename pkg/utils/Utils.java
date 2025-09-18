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

    public static int indexOf(int[] arr, int element){
        for (int index = 0;index<arr.length;index++){
            if (arr[index] == element){
                return index;
            }
        }
        return -1;
    }

    public static int[][] deepCopy(int[][] arr) {
        int[][] newArr = new int[arr.length][];
        for (int index = 0; index < arr.length; index++){
            newArr[index] = arr[index].clone();
        }
        return newArr;
    }

    public static int[][] reverseArray(int[][] arr){
        /**
         * Returns a new array which is the reverse of the input array. 
         * */
        int[][] sortedArr = new int[arr.length][];
        for (int i = 0;i<arr.length;i++){
            int[] element = arr[i];
            sortedArr[i] = element.clone();
        }
        return arr;
    }
    public static int[] reverseArray(int[] arr){
        int[] sortedArr = new int[arr.length];
        for (int i = 0;i<arr.length;i++){
            int element = arr[i];
            sortedArr[i] = element;
        }
        return sortedArr;
    }
}

