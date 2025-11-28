package src.main;
import src.window.panels.PlayWindow;
import src.window.Colours;
import java.awt.Color;

import lib.logic.Interval;
import lib.logic.Timer;
import src.main.board.GridSquare;
import src.main.board.Locks;
import src.main.board.Piece;
import src.main.board.PieceType;
import src.main.board.SevenBag;
import java.awt.Point;


public class Board extends PlayWindow{
    private Piece currentPiece;
    private Timer leftMoveTimer;
    private Timer rightMoveTimer;
    private Interval autoRepeatInterval;
    private Interval gravityInterval;
    private GridSquare[][] grid;
    private SevenBag bag;
    private final int gravityTime = 600; // All times are in milliseconds
    private final int firstMoveTime = 133;
    private final int softDropTime = 20;
    private final int autoRepeatRate = 20;
    private boolean hardDropHeld = false;
    private Locks gravityLocks = new Locks();
    private boolean instantPlace = false;
    private boolean rotHold = false;
    private int rotPrev = -1;
    public boolean gameOver = false;
    public PieceType held;
    private boolean holdEnabled = true;
    public Queue queue = new Queue();
    private boolean leftArrowHeld = false;
    private boolean rightArrowHeld = false;



    
    public Board(){
        super(400, 800);
        bag = new SevenBag();
        emptyBoard();
        newPiece();
        leftMoveTimer = new Timer(firstMoveTime);
        rightMoveTimer = new Timer(firstMoveTime);
        autoRepeatInterval = new Interval(autoRepeatRate);
        gravityInterval = new Interval(gravityTime);
        paintPiece(currentPiece);

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





    public void holdCurrent(){
        if (!holdEnabled){return;}
        wipePiece(currentPiece, currentPiece.getShadowReferencePoint());
        wipePiece(currentPiece);
        if (held==null){
            held = currentPiece.getType();
            newPiece();   
        } else {
            PieceType temp = currentPiece.getType();
            currentPiece = new Piece(held, 3, -1);
            held = temp;
            
        }
        calculateShadow();
        paintPiece(currentPiece,currentPiece.getType().shadow,currentPiece.getShadowReferencePoint());
        paintPiece(currentPiece);
        holdEnabled = false;
    }





    public void restart(){
        gameOver = false;
        rotPrev = -1;
        rotHold = false;
        instantPlace = false;
        held = null;
        holdEnabled = true;
        bag = new SevenBag();
        emptyBoard();
        newPiece();
    }






    public void newPiece(){

        currentPiece = new Piece(bag.pollNext(), 3, -1);
        queue.updateQueue(bag.getQueue());
        calculateShadow();
        paintPiece(currentPiece,currentPiece.getType().shadow,currentPiece.getShadowReferencePoint());


    }








    public void placePiece(){
        Point[] localPosArr = currentPiece.getLocalPos();
        Point ref = currentPiece.getReferencePoint();

        for (Point localPos : localPosArr){
            int xpos = localPos.x + ref.x;
            int ypos = localPos.y + ref.y;
            if (ypos < 0){
                gameOver = true;
                return;
            }
            grid[ypos][xpos] = new GridSquare(true, currentPiece.getType().reg);
        }
        newPiece();
        gravityLocks.end();
        checkFullLines();
        holdEnabled = true;
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
            //wipe
            wipePiece(currentPiece);
            wipePiece(currentPiece,currentPiece.getShadowReferencePoint());
            // switch up vars
            currentPiece.setReferencePoint(ref.x+kick.x, ref.y+kick.y);
            ref = currentPiece.getReferencePoint();
            currentPiece.rotate(rotMode);
            // do new paints
            calculateShadow();
            paintPiece(currentPiece,currentPiece.getType().shadow,currentPiece.getShadowReferencePoint());

            paintPiece(currentPiece);
            gravityLocks.resetLock(2);
            gravityLocks.resetLock(1);
            rotHold = true;
            rotPrev = rotMode;
            break;
        }
    }






    public void resetRotHold(){
        rotPrev = -1;
        rotHold = false;
    }





 
    //Finds the reference point for the current shadow to be at the ground level
    public void calculateShadow(){
        Point shadowPoint = new Point(currentPiece.getReferencePoint());
        while(!checkCollideVert(shadowPoint)){
            shadowPoint.y += 1;
        }
        currentPiece.setShadowReferencePoint(shadowPoint.x, shadowPoint.y);
    }






    public void movePiece(boolean left){
        if ((left && leftArrowHeld) ){
            if (!leftMoveTimer.enabled){
                leftMoveTimer.startTimer();
                return;
            }else if (!leftMoveTimer.isEnded()){
                return;
            } else {
                if (!autoRepeatInterval.intervalPassed()){
                    return;
                }
            }
        } else if ((!left)&&rightArrowHeld){
            if (!rightMoveTimer.enabled){
                rightMoveTimer.startTimer();
                return;
            } else if (!rightMoveTimer.isEnded()){
                return;
            } else {
                if (!autoRepeatInterval.intervalPassed()){
                    return;
                }
            }
        }
 

        if (checkCollideHoriz((left)?-1:1)){
            return;
        }
        gravityLocks.resetLock(1);

        wipePiece(currentPiece);
        wipePiece(currentPiece,currentPiece.getShadowReferencePoint());

        currentPiece.translateX((left)?-1:1);

        calculateShadow();
        paintPiece(currentPiece,currentPiece.getType().shadow,currentPiece.getShadowReferencePoint());
        paintPiece(currentPiece);
        if (left){
            leftArrowHeld = true;
        } else {
            rightArrowHeld = true;
        }
    }

    public void resetHorizArrowHeld(boolean left){
        if (left){
            leftArrowHeld = false;
            leftMoveTimer.end();
        } else {
            rightArrowHeld = false;
            rightMoveTimer.end();
        }
    }



    public void enableSoftDrop(){
        gravityInterval.setInterval(softDropTime);
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
            paintPiece(currentPiece);
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
            if (!(0<= xpos && xpos < columns)){
                return true;
            }
            if (ypos >= 0){
                if (isFilled(xpos, ypos)){
                    return true;
                }
            }
        }
        return false;
    }
    

    public boolean checkCollideVert(){
        return checkCollideVert(currentPiece.getReferencePoint());
    }

    public boolean checkCollideVert(Point ref){
        Point[] localPosArr = currentPiece.getLocalPos();

        for (Point p : localPosArr){ // Add checking for other squares once that happens
            int ypos = p.y + ref.y + 1;
            int xpos = p.x + ref.x;
            if (ypos>=rows){
                return true;
            }
            if (ypos >= 0){
                if (isFilled(xpos, ypos)){
                    return true;
                }
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
            
            if (!(ypos < rows)||!(0<= xpos && xpos < columns)){ // checking for borders. Still need to do other squares
                return true;
            }
            if (ypos >= 0){
                if (isFilled(xpos, ypos)){
                    return true;
                }
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

        calculateShadow();
        paintPiece(currentPiece,currentPiece.getType().shadow,currentPiece.getShadowReferencePoint());
    }



}
