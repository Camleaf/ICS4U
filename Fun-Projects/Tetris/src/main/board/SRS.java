package src.main.board;
import static src.main.board.PieceType.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;

import src.main.board.PieceType;

public class SRS {


    public static HashMap<PieceType,Point[][]> load_SRS(){
        HashMap<PieceType,Point[][]> SRS_Map = new HashMap<PieceType,Point[][]>();

        File fl = new File("public/srs.txt"); 
        Scanner scanner;
        try{
            scanner = new Scanner(fl); 
            scanner.nextLine(); // Get rid of the comment i put at top of file
            while (scanner.hasNextLine()) {

                String type = scanner.nextLine().trim();

                Point[][] curSRS = new Point[(type!="O")?4:1][]; // Contains the stages of SRS

                for (int i = 0;i<curSRS.length;i++){ // Grabs every stage from the srs.txt file

                    String[] line = scanner.nextLine().trim().split(" ");
                    
                }

            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap<PieceType,Point[][]> SRS_Map = load_SRS();

    public static void get_SRS(PieceType type){
        switch (type){
            case L:
            case T:
            case S:
            case Z:
        }
    }
}
