package src.main.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Uses 7bag to randomly distribute pieces
public class SevenBag {
    private int bagIndex = 0;
    private PieceType[] bag;

    public SevenBag(){
        makeNewBag();
    }

    public void makeNewBag(){
        bag =PieceType.types.clone();
        shuffleArray(bag);
    }

    public PieceType pollNext(){
        PieceType polled = bag[bagIndex];
        bagIndex+=1;
        if (bagIndex == 7){
            bagIndex = 0;
            makeNewBag();
        }
        return polled;
    }


    private static void shuffleArray(PieceType[] array) {
        List<PieceType> list = new ArrayList<>();
        for (PieceType i : array) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
    }
}   

