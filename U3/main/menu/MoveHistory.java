package main.menu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Font;
import main.game.board.Piece;
import main.window.Colours;
import java.awt.Dimension;

public class MoveHistory extends JTextPane {

    private String txt;
    private Piece.Colour turn = Piece.Colour.WHITE;
    public JScrollPane scrollPane;
    private int moveCount;
    public static final String[] letters = new String[]{"a","b","c","d","e","f","g","h"};

    public MoveHistory(){ // so close just got to make it scroll
        setLocation(0,0);
        setSize(230,300);
        setBackground(Colours.boardWhite);
        setEditable(false);
        setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 20));
        scrollPane = new JScrollPane(this);
        scrollPane.setSize(230,300);
        scrollPane.setLocation(25,140);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));


        resetHistory();
    }

    public void addMove(String move){
        if (move.length() == 2){move += "   ";}
        if (move.length() == 3){move += "  ";}
        if (move.length() == 4){move += " ";}

        if (turn == Piece.Colour.WHITE){
            txt += String.format(" %s",move);
        } else {
            moveCount += 1;
            txt += String.format("  %s\n%d.",move,moveCount);
            
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
        int number = 8-dest.y;
        String move = "";

        if (from.getType()==Piece.Type.KING && Math.abs(from.x-dest.x)==2){
            if (from.x-dest.x < 0){
                return "0-0";
            } else {
                return "0-0-0";
            }   
        }

        move += getStringSig(from);

        // case take                               // en passant
        if (dest.getType() != Piece.Type.EMPTY || (from.getType()==Piece.Type.PAWN && Math.abs(from.x-dest.x) == 1)){ // case take
            if (move.equals("")){
                move += letters[from.x];
            }
            if (from.getType()==Piece.Type.PAWN && Math.abs(from.x-dest.x) == 1){
                move += String.format("x%s%d",letter,number+((from.getColour()==Piece.Colour.WHITE)?-1:1));
            } else {
                move += String.format("x%s%d",letter,number);
            }
        } else{ // case notake

            move += letter + String.format("%d",number);
        }

        return move;

    }
}
