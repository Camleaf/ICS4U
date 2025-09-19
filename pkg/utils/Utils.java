package pkg.utils;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
    public static boolean contains(int[][] arr, int[] query){
        for (int[] element : arr){

            if (Arrays.equals(element,query)){
                return true;
            }
        }

        return false;
    }
    public static boolean contains(String[] arr, String query){
        for (String element : arr){

            if (element.equals(query)){
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

    public static int indexOf(String[] arr, String element){
        for (int index = 0;index<arr.length;index++){
            if (arr[index].equals(element)){
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

    public static int[][] exclude(int[][] arr1, int[][] arr2){
        /* Removes all elements of param arr1 that exist within arr2*/
        ArrayList<int[]> arr3 = new ArrayList<int[]>();

        for (int[] element : arr1){
            if (contains(arr2, element)){
                continue;
            }
            arr3.add(element.clone());
        }

        return arr3.toArray(new int[arr3.size()][]);
    }
}

