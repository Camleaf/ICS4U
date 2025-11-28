package src.main.board;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import src.main.board.PieceType;


public class KickTable {
    private HashMap<Integer,Point[]> kickMap; // The kickmap for every piece but the Tetris piece and O pieces (which has no kickmap)
    private HashMap<Integer,Point[]> tetrisKickMap;// The kickmap specifically for the Tetris piece
    // The kick table uses curRot + nextRot as idx, and i need to switch 3->4 so that keys don't overlap

    public KickTable(){
        buildKickTable();   
    }


    public Map<Integer,Integer> keyTransform = Map.of(
        0,0,
        1,1,
        2,2,
        3,4
    );

    public Point[] get(int from, int to, PieceType type, boolean counterClockwise){
        // To and from should never be equal going into this, so I don't need to handle it

        Integer key = Integer.valueOf(
            keyTransform.get(from) + 
            keyTransform.get(to)
        ); // Probably don't need to do this integer wrapping but I've had issues in the past w/ this

        Point[] kickTable = kickMap.getOrDefault(key, new Point[]{new Point(0,0)});
        if (type == PieceType.I){
            kickTable = tetrisKickMap.getOrDefault(key, new Point[]{new Point(0,0)});
        }

        if (counterClockwise){ // On CCW rotation we inverse all signs
            kickTable = inverseTableSigns(kickTable);
        }

        return kickTable;
    }




    private Point[] inverseTableSigns(Point[] table){ // flips the signs of a given table
        Point[] inversedKickTable = new Point[table.length];

        int idx = 0;
        for (Point kick : table){
            inversedKickTable[idx] = new Point(-kick.x,-kick.y);
            idx++;
        }

        return inversedKickTable;
    }


    private void buildKickTable(){ // kicktables from here https://harddrop.com/wiki/SRS
        kickMap = new HashMap<>(); // define kickmaps
        tetrisKickMap = new HashMap<>();
        // All kick tables are only for clockwise rotations. On counter clockwise i will just invert the given kick
        kickMap.put(1, new Point[]{
            new Point(0,0), //Trial 1 - 5
            new Point(-1,0), 
            new Point(-1,+1), 
            new Point(0,-2), 
            new Point(-1,-2)
        }); // rot 0 -> 1    1

        kickMap.put(3, new Point[]{ // rot 1 -> 2    3
            new Point(0,0),  //Trial 1 - 5
            new Point(+1,0), 
            new Point(+1,-1), 
            new Point(0,+2), 
            new Point(+1,+2)
        }); 

        kickMap.put(6, new Point[]{ // 2 -> 3|4    6
            new Point(0,0),  //Trial 1 - 5
            new Point(+1,0), 
            new Point(+1,+1), 
            new Point(0,-2), 
            new Point(+1,-2)
        });

        kickMap.put(4, new Point[]{ // 3|4 -> 0    4
            new Point(0,0),  //Trial 1 - 5
            new Point(-1,0), 
            new Point(-1,-1), 
            new Point(0,+2), 
            new Point(-1,+2)
        }); 


        // Kicktables for I/Tetris piece

        tetrisKickMap.put(1, new Point[]{
            new Point(0,0), //Trial 1 - 5
            new Point(-2,0), 
            new Point(+1,0), 
            new Point(-2,-1), 
            new Point(+1,+2)
            
        }); // rot 0 -> 1    1

        tetrisKickMap.put(3, new Point[]{ // rot 1 -> 2    3
            new Point(0,0),  //Trial 1 - 5
            new Point(-1,0), 
            new Point(+2,0), 
            new Point(-1,+2), 
            new Point(+2,-1),
        }); 

        tetrisKickMap.put(6, new Point[]{ // 2 -> 3|4    6
            new Point(0,0),  //Trial 1 - 5
            new Point(+2,0), 
            new Point(-1,0), 
            new Point(+2,+1), 
            new Point(-1,-2)
        });

        tetrisKickMap.put(4, new Point[]{ // 3|4 -> 0    4
            new Point(0,0),  //Trial 1 - 5
            new Point(+1,0), 
            new Point(-2,0), 
            new Point(+1,-2), 
            new Point(-2,+1),
        }); 
        

    }

}
