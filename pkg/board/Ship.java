package pkg.board;
import java.util.Arrays;
import pkg.messaging.Response;

public class Ship {
    static int[] lengths = {5,4,3,3,2};
    private int[][] coordinates;
    private int hits;
    public int length;
    Ship(int inLength,int[][] inCoordinates ){
        length = inLength;
        coordinates = inCoordinates;
        hits = 0;
    }

    public Response hitCheck(int[] target){
        if (!Arrays.asList(coordinates).contains(target)){
            return new Response("Fail",10);
        } 

        hits += 1;

        if (hits == length) {
            return new Response("Success", 20);
        }
        return new Response("Sink",21);
    }
}
