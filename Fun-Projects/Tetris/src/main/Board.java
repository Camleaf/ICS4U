package src.main;
import src.window.panels.PlayWindow;
import src.window.Colours;
import java.awt.Color;

public class Board extends PlayWindow{
    public Board(){
        super(400, 800);
        fillBackground();
        drawSquare(4, 4, Color.RED);
    }
}
