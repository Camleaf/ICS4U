package src.main.board;
import static src.main.board.PieceType.*;

import java.awt.Point;

import src.main.board.SuperRotationSystem;

public class Piece {
    private SuperRotationSystem srs;
    private Point referencePos;
    private Point[] currentLocalPos; // The local position relative to the referencePos
    private int rotation = 0;
    private PieceType type;

    public Piece(PieceType type, int sqX, int sqY){
        this.type = type;
        referencePos = new Point(sqX,sqY);
        srs = new SuperRotationSystem();
        updateSRS();
    }

    public void rotateCW(){
        rotation += 1;
        rotation %= 4; // Just so that it doesn't get too big probably not an issue though
        updateSRS();
    }

    public void translateX(int mov){
        referencePos.x += mov;
    }

    public void translateY(){
        referencePos.y += 1;
    }

    private void updateSRS(){
        currentLocalPos = srs.get(type,rotation);
    }

    public Point[] getLocalPos(){
        return currentLocalPos;
    }

    public Point getReferencePoint(){
        return referencePos;
    }

    public PieceType getType(){
        return this.type;
    }


}
