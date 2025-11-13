package main.menu;
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
        StylizedButton b = new StylizedButton(100, 100);
        b.setCornerArcs(40, 40);
        add(b);
    }
    
}
