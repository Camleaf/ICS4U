package lib;

/**
 * Class for handling mainloop timing
 * @param Hz the maxmimum hertz (refreshes per second) that the clock allows.
 * @author Alexcedw
 */
public class Clock{
    private long savedTime;
    private int Hz;
    private int delayMs;
    
    public Clock(int Hz){
        savedTime = System.currentTimeMillis();
        setHertz(Hz);
    }
    
    /**
     * Given an integer representing Hertz, converts the Hz into a corresponding delay in milliseconds
     * @param Hz an integer representing Hz per second
     */
    public void setHertz(int Hz){
        this.Hz = Hz;
        this.delayMs = 1000 / Hz;
    }
    
    /**
     * Throttles requests passed to it, and pauses the current thread that it belongs to to keep any loop that it is running at the stored Hz.
     */
    public void tick(){
        savedTime+=delayMs;
        long delayTime = savedTime - System.currentTimeMillis();
        if (delayTime>0){
            try {
                Thread.sleep(delayTime);
            } catch(Exception e){e.printStackTrace();}
        } else {
            savedTime=System.currentTimeMillis();
        }
    }
}