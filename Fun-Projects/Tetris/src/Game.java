package src;
import lib.Window;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Interval;
import src.main.Board;
import src.main.HoldArea;

import java.awt.Color;
import src.main.board.SuperRotationSystem;

/**
    * Main intermediary for taking commands from mainloop and distributing to components
    @author CamLeaf
*/
public class Game {
    // No interfacing with swing should be done from this class
    public Window window;
    private Keyboard keyboard;
    private Mouse mouse;
    private Board playWindow;
    private HoldArea holdArea;
    private Interval resetInterval;

    public Game(){
        window = new Window("Tetris", 1000,850);
        window.setBackground(Color.BLACK);
        resetInterval = new Interval(1000);
        playWindow = new Board();
        playWindow.setLocation(300,0);
        window.add(playWindow,Integer.valueOf(1));
        holdArea = new HoldArea();
        holdArea.setLocation(80, 40);
        window.add(holdArea, Integer.valueOf(2));
        playWindow.queue.setLocation(780, 40);
        window.add(playWindow.queue, Integer.valueOf(3));
        
        
    }

    public void show(){
        window.setVisible(true);
        window.repaint();
    }
    public void refresh(){
        window.repaint();
    }

    public void addListeners(Keyboard keyboard, Mouse mouse){
        this.keyboard = keyboard;
        this.mouse = mouse;
    }


    public void update(){


        if (!playWindow.gameOver){
            if (keyboard.isKeyPressed(16)){ // If shift pressed
                playWindow.holdCurrent();
                holdArea.changePiece(playWindow.held);
            }

            if (keyboard.isKeyPressed(38)){ // up arrow pressed
                playWindow.rotatePiece(SuperRotationSystem.ROT_CW); // 1 is cw, -1 is ccw, 2 is 180
            } else if (keyboard.isKeyPressed(87)){ // If w pressed
                playWindow.rotatePiece(SuperRotationSystem.ROT_CCW);
            } else if (keyboard.isKeyPressed(65)){ // key a pressed
                playWindow.rotatePiece(SuperRotationSystem.ROT_180);
            } else {
                playWindow.resetRotHold();
            }

            if (keyboard.isKeyPressed(37)){
            //left
                playWindow.movePiece(true);
            } else {
                playWindow.resetHorizArrowHeld(true);
            }

            if (keyboard.isKeyPressed(39)){
                //right
                playWindow.movePiece(false);
            } else {
                playWindow.resetHorizArrowHeld(false);
            }
        
            if (keyboard.isKeyPressed(40)){
                playWindow.enableSoftDrop();
            } else {
                playWindow.disableSoftDrop();
            }


            if (keyboard.isKeyPressed(32)){
                playWindow.hardDrop();
            } else {
                playWindow.resetHardDrop();
            }



            playWindow.runGravity();

        }

        if (keyboard.isKeyPressed(82)) { // r key pressed
            if (resetInterval.intervalPassed()){
                playWindow.restart();
                holdArea.changePiece(null);
            }
        }
        
        refresh();
        
    }
}
