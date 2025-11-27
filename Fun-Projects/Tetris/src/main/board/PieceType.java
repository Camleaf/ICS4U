package src.main.board;
import java.awt.Color;
import java.awt.Point;

public enum PieceType {
    I(0),J(1),L(2),O(3),S(4),T(5),Z(6);

    public int id;
    private PieceType(int id){
        this.id = id;
    }



}
