package main.menu;

import javax.swing.JTextPane;
import java.awt.Font;
import main.game.board.Piece;
import main.window.Colours;

public class MoveHistory extends JTextPane {

    private String txt;
    private Piece.Colour turn = Piece.Colour.WHITE;
    private int moveCount;
    public static final String[] letters = new String[]{"a","b","c","d","e","f","g","h"};

    public MoveHistory(){ // so close just got to make it scroll
        setLocation(40,40);
        setSize(200,300);
        setBackground(Colours.boardWhite);
        setEditable(false);
        setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 20));
        resetHistory();
    }

    public void addMove(String move){
        if (move.length() == 2){move += "  ";}
        if (move.length() == 3){move += " ";}

        if (turn == Piece.Colour.WHITE){
            txt += String.format(" %s",move);
        } else {
            moveCount += 1;
            txt += String.format("   %s\n%d.",move,moveCount);
            
        }
        turn = turn.getInverse();
        setText(txt);
    }


    public void resetHistory(){
        moveCount = 1;
        txt = String.format("%d.",moveCount);
        turn = Piece.Colour.WHITE;
        setText(txt);
    }

    public String getStringSig(Piece pc){
        switch (pc.getType()){
            case PAWN:
                return "";
            case BISHOP:
                return "B";
            case KNIGHT:
                return "N";
            case KING:
                return "K";
            case QUEEN:
                return "Q";
            case ROOK:
                return "R";
            default:
                return "";
        }
    }

    public String createMove(Piece from, Piece dest){
        String letter = letters[dest.x];
        String number = String.format("%d",8-dest.y);
        String move = "";

        move += getStringSig(from);

        // case no take
        if (dest.getType() == Piece.Type.EMPTY){
            move += letter + number;
        } else { // case take
            if (move.equals("")){
                move += letters[from.x];
            }
            move += String.format("x%s%s",letter,number);
        }

        return move;

    }
}
