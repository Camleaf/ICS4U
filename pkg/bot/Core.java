package pkg.bot;
import pkg.bot.DefenseModule;
import pkg.board.Board;
import pkg.board.Ship;
public class Core {
    DefenseModule defenseModule;

    public Core(){
    }
    public void createShips(Board board){
        for (int i=0;i<5;i++){
            DefenseModule.createShip(Ship.lengths[i],board,i);
        }
        
    }
}
