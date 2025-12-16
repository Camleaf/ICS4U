package lib.logic;

/**
 * Class to manage a non-blocking interval which can act as a delay on inputs
 * <p>
 * Often less efficient than the Timer and Clock class but easier to implement
 * @author Camleaf
 */
public class Interval {
    private long savedTime;
    private int delayMs;

    public Interval(int millis){
        savedTime = System.currentTimeMillis();
        setInterval(millis);
    }


    public void setInterval(int millis){
        this.delayMs = millis;
    }


    public int getInterval(){
        return this.delayMs;
    }

    private void resetTime(){
        savedTime = System.currentTimeMillis();
    }

    
    public long getTime(){
        return System.currentTimeMillis();
    }

    /**
     * Checks if the interval is passed, if yes, returns true and resets the reference time
     * @return if the interval has passed since last action
     */
    public boolean intervalPassed(){
        boolean passed = (getTime() - savedTime > delayMs);
        if (passed) resetTime();
        return passed;
    }
}
