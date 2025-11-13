package main.menu.stylized;
import java.awt.Color;
import java.awt.event.MouseEvent;
import lib.window.GraphicsContext;
import lib.window.base.EmptyButton;
import main.window.Colours;
import java.awt.Point;
import java.awt.Font;

public class StylizedButton extends EmptyButton {

    protected int arcWidth;
    protected int arcHeight;
    protected String value;
    protected Font font;
    protected int textMode;
    protected Point textLocation;

    public StylizedButton(int width, int height){
        super(width, height);
        this.arcHeight = 0;
        this.arcWidth = 0;
        this.value = "";
        this.font = new Font("Arial", Font.TRUETYPE_FONT, 16);
        this.textMode = GraphicsContext.TEXTMODE_CENTRE;
        this.textLocation = new Point(width/2,height/2);
        setBackground(new Color(0,0,0,0));
        drawBase();
    }

    public void setTextLocation(Point p){
        this.textLocation = p;
        drawBase();
    }
    public void setTextLocation(int x, int y){
        this.textLocation = new Point(x,y);
        drawBase();
    }

    public void setText(String text){
        this.value = text;
        drawBase();
    }

    public void setFont(Font font){
        this.font = font;
        drawBase();
    }

    public void setTextMode(int textMode){
        this.textMode = textMode;
        drawBase();
    }

    public void setDimensions(int width, int height, int arcw, int arch){
        setSize(width,height);
        this.arcHeight = arch;
        this.arcWidth = arcw;
        drawBase();
    }
    public void setCornerArcs(int arcw, int arch){
        setDimensions(getWidth(), getHeight(), arcw, arch);
    }


    @Override
    protected void drawBase(){
        gct.refresh();
        gct.drawRect(0,0,getWidth(),getHeight(),this.arcWidth,this.arcHeight,Colours.boardWhite);
        drawText();

    }


    protected void drawText(){
        gct.drawText(value,textLocation.x,textLocation.y, Color.BLACK,textMode);
    }

    @Override
    protected void onHover(MouseEvent e){
        gct.refresh();
        gct.drawRect(0,0,getWidth(),getHeight(),this.arcWidth,this.arcHeight,Colours.boardBlack.brighter());
        drawText();
    };

    @Override
    protected void onExit(MouseEvent e){
        drawBase();
    };

    @Override
    protected void onRelease(MouseEvent e){
        if (getBounds().contains(e.getPoint())){
            onHover(e);
        } else drawBase();

    };

    @Override
    protected void onPress(MouseEvent e){
        gct.refresh();
        gct.drawRect(0,0,getWidth(),getHeight(),this.arcWidth,this.arcHeight,Colours.boardHighlight);
        drawText();
        runActionListeners();
    };
    


}
