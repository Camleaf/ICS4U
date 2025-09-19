package U1.pkg.bot;
import U1.pkg.board.Board;
import U1.pkg.board.Ship;
import U1.pkg.bot.AttackModule;
import U1.pkg.bot.DefenseModule;
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
