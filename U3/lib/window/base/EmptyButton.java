package lib.window.base;
import javax.swing.JComponent;

import lib.window.GraphicsContext;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * A wrapper for the graphicsContext. Overrides rendering from default jButton but doesn't reimplement the functions itself
 */
public class EmptyButton extends JComponent {

    protected GraphicsContext gct;
    protected MouseAdapter mslr;
    protected ArrayList<Runnable> actionListeners = new ArrayList<Runnable>();

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

    /**
     * Will override current drawn buffer
     */

    public void updateSize(int width, int height){
        setSize(width, height);
        gct.setSize(width, height);
    }


    private void createMouseListener(){
        mslr = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){onPress(e);}
            @Override
            public void mouseClicked(MouseEvent e){onClick(e);}
            @Override
            public void mouseReleased(MouseEvent e){onRelease(e);}
            @Override
            public void mouseEntered(MouseEvent e){onHover(e);}
            @Override
            public void mouseExited(MouseEvent e){onExit(e);}
        };
        addMouseListener(mslr);
    }

    public void addActionListener(Runnable action){
        actionListeners.add(action);
    }

    public void removeActionListener(Runnable action){
        actionListeners.remove(action);
    }
    public void removeAllActionListeners(){
        actionListeners = new ArrayList<Runnable>();
    }

    protected void runActionListeners(){
        for (Runnable action : actionListeners){
            action.run();
        }
    }

    protected void drawBase(){};
    /**
     * Meant to be overrided. Trigged when mouse hovers over component
     */

    protected void onClick(MouseEvent e){};

    protected void onHover(MouseEvent e){};

    /**
     * Meant to be overrided. Trigged when mouse leaves component
     */
    protected void onExit(MouseEvent e){};

    /**
     * Meant to be overrided. Triggered when clicked
     */
    protected void onPress(MouseEvent e){};

    /**
     * Meant to be overrided. Triggered when click is released
     */
    protected void onRelease(MouseEvent e){};

}
