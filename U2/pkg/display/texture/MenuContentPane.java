package pkg.display.texture;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Image;

public class MenuContentPane extends JPanel{
    public Image backgroundImage;
    public MenuContentPane(){
        backgroundImage = new ImageIcon("src/menu.png").getImage();
        backgroundImage = backgroundImage.getScaledInstance(700,700,Image.SCALE_DEFAULT);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the background
        // Your drawing code for the background image goes here
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
