package src.main.board;
import static src.main.board.PieceType.*;

import java.awt.Point;

import src.main.board.SuperRotationSystem;

public class Piece {
    private SuperRotationSystem srs;
    private KickTable kickTable;
    private Point referencePos;
    private Point shadowReferencePos;
    private Point[] currentLocalPos; // The local position relative to the referencePos
    private int rotation = 0;
    private PieceType type;

    public Piece(PieceType type, int sqX, int sqY){
        this.type = type;
        referencePos = new Point(sqX,sqY);
        srs = new SuperRotationSystem();
        kickTable = new KickTable();
        updateSRS();
    }

    public void rotate(int rotMode){
        switch (rotMode){
            case SuperRotationSystem.ROT_CCW:
                if (rotation == 0){
                    rotation = 3; // not actually setting a new rotation here.
                } else {
                    rotation = rotation -1;
                }
                
                break;
            case SuperRotationSystem.ROT_180:
                rotation = rotation + 2;
                break;
            default:
                rotation = rotation +1;
                break;
        }
        rotation %= 4; // Just so that the rotation count stays below 4
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

    public Point getShadowReferencePoint(){
        return shadowReferencePos;
    }

    public void setShadowReferencePoint(int sqX,int sqY){
        this.shadowReferencePos = new Point(sqX, sqY);
    }


    public Point[] getLocalPos(int rotMode){
        // gets local pos for a given rot
        int newRotation;
        switch (rotMode){
            case SuperRotationSystem.ROT_CCW:
                if (rotation == 0){
                    newRotation = 3;
                }
                else {newRotation = rotation - 1;}
                break;
            case SuperRotationSystem.ROT_CW:
                newRotation = rotation + 1;
                break;
            case SuperRotationSystem.ROT_180:
                newRotation = rotation + 2;
                break;
            default:
                newRotation = rotation;
        }
        newRotation %= 4;

        return srs.get(type,newRotation);
    }


    public void setReferencePoint(int sqX,int sqY){
        this.referencePos = new Point(sqX, sqY);
    }

    public PieceType getType(){
        return this.type;
    }

    public Point[] getNewKickSet(int rotMode){ // Gets the kickset for a given rotation type
        boolean counterClockwise;
        int newRotation;
        switch (rotMode){
            case SuperRotationSystem.ROT_CCW:
                counterClockwise = true;
                if (rotation == 0){
                    newRotation = 3; // not actually setting a new rotation here.
                } else {
                    newRotation = rotation -1;
                }
                
                break;
            case SuperRotationSystem.ROT_180:
                counterClockwise = false;
                newRotation = rotation + 2;
                break;
            default:
                counterClockwise = false;
                newRotation = rotation +1;
                break;
        }
        newRotation %= 4;
        return kickTable.get(rotation, newRotation, this.type, counterClockwise);
    }


}
