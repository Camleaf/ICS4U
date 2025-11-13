package lib.window.base;
import javax.swing.JComponent;

import lib.window.GraphicsContext;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A wrapper for the graphicsContext. Overrides rendering from default jButton but doesn't reimplement the functions itself
 */
public class EmptyButton extends JComponent {

    protected GraphicsContext gct;
    protected MouseAdapter mslr;

    public EmptyButton(int width, int height){
        // Intialize graphicsContext
        setLayout(null); 
        setSize(width,height);
        setIgnoreRepaint(true);
        setDoubleBuffered(true);
        gct = new GraphicsContext(width, height);
        createMouseListener();
    }

    @Override
    protected void paintComponent(Graphics g){
        // g.clearRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        gct.paintComponent(g);
        g.dispose();
    };


    private void createMouseListener(){
        mslr = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){onClick();}
            @Override
            public void mouseReleased(MouseEvent e){onRelease();}
            @Override
            public void mouseEntered(MouseEvent e){onHover();}
            @Override
            public void mouseExited(MouseEvent e){onExit();}
        };
        addMouseListener(mslr);
    }

    protected void drawBase(){};
    /**
     * Meant to be overrided. Trigged when mouse hovers over component
     */
    protected void onHover(){};

    /**
     * Meant to be overrided. Trigged when mouse leaves component
     */
    protected void onExit(){};

    /**
     * Meant to be overrided. Triggered when clicked
     */
    protected void onClick(){};

    /**
     * Meant to be overrided. Triggered when click is released
     */
    protected void onRelease(){};

}
