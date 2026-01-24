package src.display.menu;
import src.logic.Tile;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import lib.graphics.BasePanel;
import java.awt.Color;
import java.awt.Font;
import src.logic.Tower;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.display.BoardDisplay;
import src.display.menu.VariableMenuDisplay;
import src.display.menu.WaveMenu;

// Could move these to new file if gets too long but should be fine for now
/**Submenu that appears when clicking on a tower and contains pertaining options
 * @author SpencerM
 * @author Alexcedw
 */
public class TowerMenu extends BasePanel {
    

    public TowerMenu(int width, int height){
        super(width,height, Color.DARK_GRAY); // will be another color just placeholder for testing

    }

    
    /** 
     * Builds the text and buttons for the tower menu
     * 
     * @param Point click: the coordinates of the tile clicked
     * @param Tile[][] tileArray: the list of all tiles
     */
    public void buildContent(Point click, Tile[][] tileArray, BoardDisplay board, VariableMenuDisplay menu, WaveMenu waveMenu){
        
        Tile towerTile = tileArray[click.y][click.x];
        Tower tower = towerTile.getOccupier();
        JLabel menuName = new JLabel("Tower Menu");
        menuName.setBounds(25, 0, 160, 30);
        menuName.setFont(new Font("arial", Font.TRUETYPE_FONT, 21));
        menuName.setForeground(Color.white);
        
        JLabel name = new JLabel(tower.getName());
        name.setBounds(55, 30, 160, 30);
        name.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        name.setForeground(Color.white);
        
        JTextPane infoText = new JTextPane();
        infoText.setSize(140,180);
        infoText.setLocation(20,80);
        infoText.setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 13));
        infoText.setEditable(false);
        infoText.setBackground(Color.lightGray);
        add(infoText, Integer.valueOf(1));
        infoText.setText(new StringBuilder("")
            .append(String.format("Damage: %s\n", tower.getDamage()))
            .append(String.format("Attacks/s: %.2f\n",1000.0/tower.getAttackDelay()))
            .append(String.format("Max targets: %d\n",tower.getMaxTargets()))
            .append(String.format("Range: %d\n", tower.getRange()))
           // .append("Upgrade Cost:" + tower.baseCost + )
            .toString()
        );
        
        JButton upgrade = new JButton("Upgrade");
        upgrade.setBounds(10, 330, 160, 30);
        upgrade.setFont(new Font("arial", Font.TRUETYPE_FONT, 18));
        upgrade.setBackground(Color.gray);
        upgrade.setForeground(Color.white);
        
        upgrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tower.getUpgradeCost() == null) return;
                if (tower.getUpgradeCost() > board.logic.coins) return;
                board.logic.coins -= tower.getUpgradeCost();

                tower.upgrade();
                board.selectedPoint = null;
                board.refreshTile(tileArray[click.y][click.x]);
                removeAll();
                buildContent(click, tileArray, board, menu, waveMenu);
                waveMenu.updateCoins(board.logic.coins);
            }
            
        });
        JButton destroy = new JButton("Destroy");
        destroy.setBounds(10, 370, 160, 30);
        destroy.setFont(new Font("arial", Font.TRUETYPE_FONT, 18));
        destroy.setBackground(Color.gray);
        destroy.setForeground(Color.red);
        
        destroy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tileArray[click.y][click.x].setOccupier(null);
                board.refreshTile(tileArray[click.y][click.x]);
                removeAll();
                menu.currentStored = null;
                menu.removeAll();
            }
        });
        
        JLabel costLabel = new JLabel("Cost: "+tower.getUpgradeCost()); // have no text to start
        costLabel.setBounds(20,290,160,30);
        costLabel.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        costLabel.setForeground(Color.white);

        add(name);
        add(menuName);
        if (tower.getUpgradeLevel()<2){
            add(costLabel);
            add(upgrade);
        }
        add(destroy);
        add(infoText);

        
    };
    
    
}