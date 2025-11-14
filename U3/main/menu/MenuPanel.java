package main.menu;
import lib.window.GraphicsContext;
import lib.window.base.BasePanel;
import main.menu.stylized.StylizedButton;
import main.menu.stylized.StylizedButtonFactory;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.HashMap;
import static main.game.board.Piece.Colour.*;
import static main.menu.MenuOption.*;

public class MenuPanel extends BasePanel{
    private ArrayList<JComponent> componentList = new ArrayList<JComponent>();
    private HashMap<MenuOption,Integer> options = new HashMap<MenuOption,Integer>();


    public MenuPanel(int width, int height, Color background){
        super(width, height, background);
        intializeOptionsValues();
        createButtons();
    }


    private void createButtons(){
        // Button that changes orientation. A lot of boilerplate but it works
        StylizedButton orientationButton = new StylizedButtonFactory() // Ah factory methods. Probably was super overkill but I love it
                    .setLocation(20,450)   .setDimensions(100, 40, 10, 15)
                    .setText("Flip Board") .setFont(new Font("arial", Font.TRUETYPE_FONT, 20))
                    .setTextMode(GraphicsContext.TEXTMODE_CENTRE) .setTextLocation(50, 20)
                    .build();

        componentList.add(orientationButton);
        orientationButton.addActionListener(new Runnable() {
            public void run(){
                options.put(OPTIONS_BOARD_ORIENT,(getOption(OPTIONS_BOARD_ORIENT)==WHITE.id) ? BLACK.id : WHITE.id);
            }
        });
        add(orientationButton);


        // Reset Board
        StylizedButton resetButton = new StylizedButtonFactory() 
                    .setLocation(130,450)   .setDimensions(120, 40, 10, 15)
                    .setText("Reset Board") .setFont(new Font("arial", Font.TRUETYPE_FONT, 20))
                    .setTextMode(GraphicsContext.TEXTMODE_CENTRE) .setTextLocation(60, 20)
                    .build();

        componentList.add(resetButton);
        resetButton.addActionListener(new Runnable() {
            public void run(){
                options.put(TRIGGER_BOARD_RESET,TRIGGER_TRUE);
            }
        });
        add(resetButton);


    }

    private void intializeOptionsValues(){
        options.put(OPTIONS_BOARD_ORIENT,WHITE.id);
        options.put(TRIGGER_BOARD_RESET, TRIGGER_FALSE);


    }

    public int getOption(MenuOption optionKey){
        return options.getOrDefault(optionKey, -1);
    }

    public void setOption(MenuOption optionKey, int val){
        options.put(optionKey, val);
    }

    
}
