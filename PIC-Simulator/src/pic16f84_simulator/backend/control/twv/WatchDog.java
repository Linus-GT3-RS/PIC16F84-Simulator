package pic16f84_simulator.backend.control.twv;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
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

    private static long debug_start;
    private static long debug_lastRuntime; // in ns

    // private boolean on = false; // for toggle function in gui
    
    
    public void start() { // this.on = true;
        if(this.isRunning == true) {
            throw new IllegalArgumentException("WDog is already running.. cannot start a second one");
        }
        setIsRunning(true);
        this.wdogTimerVal = this.std;
        if(SFR.getPSA() == 1){
            this.wdogTimerVal *= MC.prescaler.getPRS(1);
        }  
        debug_start = System.nanoTime();
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
            debug_lastRuntime = System.nanoTime() - debug_start;
            timer.cancel(); // so thread can terminate after following code is run
            watchDogTimeOut();
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
    
    public static void watchDogTimeOut(){
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 3, 0);
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 4, 1);
        MC.control.pc(0);
        MC.ram.otherReset();
        // System.out.println("Watchdog timer has overflowed"); 

        
    }
    
    
    
    /*
     * ------------------------------ Getter & Setter -------------------------------------
     */
    public int getWDogTimerVal() {
        return this.wdogTimerVal;
    }
    
    // When SLEEP or CLRWDT
    public void clearPRS() {
        if(MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4) == 1) { // case 0 -> TMR
            MC.prescaler.clearPRS();
        }
         
    }

    void setIsRunning(boolean val) {
        this.isRunning = val;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public long debug_lastRuntime() {
        return debug_lastRuntime;
    }

    //    public void toggle() {
    //        // this.executor.wait();
    //    }    

}
