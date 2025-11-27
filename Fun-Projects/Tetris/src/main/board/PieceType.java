package src.main.board;
import java.awt.Color;
import java.awt.Point;
import java.util.Map;

public enum PieceType {
    I(0),J(1),L(2),O(3),S(4),T(5),Z(6);

    public int id;
    public static Map<String,PieceType> StringMap = Map.of(
        "I",I,
        "J",J,
        "L",L,
        "O",O,
        "S",S,
        "T",T,
        "Z",Z
    );
    private PieceType(int id){
        this.id = id;
    }



}
