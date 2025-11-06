package lib.interactions;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.awt.Point;

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

    public Point getNextEvent(){
        return eventStack.poll();
    }

    public Point peekNextEvent(){
        return eventStack.peek();
    }
}

