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
    private static int yOffset = -32; // To counteract the offset in pixels i've observed on swing's displayadapter on my machine. 
    //                                   When testing on other machine's i'll determine if this is different, and if so how to determine programmatically
    private static int xOffset = 0; // It looks like no offset but I'll reserve judgement until i look at other devices

    @Override
    public void mousePressed(MouseEvent event) {

        if (event.getButton() == MouseEvent.BUTTON1){ // If left click
            Point point = event.getPoint();
            point.y += yOffset;
            eventStack.offer(point);
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
        return eventStack.peekLast();
    }
}

