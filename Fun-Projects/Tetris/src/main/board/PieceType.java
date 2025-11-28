package src.main.board;
import java.awt.Color;
import java.awt.Point;
import java.util.Map;

public enum PieceType {
    I(0),J(1),L(2),O(3),S(4),T(5),Z(6);

    
    public int id;
    public static PieceType[] types = new PieceType[]{
        I,J,L,O,S,T,Z
    };
    public static Map<String,PieceType> StringMap = Map.of(
        "I",I,
        "J",J,
        "L",L,
        "O",O,
        "S",S,
        "T",T,
        "Z",Z
    );
    public Color reg;
    public Color shadow;

    private PieceType(int id){
        this.id = id;
        addColors();
    }
    private void addColors(){
        switch (this){
            case I:
                this.reg = Color.CYAN;
                this.shadow = Color.CYAN.darker().darker();
                break;
            case J:
                this.reg = Color.BLUE;
                this.shadow = Color.BLUE.darker().darker();
                break;
            case L:
                this.reg = Color.ORANGE;
                this.shadow = Color.ORANGE.darker().darker();
                break;
            case O:
                this.reg = Color.YELLOW;
                this.shadow = Color.YELLOW.darker().darker();
                break;
            case S:
                this.reg = Color.GREEN;
                this.shadow = Color.GREEN.darker().darker();
                break;
            case T:
                this.reg = Color.MAGENTA;
                this.shadow = Color.MAGENTA.darker().darker();
                break;
            case Z:
                this.reg = Color.RED;
                this.shadow = Color.RED.darker().darker();
                break;
        }
    }



}
