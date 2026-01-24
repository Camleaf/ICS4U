package src.render;
import src.render.BoardRenderer;    
import javax.swing.JComponent;
import src.logic.Enemy;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import lib.graphics.Texture;
import java.awt.image.BufferedImage;
import lib.Interval;

// Acts as a box for an individual enemy to be displayed. Does not contain any individual enemy
public class EnemyRenderBox extends JComponent {
    
    private static Texture texture = new Texture("public/Enemy_Spritesheet.png",256,256); 
    private BufferedImage image;
    private static int squareSize = BoardRenderer.squareSize;
    private static int spriteSheetSquareSize = BoardRenderer.spriteSheetSquareSize;
    private int steps = 8;
    private int movDelay;
    private int stepDist = squareSize/steps;
    public Interval stepInterval = null;
    private Point stepVector= new Point(0,0);

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
        if (reference.health<=0){return;} // don't bother handling below 0 cases because object is abt to get deleted anyway
        image = indexTexture(reference.health-1); 
    }

    public void setLocationToRef(Enemy reference){
        setLocation(reference.x*squareSize,reference.y*squareSize);
    }
    
    public void setMovDelay(int ms){
        movDelay = ms/steps;
        stepInterval = new Interval(movDelay);
    }

    public void setStepVector(int x, int y){
        stepVector= new Point(x,y);
        stepInterval.resetTime();
    }


    public void step(){
        if (stepInterval == null) return;
        if (!stepInterval.intervalPassed()) return;
        setLocation(getX()+stepDist*stepVector.x,getY()+stepDist*stepVector.y); 
        
    }





    


    
}
