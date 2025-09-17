package pkg.board;
import pkg.messaging.Response;
import pkg.utils.Utils;

public class Ship {
    static int[] lengths = {5,4,3,3,2};
    private int[][] coordinates;
    private int[][] hits;
    private int hitCount;
    public int length;
    Ship(int inLength,int[][] inCoordinates ){
        length = inLength;
        coordinates = inCoordinates;
        hits = new int[length][2];
        hitCount = 0;
    }

    public Response castHit(int[] target){
        if (!Utils.containsArray(coordinates, target) || Utils.containsArray(hits, target)){
            return new Response("Fail",10);
        } 

        hits[hitCount] = target;
        hitCount += 1;
        if (hitCount != length) {
            return new Response("Success", 20);
        }
        return new Response("Sink",21);
    }

    public boolean hasMatch(int[] coordinate){
        return Utils.containsArray(coordinates, coordinate);
    }
    public boolean hasHit(int[] coordinate){
        return Utils.containsArray(hits, coordinate);
    }
}
