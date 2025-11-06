package lib.interactions.mouse;
import java.awt.Point;

public class ClickEvent {
    private Point pos;

    public ClickEvent(Point pos){
        this.pos = new Point(pos.x,pos.y);
    }

    public Point getPos(){return pos;}
}
