package main;
import main.game.BoardDisplay;
import lib.Window;
import lib.interactions.Keyboard;
import lib.interactions.Mouse;
import lib.logic.Interval;
import main.menu.MenuPanel;
import java.awt.Color;
import java.awt.Point;
import static main.menu.MenuOption.*;

/**
    * Main intermediary for taking commands from mainloop and distributing to components
    @author CamLeaf
*/
public class Game {
    // No interfacing with swing should be done from this class
    public Window window;
    private BoardDisplay board;
    private MenuPanel menu;
    private Keyboard keyboard;
    private Mouse mouse;
    private Interval boardFlipInterval;

    public Game(){
        boardFlipInterval = new Interval(250);
        window = new Window("Chess", 800,560);
        board = new BoardDisplay(512);
        menu = new MenuPanel(288, 560, null);
        menu.setLocation(512, 0);

        window.add(menu,Integer.valueOf(1));
        window.add(board,Integer.valueOf(2));
        window.add(board.pawnPromoteDisplay,Integer.valueOf(3));
        window.setBackground(Color.DARK_GRAY);
        
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

        if ((keyboard.isKeyPressed(27) && boardFlipInterval.intervalPassed()) || menu.getOption(OPTIONS_BOARD_ORIENT)!=board.orientation.id){
            board.switchOrientation();
            menu.setOption(OPTIONS_BOARD_ORIENT,board.orientation.id);
        }


        if (menu.getOption(TRIGGER_BOARD_RESET)==TRIGGER_TRUE){
            board.reset();
            menu.setOption(TRIGGER_BOARD_RESET,TRIGGER_FALSE);
        }
    }
}
