package src.main;
import src.window.panels.PlayWindow;
import src.window.Colours;
import java.awt.Color;

import lib.logic.Interval;
import src.main.board.Piece;
import src.main.board.PieceType;
import java.awt.Point;

public class Board extends PlayWindow{
    private Piece currentPiece;
    private Interval rotateInterval;
    private Interval moveInterval;
    private Interval gravityInterval;

    public Board(){
        super(400, 800);
        currentPiece = new Piece(PieceType.S,3,0);
        rotateInterval = new Interval(200);
        moveInterval = new Interval(150);
        gravityInterval = new Interval(600);
        fillBackground();
        displayPiece(currentPiece);
    }


    public void rotatePiece(){
        // Will need to handle kick tables later
        
        if (rotateInterval.intervalPassed()){
            wipePiece(currentPiece);
            currentPiece.rotateCW();
            displayPiece(currentPiece);
        }
    }

    public void movePiece(boolean left){
        if (moveInterval.intervalPassed()){
            if (checkCollideHoriz((left)?-1:1)){
                return;
            }
            wipePiece(currentPiece);
            currentPiece.translateX((left)?-1:1);
            displayPiece(currentPiece);
        }
    }


    public void runGravity(){
        if (gravityInterval.intervalPassed()){
            if (checkCollideVert()){
                return;
            }
            wipePiece(currentPiece);
            currentPiece.translateY();
            displayPiece(currentPiece);
        }
    }


    public boolean checkCollideHoriz(int mov){
        Point ref = currentPiece.getReferencePoint();
        Point[] localPosArr = currentPiece.getLocalPos();

        for (Point p : localPosArr){ // Add checking for other squares once that happens
            int xpos = p.x + ref.x + mov;
            if (!(0<= xpos && xpos < columns)){
                return true;
            }
        }
        return false;
    }
    

    public boolean checkCollideVert(){
        Point ref = currentPiece.getReferencePoint();
        Point[] localPosArr = currentPiece.getLocalPos();

        for (Point p : localPosArr){ // Add checking for other squares once that happens
            int ypos = p.y + ref.y + 1;
            if (!(0<= ypos && ypos < rows)){
                return true;
            }
        }
        return false;
    }


    


}
