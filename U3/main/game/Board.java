package main.game;

/**
 * Contains the data for the chess game, and all methods which can mutate that data
 * @author Camleaf
 */
public class Board {
    public static enum Pieces {
        PAWN(0), BISHOP(1), KING(2), KNIGHT(3), QUEEN(4), ROOK(5);
        public int id;
        private Pieces(int index){this.id = index;}
    }
    public static enum Colour {
        WHITE(0),BLACK(1);
        public int id;
        private Colour(int index){this.id = index;}
    }
}
