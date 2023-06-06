package pic16f84_simulator.backend.control.twv;
import java.util.TimerTask;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class WatchDog {

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class    
    public WatchDog(){
        allow = Utils.allow(allow, this);
    }

    private java.util.Timer timer;

    private int std = 18; // standardVal in ms
    private int wdogTimerVal; // timer val to be counted to
    private boolean isRunning = false; // ensures that only one WDog is running 

    private static long start;
    private static long lastRuntime; // in ns

    // private boolean on = false; // for toggle function in gui


    public void start() { // this.on = true;
        if(this.isRunning == true) {
            throw new IllegalArgumentException("WDog is already running.. cannot start a second one");
        }
        setIsRunning(true);
        this.wdogTimerVal = this.std;
        if(SFR.getPSA() == 1){
            this.wdogTimerVal *= MC.prescaler.getPRS();
        }  
        start = System.nanoTime();
        startTimerThread();
    }

    /*
     * creates a new Timer in background
     *  - after set time(= getTimer()) the run() is executed --> entering means WDog overflowed
     */
    private void startTimerThread() {
        timer = new java.util.Timer();        
        timer.schedule(new TimerTask() {    

            @Override         
            public void run() { //System.out.println("WDog overflow"); 
            lastRuntime = System.nanoTime() - start;
            timer.cancel(); // so thread can terminate after following code is run

            // Eduards WDog reset hier

            MC.wdog.setIsRunning(false); // now WDog is allowed to run again
            }        

        }, this.wdogTimerVal); 
    }


    public void stop() { // this.on = false;
        if(isRunning()) {
            timer.cancel();
            setIsRunning(false);   
        } 
    }


    /*
     * ------------------------------ Getter & Setter -------------------------------------
     */
    public int getWDogTimerVal() {
        return this.wdogTimerVal;
    }

    void setIsRunning(boolean val) {
        this.isRunning = val;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public long lastRuntime() {
        return lastRuntime;
    }

    //    public void toggle() {
    //        // this.executor.wait();
    //    }    

}
