package U1.pkg.board;
import U1.pkg.messaging.Response;
import U1.pkg.utils.Utils;

public class Ship {
    public static int[] lengths = {5,4,3,3,2};
    private int[][] coordinates;
    private int[][] hits;
    private int hitCount;
    public int length;
    Ship(int inLength,int[][] inCoordinates ){
        length = inLength;
        coordinates = inCoordinates;
        hits = new int[length][2];
        for (int[] hit : hits) {hit[0] = -1;hit[1]=-1;}
        hitCount = 0;
    }

    public Response castHit(int[] query){
        

        if ((!Utils.contains(coordinates, query)) || (Utils.contains(hits, query))){
            return new Response("Fail",10);
        }
        else{
            hits[hitCount] = query.clone(); 
        }
        hitCount += 1;
        if (hitCount != length) {
            return new Response("Success", 20);
        }
        return new Response("Sink",21);
    }

    public boolean hasMatch(int[] query){
        return Utils.contains(coordinates, query);
    }
    public boolean hasHit(int[] query){

        return Utils.contains(hits, query);
    }

    public int[][] getCoordinates(){
        return Utils.deepCopy(coordinates);
    }
    public int[][] getHitCoordinates(){
        return Utils.deepCopy(hits);
    }

    public boolean isSunken(){
        // System.out.println(hitCount);
        // System.out.println(length);
        return hitCount == length;
    }
    public int getLength(){
        return length;
    }
}
