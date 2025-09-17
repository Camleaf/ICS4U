import pkg.board.Board;

public class Main {
    Board board;
    public static void main(String[] args) throws Exception{
        Main main = new Main();
    }

    public Main() throws Exception{
        board = new Board();
        board.displayDefense(Board.defensePlaceholder);
        int[][] arr = {{0,0},{0,1},{1,1}};
        board.displayDefense(arr);

        int[][] newShip = {{6,5},{6,6},{6,7}};
        board.createShip(newShip, 3, 0);
        board.displayDefense(arr);
        int[] cast = {6,5};
        board.attack(cast);
        board.displayDefense(arr);
        board.displayOffense();
        // System.out.print("\033\143"); screen clear
    }

    
}
