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
        for (int[] hit : hits) {hit[0] = -1;hit[1]=-1;}
        hitCount = 0;
    }

    public Response castHit(int[] query){
        System.out.println((!Utils.containsArray(coordinates.clone(), query)) || (Utils.containsArray(hits.clone(), query)));
        if ((!Utils.containsArray(coordinates.clone(), query)) || (Utils.containsArray(hits.clone(), query))){
            return new Response("Fail",10);
        }
        
        hits[hitCount] = query; // this single line, when deleted, solves my issue, however no other code around here flags anything, for some reason. Maybe its something else modifying this list but how the fuck would that work
        hitCount += 1;
        if (hitCount != length) {
            return new Response("Success", 20);
        }
        return new Response("Sink",21);
    }

    public boolean hasMatch(int[] query){
        return Utils.containsArray(coordinates.clone(), query);
    }
    public boolean hasHit(int[] query){
        // for (int[] hit : hits){
        //         System.out.printf("{%d, %d} : {%d, %d}\n",hit[0],hit[1], query[0], query[1]);
        //     }
        // if (Utils.containsArray(hits, query)){
        //     System.out.println("HITIHTIHITIT");
        //     for (int[] hit : hits){
        //         System.out.printf("{%d, %d} : {%d, %d}\n",hit[0],hit[1], query[0], query[1]);
        //     }
        // }
        return Utils.containsArray(hits.clone(), query);
    }
}
