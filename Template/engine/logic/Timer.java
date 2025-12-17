package engine.logic;

/**
 * Class to manage a non-blocking timer which can act as a delay on inputs
 * @author Camleaf
 */
public class Timer {
    private long savedTime; // The current reference time for the timer
    private int delayMs; // if isEnded is called at a time before savedTime + delayMs, will return false
    public boolean enabled = false; // If not enabled will always return false

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
        this.enable();
    }

    public long getTime(){
        return System.currentTimeMillis();
    }

    public void enable(){
        this.enabled = true;
    }

    public void disable(){
        this.enabled = false;
    }

    // chceks if the timer has ended
    public boolean isEnded(){
        if (!enabled) return false;
        boolean passed = (getTime() - savedTime > delayMs);
        return passed;
    }
}
