package main.game.board;
import main.game.board.Piece;
import java.awt.Point;

public class Attacker {
    public Piece pc;
    public int magnitude;
    public Point vector;

    public Attacker(Piece pc, int magnitude, Point vector){
        this.pc = pc;
        this.magnitude = magnitude;
        this.vector = vector;
    }
}
