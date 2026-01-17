package src.display.menu;
import src.logic.Tile;
import java.awt.Point;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import lib.graphics.BasePanel;
import src.render.BoardRenderer;
import src.logic.Tower;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.display.BoardDisplay;
import javax.swing.JTextPane;
import src.display.menu.VariableMenuDisplay;


/**Submenu that appears when clicking on an empty tile and contains pertaining options
 * @author Alexcedw
 */
public class EmptyMenu extends BasePanel {
    private TowerSelector towerSelector;
    private JTextPane infoText;
    public EmptyMenu(int width, int height){
        super(width,height, Color.darkGray); // will be another color just placeholder for

    }

    /**To Implement
     */
    public void handleClick(Point clickPos, Tile[][] tileArray){
        Rectangle bounds = getBounds(); // Normalize to panel
        Point normalizedPosition = new Point(
            (clickPos.x-bounds.x),
            (clickPos.y-bounds.y)
        );
        
        if (bounds.contains(clickPos)){
            towerSelector.handleClick(normalizedPosition, tileArray);
            buildTowerData();
        }
        
    };

    
    /** To implement
     */
    public void buildContent(Point click, Tile[][] tileArray, BoardDisplay board, VariableMenuDisplay menu){
 
        JLabel textLabel = new JLabel();
        textLabel.setText("Buy Menu");
        textLabel.setBounds(0,0,160,30);
        textLabel.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        textLabel.setForeground(Color.white);
        add(textLabel);
        
        
        JButton confirmButton = new JButton();
        confirmButton.setText("Buy");
        confirmButton.setBounds(20,370,120,40);
        confirmButton.setFont(new Font("arial", Font.TRUETYPE_FONT, 20));
        confirmButton.setBackground(Color.black);
        confirmButton.setForeground(Color.white);
        add(confirmButton);
        
        towerSelector = new TowerSelector(64*2,64*2);
        towerSelector.setLocation(20,40);
        towerSelector.generateOptionsScreen();
        add(towerSelector);
        
        // Add confirm button
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Run action on buy. If successful revert to default menu state and execute rerender
                if (towerSelector.selectedPoint == null) return;
                int towerIndex = towerSelector.selectedPoint.y*2 + towerSelector.selectedPoint.x;
                Tile temp = tileArray[click.y][click.x];
                temp.setOccupier(Tower.getTowerFromType(Tower.types[towerIndex]));
                board.selectedPoint = null;
                board.refreshTile(temp);
                menu.removeAll();
            }
        });
        
        infoText = new JTextPane();
        infoText.setSize(140,150);
        infoText.setLocation(20,180);
        infoText.setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 12));
        infoText.setEditable(false);
        infoText.setBackground(Color.lightGray);
        add(infoText, Integer.valueOf(1));
        infoText.setText(new StringBuilder("Click on a tower to see stats and cost")
            .toString()
        );
    };
    

    private void buildTowerData(){
        if (towerSelector.selectedPoint == null) { // if null reset
            infoText.setText(new StringBuilder("Click on a tower to see stats and cost")
                .toString()
            );
            return;
        }
        // If not null get the tower and data for it
        int towerIndex = towerSelector.selectedPoint.y*2 + towerSelector.selectedPoint.x;
        Tower tower = Tower.getTowerFromType(Tower.types[towerIndex]);
        infoText.setText(new StringBuilder("")
            .append(String.format("Name: %s\n",tower.getName()))
            .append(String.format("Damage: %s\n", tower.getDamage()))
            .append(String.format("Attacks/s: %.2f\n",1000.0/tower.getAttackDelay()))
            .append(String.format("Max targets: %d\n",tower.getMaxTargets()))
            .append(String.format("Range: %d\n", tower.getRange()))
            .toString()
        );
    
    }
}





class TowerSelector extends BoardRenderer {
    public Point selectedPoint = null;
    
    public TowerSelector(int width, int height){
        super(width,height);
    }
    
    
    public void handleClick(Point clickPos, Tile[][] tileArray){
        Rectangle bounds = getBounds(); // get bounds for location checking
        
        if (!bounds.contains(clickPos)){ // Reset highlight if clicked on menu but not directly
            selectedPoint = null;
            generateOptionsScreen();
            return;
        }
        // Normalize to the towerselector
        Point normalizedPosition = new Point(
            (clickPos.x-bounds.x) / squareSize,
            (clickPos.y-bounds.y) / squareSize
        );
        int towerIndex = normalizedPosition.y*2 + normalizedPosition.x;
        
        // If clicked on a not implemented spot reset
        if (towerIndex >= Tower.types.length){
            selectedPoint = null;
            generateOptionsScreen();
            return;
        }
        
        selectedPoint = normalizedPosition;
        
        generateOptionsScreen();
        
    }
    
    
    public void generateOptionsScreen(){
        
        int position = 0;
        for (Tower.Type type : Tower.types){
            Point tempPoint = new Point(position%2,position/2);
            Tile temp = new Tile(position%2, tempPoint);
            temp.setOccupier(Tower.getTowerFromType(type));
            
            // Bit messy but it works
            if (selectedPoint == null){ // If null then draw regularly 
                drawTile(temp);
                // if selected draw highlight
            } else if (tempPoint.x == selectedPoint.x && tempPoint.y == selectedPoint.y) drawTile(temp,1);       
            else drawTile(temp); // If not the right point draw regularly
             
            position++;
        }
    };
}


















