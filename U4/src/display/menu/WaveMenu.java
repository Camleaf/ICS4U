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
import src.display.menu.EndScreen;
import lib.Window;

/**Menu that shows live health and wave start
 * @author Alexcedw
 * @author SpencerM
 */
public class WaveMenu extends BasePanel {
    private BoardDisplay board;    
    private JLabel hpText;
    private JLabel coinsText;
    private Window window;
    private boolean gameOver;
    private EndScreen endScreen = new EndScreen(10000, 10000);
    
    public WaveMenu(int width, int height, BoardDisplay board, Window window){
        super(width,height, Color.DARK_GRAY);
        this.gameOver = false;
        this.window = window;
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
   
    /**
     * ends this wave
     */
    public void endWave(){
        if (gameOver == true) {
            return;
        }
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
    /**
     * updates the players health
     */
    public void updateHP(int newHP){
        if (gameOver == true) {
            return;
        }
        if (newHP < 1) {
            window.add(endScreen,Integer.valueOf(1000));
            gameOver = true;
        }
        
        hpText.setText("HP: "+newHP);
    }


    public void updateCoins(int newCoins){
        coinsText.setText("Coins: "+newCoins);
    }
}