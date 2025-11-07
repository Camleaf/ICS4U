package main.game.board;

public class Piece {
    public static enum Type {
        EMPTY(-1), PAWN(0), BISHOP(1), KING(2), KNIGHT(3), QUEEN(4), ROOK(5);
        public int id;
        private Type(int index){
            this.id = index;
        }
    }


    public static enum Colour {
        NONE(-1), WHITE(0),BLACK(1);
        public int id;
        private Colour(int index){this.id = index;}
    }


    public int x;
    public int y;
    private Type type;
    private Colour colour;
    private Boolean hasMoved;

    public Piece(int x, int y, Type type, Colour colour){
        this.x = x;
        this.y = y;
        this.type = type;
        this.colour = colour;
        this.hasMoved = false;
    }

    public Piece(int x, int y, Type type, Colour colour, boolean hasMoved){
        this.x = x;
        this.y = y;
        this.type = type;
        this.colour = colour;
        this.hasMoved = hasMoved;
    }

    public Type getType(){
        return this.type;
    }

    public Colour getColour(){
        return this.colour;
    }


}
