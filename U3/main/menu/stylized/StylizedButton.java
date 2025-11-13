package main.menu.stylized;
import java.awt.Color;
import lib.window.base.EmptyButton;

public class StylizedButton extends EmptyButton {

    protected int arcWidth;
    protected int arcHeight;

    public StylizedButton(int width, int height){
        super(width, height);
        this.arcHeight = 0;
        this.arcWidth = 0;
        setBackground(new Color(0,0,0,0));
        drawBase();
    }




    public void setDimensions(int width, int height, int arcw, int arch){
        setSize(width,height);
        this.arcHeight = arch;
        this.arcWidth = arcw;
        gct.refresh();
        drawBase();
    }
    public void setCornerArcs(int arcw, int arch){
        setDimensions(getWidth(), getHeight(), arcw, arch);
    }


    @Override
    protected void drawBase(){
        gct.drawRect(0,0,getWidth(),getHeight(),this.arcWidth,this.arcHeight,Color.LIGHT_GRAY);
    }

    @Override
    protected void onHover(){
        gct.refresh();
        gct.drawRect(0,0,getWidth(),getHeight(),this.arcWidth,this.arcHeight,Color.LIGHT_GRAY.darker());
    };

    @Override
    protected void onExit(){
        drawBase();
    };

    @Override
    protected void onClick(){

    };

    @Override
    protected void onRelease(){

    };
    


}
