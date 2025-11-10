package main;
import main.game.BoardDisplay;
import main.game.board.PromoteDisplay;
import lib.Window;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import java.awt.Point;
/**
    * Main intermediary for taking commands from mainloop and distributing to components
    @author CamLeaf
*/
public class Game {
    // No interfacing with swing should be done from this class
    public Window window;
    private BoardDisplay board;
    private Keyboard keyboard;
    private Mouse mouse;

    public Game(){
        window = new Window("Chess", 800,640);
        board = new BoardDisplay(512);
        window.add(board,Integer.valueOf(1));
        window.add(board.pawnPromoteDisplay,Integer.valueOf(2));
        /*
         * Screen will be 800 x 800
         */
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


    public void updateBoard(){

        // Take the last input in the stored clicks as that is the most recent datapoint. 
        // Most humans can't even click fast enough consistently to get a double click in 60fps, much less with enough accuracy to intentionally move pieces around at that speed
        Point clickPoint = mouse.peekLastEvent();
        if (clickPoint != null){
            board.handleMouseClick(clickPoint);
        }
    }
}
