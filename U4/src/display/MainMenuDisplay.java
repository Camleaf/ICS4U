package src.display;
import lib.graphics.BasePanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainMenuDisplay extends BasePanel {
    
    public MainMenuDisplay(int width, int height){
        super(width, height, Color.lightGray);
        

        buildMenu();
    }

    public void buildMenu(){ 
        JLabel textLabel = new JLabel();
        textLabel.setText("Main Menu goes here");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setBounds(0,0,160,30);
        textLabel.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        textLabel.setForeground(Color.BLACK);
        add(textLabel);
    }
}
