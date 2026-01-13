package src.display.menu;
import src.logic.Tile;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLabel;
import lib.graphics.BasePanel;
import java.awt.Color;
import java.awt.Font;
import src.logic.Tower;

// Could move these to new file if gets too long but should be fine for now
/**Submenu that appears when clicking on a tower and contains pertaining options
 * @author SpencerM
 * @author Alexcedw
 */
public class TowerMenu extends BasePanel {
    

    public TowerMenu(int width, int height){
        super(width,height, Color.DARK_GRAY); // will be another color just placeholder for testing

    }

    /**To Implement
     */
    public void handleClick(Point pos, Tile[][] tileArray){
        
    };

    
    /** 
     * Builds the text and buttons for the tower menu
     * 
     * @param Point click: the coordinates of the tile clicked
     * @param Tile[][] tileArray: the list of all tiles
     */
    public void buildContent(Point click, Tile[][] tileArray){
        
        Tile towerTile = tileArray[click.y][click.x];
        Tower tower = towerTile.getOccupier();
        JLabel menuName = new JLabel("Tower Menu");
        menuName.setBounds(25, 0, 160, 30);
        menuName.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        menuName.setForeground(Color.white);
        
        JLabel stats = new JLabel("Stats: ");
        stats.setBounds(60, 60, 160, 30);
        stats.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        stats.setForeground(Color.white);
        
        JLabel damage = new JLabel("Damage: " + tower.getDamage());
        damage.setBounds(10, 100, 160, 30);
        damage.setFont(new Font("arial", Font.TRUETYPE_FONT, 18));
        damage.setForeground(Color.white);
        
        JButton upgrade = new JButton("Upgrade");
        upgrade.setBounds(10, 360, 160, 30);
        upgrade.setFont(new Font("arial", Font.TRUETYPE_FONT, 18));
        upgrade.setBackground(Color.gray);
        upgrade.setForeground(Color.white);
        
        JButton destroy = new JButton("Destroy");
        destroy.setBounds(10, 400, 160, 30);
        destroy.setFont(new Font("arial", Font.TRUETYPE_FONT, 18));
        destroy.setBackground(Color.gray);
        destroy.setForeground(Color.red);
        
        add(menuName);
        add(stats);
        add(upgrade);
        add(destroy);
        add(damage);
        
    };
}







