package main.menu.stylized;
import java.awt.Font;
import java.awt.Point;

public class StylizedButtonFactory {
    private int arcWidth;
    private int arcHeight;
    private int width;
    private int height;
    private String value;
    private Font font;
    private int textMode;
    private Point textLocation;
    private Point location;

    public StylizedButtonFactory(){
        arcWidth = 0;
        width = 0;
        height = 0;
        arcHeight = 0;
        value = "";
        font = new Font("Arial", Font.TRUETYPE_FONT, 16);
        textMode = 0;
        textLocation = new Point(0,0);
        location = new Point(0,0);
    }

     public StylizedButtonFactory setTextLocation(Point p){
        this.textLocation = p;
        return this;
    }
    public StylizedButtonFactory setTextLocation(int x, int y){
        this.textLocation = new Point(x,y);
        return this;
    }

    public StylizedButtonFactory setLocation(int x, int y){
        this.location = new Point(x,y);
        return this;
    }

    public StylizedButtonFactory setText(String text){
        this.value = text;
        return this;
    }

    public StylizedButtonFactory setFont(Font font){
        this.font = font;
        return this;
    }

    public StylizedButtonFactory setTextMode(int textMode){
        this.textMode = textMode;
        return this;
    }

    public StylizedButtonFactory setDimensions(int width, int height, int arcw, int arch){
        this.width = width;
        this.height = height;
        this.arcHeight = arch;
        this.arcWidth = arcw;
        return this;
    }
    public StylizedButtonFactory setCornerArcs(int arcw, int arch){
        setDimensions(width, height, arcw, arch);
        return this;
    }

    public StylizedButton build(){
        return new StylizedButton(width, height, arcWidth, arcHeight, value, font, textMode, textLocation, location);
    }
}
