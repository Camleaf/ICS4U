package src;
import java.awt.Color;
import java.awt.Point;
import lib.Window;
import lib.Mouse;
import lib.Keyboard;
import lib.graphics.BasePanel;
import src.display.BoardDisplay;
import src.display.menu.VariableMenuDisplay;
import src.display.menu.WaveMenu;

// Alex note here: I feel that the shop should be handled in that if a player clicks an empty tile, it brings up a "would you like to buy a tower menu". It would be much easier to implement

/**
 * @author SpencerM
 * @author Alexcedw
 */
public class Game {
    public Window window;
    private Mouse mouse;
    private Keyboard keyboard;
    private BoardDisplay boardDisplay;
    private VariableMenuDisplay variableMenuDisplay;
    private BasePanel enemyPage;
    private WaveMenu waveMenu;

    public Game() {
        window = new Window("Tower Defense", 832, 600);
        window.setBackground(Color.white);
        
        boardDisplay = new BoardDisplay();
        boardDisplay.setLocation(200,0);
        window.add(boardDisplay, Integer.valueOf(0));
        
        variableMenuDisplay = new VariableMenuDisplay(200,512);
        window.add(variableMenuDisplay, Integer.valueOf(1));

        enemyPage = new BasePanel(boardDisplay.getWidth(),boardDisplay.getHeight(),new Color(0,0,0,0));
        enemyPage.setLocation(200,0);
        enemyPage.setOpaque(false);
        window.add(enemyPage,Integer.valueOf(1));

        waveMenu = new WaveMenu(180,80, boardDisplay);
        window.add(waveMenu, Integer.valueOf(2));

    }
    public void show(){
        window.setVisible(true);
        window.repaint();
    }
    public void refresh(){
        window.repaint();
    }

    public void addEventListeners(Mouse mouse, Keyboard keyboard){
        this.mouse = mouse;
        this.keyboard = keyboard;
    }
     
    public void update(){
        
        Point event = mouse.pollEvent();
        if (event != null){
            // pass clickEvent and get if the board state was updated or not
            Point stateUpdate = boardDisplay.handleClick(event);
            variableMenuDisplay.handleUpdate(stateUpdate, boardDisplay.getTileArray(), boardDisplay);
            // Todo make variable menu Pass data to it to update the status there
            variableMenuDisplay.handleClick(event,boardDisplay.getTileArray());
            
        }
        boardDisplay.update(enemyPage, waveMenu);
        // Run some code idk
        refresh();
    }
}
