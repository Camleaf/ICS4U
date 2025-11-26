package lib.interactions;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;


/**
    * Input processor which takes key presses and releases and stores the current status of each keycode inside a integer-boolean map
    @author CamLeaf
*/
public class Keyboard implements KeyListener {
        private Map<Integer, Boolean> keys = new HashMap<Integer, Boolean>();

        @Override public void keyPressed(KeyEvent e) {
            keys.put(e.getKeyCode(),true);
            
        }

        @Override public void keyReleased(KeyEvent e) {
            keys.put(e.getKeyCode(),false);
        }

        @Override public void keyTyped(KeyEvent e) {}
        /**
            * Given an integer keyCode, returns a boolean value corresponding to the pressed status of the related key.
            * @param keyCode An integer keyCode corresponding to Java's KeyCode documentation
            * @return A boolean where true means that the key is pressed and false means the key is not.
        */
        public boolean isKeyPressed(int keyCode){
            return keys.getOrDefault(keyCode,false);
        }
    }