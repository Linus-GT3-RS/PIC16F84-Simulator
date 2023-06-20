package pic16f84_simulator.backend.control.twv;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.controller.ButtonInteraction;

public class WatchDog {

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class    
    public WatchDog(){
        allow = Utils.allow(allow, this);
    }

    private Thread t;
    //private java.util.Timer timer;

    private int std = 18; // standardVal in ms
    private int wdogTimerVal; // timer val to be counted to
    private boolean noThread = true; // ensures that only one WDog is running 

    private static long debug_start;
    private static long debug_lastRuntime; // in ns
    
    
<<<<<<< HEAD
    public void start() { // this.on = true;
        if(noThread) {
            noThread = false;
            this.wdogTimerVal = this.std * MC.prescaler.getPRS(1);;
            startTimerThread();
=======
    public void start() {
        if(this.isRunning == true) {
            throw new IllegalArgumentException("WDog is already running.. cannot start a second one");
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
        }
    }

    /*
     * creates a new Timer in background
     *  - after set time(= getTimer()) the run() is executed --> entering means WDog overflowed
     */
    private void startTimerThread() {
        
        t = new Thread() {
            @Override
            public void run() {
                while(ButtonInteraction.timer < getWDogTimerVal()) {
                }
                watchDogTimeOut();
             // EXCEPTION // NEW WINDOW
                System.out.println("WatchDog Timeout!");
                t.stop();
            }
        };
    }


<<<<<<< HEAD
    public void stop() { // this.on = false;
        if(noThread == false) {
            t.stop();
            noThread = true;
=======
    public void stop() {
        if(isRunning()) {
            timer.cancel();
            setIsRunning(false);   
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
        } 
    }
    
    public static void watchDogTimeOut(){
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 3, 0);
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 4, 1);
        MC.control.pc(0);
<<<<<<< HEAD
        MC.ram.otherReset(); 
=======
        MC.ram.otherReset();
        // System.out.println("Watchdog timer has overflowed"); 
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
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

    public long debug_lastRuntime() {
        return debug_lastRuntime;
<<<<<<< HEAD
    }   
=======
    }    
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git

}
