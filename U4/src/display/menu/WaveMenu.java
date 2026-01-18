
package src.display.menu;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLabel;
import lib.graphics.BasePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import src.display.BoardDisplay;


/**Menu that shows live health and wave start
 * @author Alexcedw
 */
public class WaveMenu extends BasePanel {
    private BoardDisplay board;    
    private JLabel hpText;
    private JLabel coinsText;

    public WaveMenu(int width, int height, BoardDisplay board){
        super(width,height, Color.DARK_GRAY);
        setLocation(10,0);
        this.board = board;
        buildContent();
    }

    /** 
     * Builds the text and buttons for the menu
     */
    public void buildContent(){
        endWave();
        
        hpText = new JLabel("HP: 20");
        hpText.setBounds(10, 10, 60, 30);
        hpText.setFont(new Font("arial", Font.TRUETYPE_FONT, 18));
        hpText.setForeground(Color.white);
        add(hpText);
        
        coinsText = new JLabel("Coins: 100");
        coinsText.setBounds(10, 40, 80, 30);
        coinsText.setFont(new Font("arial", Font.TRUETYPE_FONT, 12));
        coinsText.setForeground(Color.white);
        add(coinsText);
    };
   

    public void endWave(){
        JButton startWave = new JButton("<html>Start Wave</html>");
        startWave.setBounds(90, 10, 80, 60);
        startWave.setFont(new Font("arial", Font.TRUETYPE_FONT, 14));
        startWave.setBackground(Color.gray);
        startWave.setForeground(Color.white);
        startWave.setMargin(new Insets(5,5,5,5));
        
        
        startWave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.logic.startWave(board.getPath()); 
                remove(startWave);
            };  
        });



        add(startWave);

    }
   
    public void updateHP(int newHP){
        hpText.setText("HP: "+newHP);
    }


    public void updateCoins(int newCoins){
        coinsText.setText("Coins: "+newCoins);
    }
}







