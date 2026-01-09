package lib;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.awt.Point;

/**
 * A class to allow mouse input in our game
 * @author Alexcedw
 */
public class Mouse extends MouseAdapter {
    private Deque<Point> eventStack = new ArrayDeque<Point>();
    private int yOffset = -32; 
    
    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1){
            Point point = event.getPoint();
            point.y += yOffset;
            eventStack.offer(point);
        }
    }
    
    /** clears the mouse event buffer
     */
    public void clearStack(){
        eventStack.clear();
    }
    
    /** Polls the first element in the mouse event buffer
     * @return the first element in the mouse event buffer
     */
    public Point pollEvent(){
        return eventStack.poll();
    }

    /** Polls the last element in the mouse event buffer
     * @return the last element in the mouse event buffer
     */
    public Point pollLastEvent(){
        return eventStack.pollLast();
    }
}
