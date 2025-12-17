package engine.interactions;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.awt.Point;

/**
 * 
 * @author Camleaf
 */
public class Mouse extends MouseAdapter {
    private Deque<Point> eventStack = new ArrayDeque<Point>();
    private static int yOffset = -32; // To counteract the offset in pixels i've observed on swing's displayadapter on my machine. 
    //                                   When testing on other machine's i'll determine if this is different, and if so how to determine programmatically

    @Override
    public void mousePressed(MouseEvent event) {

        if (event.getButton() == MouseEvent.BUTTON1){ // If left click
            Point point = event.getPoint();
            point.y += yOffset;
            eventStack.offer(point);
        }
    }
    /**
     * Clears the mouse eventDeque that has accumulated since the last clear
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

    /** Peeks the first element in the mouse event buffer
     * @return the first element in the mouse event buffer
     */
    public Point peekEvent(){
        return eventStack.peek();
    }

    /** Peeks the last element in the mouse event buffer
     * @return the last element in the mouse event buffer
     */
    public Point peekLastEvent(){
        return eventStack.peekLast();
    }
}

