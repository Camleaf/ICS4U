package src.main.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Uses 7bag to randomly distribute pieces
public class SevenBag {
    private int bagIndex = 0;
    private PieceType[] bag;
    private PieceType[] nextBag; // 7 after this. Mainly used for getting an accurate queue

    public SevenBag(){
        makeNewBag();
    }

    public void makeNewBag(){

        if (nextBag == null){
            nextBag = PieceType.types.clone();
            shuffleArray(nextBag);
        }

        bag = nextBag.clone();
        nextBag = PieceType.types.clone();
        shuffleArray(nextBag);
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

    public PieceType[] getQueue(){ // gets the 5 next pieces
        PieceType[] queuePieces = new PieceType[5];
        for (int i = 0;i<5;i++){
            int queryIdx = i+bagIndex;
            if (queryIdx >= 7){
                queryIdx %=7;
                queuePieces[i] = nextBag[queryIdx];
                continue;
            }
            queuePieces[i] = bag[queryIdx];
        }
        return queuePieces;
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

