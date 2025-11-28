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
        currentPiece = new Piece(PieceType.I,3,0);
        rotateInterval = new Interval(200);
        moveInterval = new Interval(150);
        gravityInterval = new Interval(600);
        fillBackground();
        displayPiece(currentPiece);
    } //PIECES ARE RENDERING BACKWARDS WHY


    public void rotatePiece(int rotMode){ // 1 is cw, -1 is ccw, 2 is 180
        // Will need to handle kick tables later
        
        if (rotateInterval.intervalPassed()){
            Point[] kickSet = currentPiece.getNewKickSet(rotMode);
            Point ref = currentPiece.getReferencePoint();
            // Now that i have the kickset we need to apply kicks
            Point[] nextLocalPos = currentPiece.getLocalPos(rotMode); // the rotated local pos
            for (Point kick : kickSet){
                
                if (checkCollide(new Point(ref.x+kick.x,ref.y+kick.y),nextLocalPos)){
                    continue;
                } 
                // If kick found do rotation
                 // get updated reference from kicksv
                wipePiece(currentPiece);
                currentPiece.setReferencePoint(ref.x+kick.x, ref.y+kick.y);
                ref = currentPiece.getReferencePoint();
                currentPiece.rotate(rotMode);
                displayPiece(currentPiece);
                break;
            }
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


    public boolean checkCollide(Point ref, Point[] localPosArr){ // pass in custom reference
        for (Point p : localPosArr){
            int ypos = p.y + ref.y;
            int xpos = p.x + ref.x;
            
            if (!(0<= ypos && ypos < rows)||!(0<= xpos && xpos < columns)){ // checking for borders. Still need to do other squares
                return true;
            }

        }

        return false;
    }





}
