package src.display.menu;
import lib.graphics.BasePanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

/**
 * displays the screen that appears when the player loses
 * @Author Spencer M
 */
public class EndScreen extends BasePanel {
    //private JLabel endText;
    
   
   public EndScreen(int width, int height) {
       super(width, height, Color.black);
       buildScreen();
   }
   
   /**
    * Builds the screen that displays on a game over
    */
    public void buildScreen() {
        JLabel endText = new JLabel("Game Over!");
        endText.setBounds(250, 100, 250, 250);
        endText.setForeground(Color.white);
        endText.setFont(new Font("arial", Font.TRUETYPE_FONT, 41));
        
        add(endText);
    } //
    
}