package main.menu;
import lib.window.GraphicsContext;
import lib.window.base.BasePanel;
import main.menu.stylized.StylizedButton;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.util.ArrayList;

public class MenuPanel extends BasePanel{
    ArrayList<JComponent> componentList = new ArrayList<JComponent>();


    public MenuPanel(int width, int height, Color background){
        super(width, height, background);
        createButtons();
    }


    private void createButtons(){
        // This is just testing for now
        StylizedButton b = new StylizedButton(100, 100);
        componentList.add(b);
        b.setCornerArcs(40, 40);
        b.setText("Hello\nHi");
        b.setTextMode(GraphicsContext.TEXTMODE_RIGHT);
        b.setTextLocation(90, 20);

        b.addActionListener(new Runnable(){
            @Override
            public void run(){
                System.out.println(componentList.toString());
            }
        });


        add(b);
    }
    
}
