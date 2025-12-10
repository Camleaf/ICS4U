package main.menu.onLoad;
import lib.window.GraphicsContext;
import lib.window.base.BaseComponent;
import lib.window.base.BasePanel;
import main.menu.stylized.StylizedButton;
import main.menu.stylized.StylizedButtonFactory;
import main.window.Colours;

import java.awt.Font;
import javax.swing.JLayeredPane;
import javax.swing.JTextPane;

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
                    .setLocation(width/2-60,450)   .setDimensions(120, 40, 10, 15)
                    .setText("Enter Game") .setFont(new Font("arial", Font.TRUETYPE_FONT, 20))
                    .setTextMode(GraphicsContext.TEXTMODE_CENTRE) .setTextLocation(60, 20)
                    .build();
        resetButton.addActionListener(gameStart);
        add(resetButton,Integer.valueOf(1));

        // Add in the warning
        JTextPane foreWarnText = new JTextPane();
        foreWarnText.setSize(200,350);
        foreWarnText.setLocation(width/2-230,50);
        foreWarnText.setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 15));
        foreWarnText.setEditable(false);
        foreWarnText.setBackground(Colours.boardWhite);
        add(foreWarnText, Integer.valueOf(1));
        foreWarnText.setText("Welcome to Chess!\n\nChess is a complicated game, and the length of it's rules can't fit in to this project. So, if you don't already know how to play, I highly suggest checking out a site such as chess.com or learningchess.net before you proceed.");

        // add the instructions
        JTextPane textArea = new JTextPane();
        textArea.setSize(200,350);
        textArea.setLocation(width/2+30,50);
        textArea.setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 15));
        textArea.setEditable(false);
        textArea.setBackground(Colours.boardWhite);
        add(textArea, Integer.valueOf(1));
        textArea.setText("Instructions:\n\nThis implementation of chess is best with two people, as it does not feature a bot. In terms of the menu, you can reorient the board, reset it, and see the move history of the current game. The game itself is a full-featured game of chess.");

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