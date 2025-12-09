package main.menu.onLoad;
import lib.window.GraphicsContext;
import lib.window.base.BaseComponent;
import lib.window.base.BasePanel;
import main.menu.stylized.StylizedButton;
import main.menu.stylized.StylizedButtonFactory;
import java.awt.Font;

import javax.swing.JLayeredPane;

import java.awt.Color;

public class Initial extends JLayeredPane{
    public Initial(int width, int height, Runnable gameStart){

        setOpaque(false);
        setLayout(null); 
        setSize(width,height);
        setIgnoreRepaint(true);
        setBackground(new Color(0,0,0,0));
        setDoubleBuffered(true);
        add(new Background(width, height),Integer.valueOf(0));
        addComponents(width,height, gameStart);
    }

    private void addComponents(int width, int height, Runnable gameStart){
        StylizedButton resetButton = new StylizedButtonFactory() 
                    .setLocation(width/2-60,400)   .setDimensions(120, 40, 10, 15)
                    .setText("Enter Game") .setFont(new Font("arial", Font.TRUETYPE_FONT, 20))
                    .setTextMode(GraphicsContext.TEXTMODE_CENTRE) .setTextLocation(60, 20)
                    .build();
        resetButton.addActionListener(gameStart);
        add(resetButton,Integer.valueOf(1));
        repaint();
    }
}

class Background extends BaseComponent{
    public Background(int width, int height){
        super(width,height);
        setOpaque(false);
        gct.fill(new Color(0,0,0,200));
    }
}