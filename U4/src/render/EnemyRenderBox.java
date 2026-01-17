package src.render;
import src.render.BoardRenderer;    
import javax.swing.JComponent;
import src.logic.Enemy;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import lib.graphics.Texture;
import java.awt.image.BufferedImage;


// Acts as a box for an individual enemy to be displayed. Does not contain any individual enemy
public class EnemyRenderBox extends JComponent {
    
    private static Texture texture = new Texture("public/Enemy_Spritesheet.png",256,256); 
    private BufferedImage image;
    private static int squareSize = BoardRenderer.squareSize;
    private static int spriteSheetSquareSize = BoardRenderer.spriteSheetSquareSize;


    public EnemyRenderBox(Enemy reference){
        setSize(64,64);
        setOpaque(false);
        updateGraphicsToRef(reference);
    };

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public static BufferedImage indexTexture(int index){
        int row = (int) Math.floor(index/4);
        int col = index%4;
        return texture.getSubImage(col*spriteSheetSquareSize,row*spriteSheetSquareSize,spriteSheetSquareSize,spriteSheetSquareSize);
    }



    public void updateGraphicsToRef(Enemy reference){
        image = indexTexture(reference.health); 
    }

    public void setLocationToRef(Enemy reference){
        setLocation(reference.x*squareSize,reference.y*squareSize);
    }




    


    
}

