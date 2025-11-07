package lib.interactions;
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

    @Override
    public void mousePressed(MouseEvent event) {

        if (event.getButton() == MouseEvent.BUTTON1){ // If left click
            eventStack.offer(event.getPoint());
        }
    }

    public void clearStack(){
        eventStack.clear();
    }

    public Point pollEvent(){
        return eventStack.poll();
    }

    public Point pollLastEvent(){
        return eventStack.pollLast();
    }

    public Point peekEvent(){
        return eventStack.peek();
    }

    public Point peekLastEvent(){
        return eventStack.peek();
    }
}

