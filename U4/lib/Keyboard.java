package lib;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
// Alex message: I fixed some bugs to let this compile because I wanted to test something.

/**
 * A class to allow keyboard input in our game
 * @author SpencerM
 * @author Alexcedw
 */
public class Keyboard implements KeyListener {
    private HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
    
    
    /**
     * returns the map of the keys that have been pressed
     * @params null
     * @returns Map: the keys and whether they were pressed or released
     */
    public HashMap returnKeys() {
        return keys;
    }
    
    /**
     * adds the pressed key's key code to the map
     * @ params eventListener e: the key that's pressed
     * @ return null
     */
     
    @Override public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }
    
    /**
     * adds the released key's code to the map
     * @params eventListener e: the key that's released
     * @return null
     */
     
    @Override public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
    }
    
    @Override public void keyTyped(KeyEvent e){} // Leave empty, just to let the KeyListener Interface compile
    
    /**
     * @param keyCode An integer keyCode corresponding to Java's KeyCode documentation
     * @return A boolean where true means that the key is pressed and false means the key is not.
     */
    public boolean isKeyPressed(int keyCode) {
        return keys.getOrDefault(keyCode, false);
    }
    
    
}