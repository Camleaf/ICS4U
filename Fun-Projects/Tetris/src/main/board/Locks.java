package src.main.board;
import lib.logic.Timer;

// L1, L2, and L3 locks when dropping 
public class Locks {
    private Timer L1;
    private Timer L2;
    private Timer L3;
    public boolean enabled = false;

    public Locks(){
        L1 = new Timer(500);
        L2 = new Timer(5000);
        L3 = new Timer(20000);
    }

    public void start(){
        enabled = true;
        L1.startTimer();
        L2.startTimer();
        L3.startTimer();
    }

    public void resetLock(int lock){
        switch (lock){
            case 2:
                L2.startTimer();
                break;
            case 3:
                L3.startTimer();
                break;
            default:
                L1.startTimer();
                break;
        }
    }

    public void end(){
        enabled = false;
    }

    public boolean isAnyPassed(){
        if (!enabled){return false;}
        if (L1.isEnded() || L2.isEnded() || L3.isEnded()){
            enabled = false;
            return true;
        }
        return false;
    }

}
