package src.main.board;
import static src.main.board.PieceType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;

import src.main.board.PieceType;


/**
 * Gets the moves from my srs.txt which hardcodes the basic no interference rotation part of SRS : the tetris standard for rotation.
 * <p>
 * Horrible code here but it should be fine as long as it works
 */
public class SuperRotationSystem {


    public static HashMap<PieceType,Point[][]> load_SRS(){
        HashMap<PieceType,Point[][]> SRS_Map = new HashMap<PieceType,Point[][]>();

        File fl = new File("public/srs.txt"); 
        Scanner scanner;
        try{
            scanner = new Scanner(fl); 
            scanner.nextLine(); // Get rid of the comment i put at top of file
            while (scanner.hasNextLine()) {
                String type = scanner.nextLine().trim();
                Point[][] curSRS = new Point[(!type.equals("O"))?4:1][]; // Contains the stages of SRS

                for (int i = 0;i<curSRS.length;i++){ // Grabs every stage from the srs.txt file
                    ArrayList<Point> SRSline = new ArrayList<Point>();

                    String[] line = scanner.nextLine().trim().split(" ");
                    int r = 0;
                    for (String row : line){
                        String[] rsp = row.split("");
                        for (int j =0; j<rsp.length;j++){
                            String val = rsp[j];
                            if (val.equals("1")){
                                SRSline.add(new Point(j,r));
                            }
                        }
                        r++;
                    }
                    curSRS[i] = SRSline.toArray(new Point[SRSline.size()]);
                }
                SRS_Map.put(PieceType.StringMap.get(type),curSRS);
                scanner.nextLine();
                
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return SRS_Map;
    }

    public HashMap<PieceType,Point[][]> SRS_Map;
    public Point[][] I;
    public Point[][] J;
    public Point[][] L;
    public Point[][] O;
    public Point[][] S;
    public Point[][] T;
    public Point[][] Z;


    public SuperRotationSystem(){
        SRS_Map = load_SRS();
        I = SRS_Map.get(PieceType.I);
        J = SRS_Map.get(PieceType.J);
        L = SRS_Map.get(PieceType.L);
        O = SRS_Map.get(PieceType.O);
        S = SRS_Map.get(PieceType.S);
        T = SRS_Map.get(PieceType.T);
        Z = SRS_Map.get(PieceType.Z);
    }

    public Point[] get(PieceType type, int rot){
        if (type == PieceType.O){
            return SRS_Map.get(type)[0];
        }
        return SRS_Map.get(type)[rot];
    }
}
