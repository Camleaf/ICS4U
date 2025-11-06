package main.game.board;

public class Piece {
    public static enum Type {
        PAWN(0), BISHOP(1), KING(2), KNIGHT(3), QUEEN(4), ROOK(5);
        public int id;
        private Type(int index){this.id = index;}
    }
    public static enum Colour {
        WHITE(0),BLACK(1);
        public int id;
        private Colour(int index){this.id = index;}
    }
}
