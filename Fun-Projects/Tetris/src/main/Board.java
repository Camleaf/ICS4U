package src.main;
import src.window.panels.PlayWindow;
import src.window.Colours;
import java.awt.Color;

import lib.logic.Interval;
import src.main.board.GridSquare;
import src.main.board.Locks;
import src.main.board.Piece;
import src.main.board.PieceType;
import src.main.board.SevenBag;
import java.awt.Point;


public class Board extends PlayWindow{
    private Piece currentPiece;
    private Interval moveInterval;
    private Interval gravityInterval;
    private GridSquare[][] grid;
    private SevenBag bag;
    private final int gravityTime = 600;
    private final int firstMoveTime = 133;
    private boolean hardDropHeld = false;
    private Locks gravityLocks = new Locks();
    private boolean instantPlace = false;
    private boolean rotHold = false;
    private int rotPrev = -1;

    public Board(){
        super(400, 800);
        bag = new SevenBag();
        newPiece();
        moveInterval = new Interval(firstMoveTime);
        gravityInterval = new Interval(gravityTime);
        displayPiece(currentPiece);
        emptyBoard();
    } 

    public void emptyBoard(){
        grid = new GridSquare[rows][columns];
        for (int i = 0 ; i < rows; i++){
            for (int j = 0 ; j<columns;j++){
                grid[i][j] = new GridSquare(false, null);
            }
        }
        paintBackground();
    }


    public void newPiece(){
        currentPiece = new Piece(bag.pollNext(), 3, 0);
    }


    public void placePiece(){
        Point[] localPosArr = currentPiece.getLocalPos();
        Point ref = currentPiece.getReferencePoint();

        for (Point localPos : localPosArr){
            int xpos = localPos.x + ref.x;
            int ypos = localPos.y + ref.y;
            grid[ypos][xpos] = new GridSquare(true, currentPiece.getType().reg);
        }
        newPiece();
        gravityLocks.end();
        checkFullLines();
    }


    public void rotatePiece(int rotMode){ // 1 is cw, -1 is ccw, 2 is 180
        // Will need to handle kick tables later
        if (rotPrev == rotMode){
            if (rotHold){
                return;
            }
        }
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
            gravityLocks.resetLock(2);
            rotHold = true;
            rotPrev = rotMode;
            break;
        }
    }

    public void resetRotHold(){
        rotPrev = -1;
        rotHold = false;
    }

    public void movePiece(boolean left){

        if (moveInterval.intervalPassed()){
            
            if (checkCollideHoriz((left)?-1:1)){
                return;
            }
            gravityLocks.resetLock(1);

            wipePiece(currentPiece);
            currentPiece.translateX((left)?-1:1);
            displayPiece(currentPiece);
        }

        
    }

    public void enableSoftDrop(){
        gravityInterval.setInterval(50);
    }

    public void disableSoftDrop(){
        gravityInterval.setInterval(gravityTime);
    }

    public void runGravity(){
        if (gravityLocks.enabled){
            checkLocks();
        }
        if (gravityInterval.intervalPassed()){
            if (checkCollideVert()){
                if (!gravityLocks.enabled){
                    gravityLocks.start();
                }
                if (instantPlace){
                    placePiece();
                    gravityLocks.end();
                    instantPlace = false;
                }
                return;
            }
            wipePiece(currentPiece);
            currentPiece.translateY();
            displayPiece(currentPiece);
        }
    }

    private void checkLocks(){
        if (gravityLocks.isAnyPassed()){
            instantPlace = true;
            gravityLocks.end();
        }
    }


    public void hardDrop(){
        if (hardDropHeld){return;}
        wipePiece(currentPiece);
        while(!checkCollideVert()){
            currentPiece.translateY();
        }
        displayPiece(currentPiece);
        placePiece();
        gravityLocks.end();
        hardDropHeld = true;
    }

    public void resetHardDrop(){
        hardDropHeld = false;
    }


    public boolean checkCollideHoriz(int mov){
        Point ref = currentPiece.getReferencePoint();
        Point[] localPosArr = currentPiece.getLocalPos();

        for (Point p : localPosArr){ // Add checking for other squares once that happens
            int xpos = p.x + ref.x + mov;
            int ypos = p.y + ref.y;
            if (!(0<= xpos && xpos < columns) ||isFilled(xpos, ypos)){
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
            int xpos = p.x + ref.x;
            if (ypos>=rows){
                return true;
            }
            if (isFilled(xpos, ypos)){
                return true;
            }
        }
        return false;
    }

    public boolean isFilled(int sqX, int sqY){
        return grid[sqY][sqX].filled;
    }

    public boolean checkCollide(Point ref, Point[] localPosArr){ // pass in custom reference
        for (Point p : localPosArr){
            int ypos = p.y + ref.y;
            int xpos = p.x + ref.x;
            
            if (!(0<= ypos && ypos < rows)||!(0<= xpos && xpos < columns)||isFilled(xpos, ypos)){ // checking for borders. Still need to do other squares
                return true;
            }
        }

        return false;
    }



    public void checkFullLines(){ // checks for and removes filled lines
        int idx = 0;
        GridSquare[][] newGrid = new GridSquare[rows][columns];

        for (int p = 0;p<grid.length;p++){
            GridSquare[] line = grid[grid.length-p-1];
            int filled = 0;
            for (GridSquare square :line){
                if (square.filled)filled++;
            }
            if (filled != line.length){
                newGrid[grid.length-idx-1] = line;
                idx++;
            }
        }


        for (int jdx = 0;jdx<newGrid.length-idx;jdx++){
            
            newGrid[jdx] = new GridSquare[columns];
            for (int l = 0;l<newGrid[jdx].length;l++){
                newGrid[jdx][l] = new GridSquare(false, null);
            }
        }
        grid = newGrid;
        paintFullGrid(grid);
    }



}
