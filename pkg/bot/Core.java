package pkg.bot;
import pkg.bot.DefenseModule;
import pkg.bot.AttackModule;
import pkg.board.Board;
import pkg.board.Ship;
public class Core {
    DefenseModule defenseModule;
    int difficulty;

    public Core(int difficulty){
        this.difficulty = difficulty;
    }
    public void createShips(Board board){
        for (int i=0;i<5;i++){
            DefenseModule.createShip(Ship.lengths[i],board,i);
        }
        
        
    }
    public int[] attack(Board board){
        return AttackModule.getAttackCoordinate(board, this.difficulty); //temporary
    }
}
