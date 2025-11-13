package main.menu.stylized;
import java.awt.Color;
import lib.window.base.EmptyButton;

public class StylizedButton extends EmptyButton {
    public StylizedButton(int width, int height){
        super(width, height);
        setBackground(new Color(0,0,0,0));

        drawBase();
    }

    @Override
    protected void drawBase(){
        gct.drawRect(0,0,100,100,40,40,Color.LIGHT_GRAY);
        // gct.drawRectBorder(0,0,100,100,40,40,Color.LIGHT_GRAY.darker());
        
    }

    @Override
    protected void onHover(){

    };

    @Override
    protected void onExit(){

    };

    @Override
    protected void onClick(){

    };

    @Override
    protected void onRelease(){

    };
    


}
