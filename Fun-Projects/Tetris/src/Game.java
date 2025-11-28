package src;
import lib.Window;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Interval;
import src.main.Board;
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

    public Game(){
        window = new Window("Tetris", 800,850);
        window.setBackground(Color.WHITE);
        playWindow = new Board();
        window.add(playWindow,Integer.valueOf(1));
        
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
        // Some example functions
        mouse.peekEvent();

        if (keyboard.isKeyPressed(27)){
            System.out.println("hello");
        };

        if (keyboard.isKeyPressed(38)){ // up arrow pressed
            playWindow.rotatePiece(SuperRotationSystem.ROT_CW); // 1 is cw, -1 is ccw, 2 is 180
        }

        if (keyboard.isKeyPressed(37)){
           //left
           playWindow.movePiece(true);
        } else {
            playWindow.resetMoveInterval();
        }
        if (keyboard.isKeyPressed(39)){
            //right
            playWindow.movePiece(false);
        } else {
            playWindow.resetMoveInterval();
        }
        if (keyboard.isKeyPressed(40)){
           playWindow.enableSoftDrop();
        } else {
            playWindow.disableSoftDrop();
        }
        playWindow.runGravity();

        
        refresh();
    }
}
