package lib.logic;

/**
 * Class to manage a non-blocking timer which can act as a delay on inputs
 * @author Camleaf
 */
public class Timer {
    private long savedTime;
    private int delayMs;
    public boolean enabled = false;

    public Timer(int millis){
        savedTime = System.currentTimeMillis();
        setDelay(millis);
    }


    public void setDelay(int millis){
        this.delayMs = millis;
    }


    public int getDelay(){
        return this.delayMs;
    }

    

    public void startTimer(){
        savedTime = System.currentTimeMillis();
        enabled = true;
    }

    public long getTime(){
        return System.currentTimeMillis();
    }

    public void end(){
        this.enabled = false;
    }


    public boolean isEnded(){
        if (!enabled) return false;
        boolean passed = (getTime() - savedTime > delayMs);
        return passed;
    }
}
