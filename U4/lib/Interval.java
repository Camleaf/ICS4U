package lib;

/**
 * Class to manage a non-blocking interval which can act as a delay on inputs
 * @author Alexcedw
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

    public void resetTime(){
        savedTime = System.currentTimeMillis();
    }

    public long getTime(){
        return System.currentTimeMillis();
    }

    /** Checks if the interval has passed and if true resets it.
     */
    public boolean intervalPassed(){
        boolean passed = peekIntervalPassed();
        if (passed) resetTime();
        return passed;
    }

    /** Checks if the interval has passed without resetting
     */
    public boolean peekIntervalPassed(){
        return (getTime()-savedTime>delayMs);
    }
}