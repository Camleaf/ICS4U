package pkg.display;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;


// Took the boilerplate code from https://stackoverflow.com/questions/51774451/what-is-better-key-listener-or-key-adapter
// made some modifications to better fit my usecase - eg. adding the hashmap to store all key status

public class KeyProcessor implements KeyListener {
        public Map<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
        @Override public void keyPressed(KeyEvent e) {
            keys.put(e.getKeyCode(),true);
            System.out.println(e.getKeyCode());

        }
        @Override public void keyReleased(KeyEvent e) {
            keys.put(e.getKeyCode(),false);
        }

        @Override public void keyTyped(KeyEvent e) {}

        public boolean isKeyPressed(int keyCode){
            /*
             * Given a keyCode, returns a boolean value where true is pressed and false is off
             */
            return keys.getOrDefault(keyCode,false);
        }

        public boolean[] areKeysPressed(int[] keyCodes){
            /*
             * Given an array of keyCodes, returns an array x where x[i] = (true if is keyCodes[i] pressed otherwise false)
             */
            boolean[] arePressed = new boolean[keyCodes.length];
            int idx = 0;
            for (int keyCode : keyCodes){
                arePressed[idx] = isKeyPressed(keyCode);
                idx++;
            }
            return arePressed;
        }
    }