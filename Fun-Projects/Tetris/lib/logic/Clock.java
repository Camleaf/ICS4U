package lib.logic;

/**
 * Class to manage iterations of a loop per second, where a given Hz means that the loop runs in intervals of <code>1000ms / Hz</code>
 * @author Camleaf
 */
public class Clock {
    private long savedTime;
    private int Hz;
    private int delayMs;


    public Clock(){
        savedTime = System.currentTimeMillis();
        setHertz(60);
    }


    public Clock(int Hz){
        savedTime = System.currentTimeMillis();
        setHertz(Hz);
    }

     /**
     * Sets the clock's speed to an integer Hz
     * @param Hz the cycles per second of the clock
     */
    public void setHertz(int Hz){
        this.Hz = Hz;
        this.delayMs = 1000 / Hz;
    }

    /**
     * Returns the clock's Hz
     * @return an integer Hz
     */
    public int getHz(){
        return this.Hz;
    }

    /**
     * Sleeps the current thread so that in a infinite loop the thread would run at the clock's stored Hz to an integer precision
     */

    public void tick(){
        savedTime += delayMs;
        long delayTime = savedTime - System.currentTimeMillis();
        if (delayTime > 0){
            try{
                Thread.sleep(delayTime);
            }catch(Exception e){e.printStackTrace();}
            
        } else {
            savedTime = System.currentTimeMillis();
        }
    }
}
