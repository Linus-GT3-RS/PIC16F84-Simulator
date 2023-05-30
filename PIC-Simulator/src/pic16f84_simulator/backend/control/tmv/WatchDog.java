package pic16f84_simulator.backend.control.tmv;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

@SuppressWarnings("serial")
class WatchdogTimerFinishedException extends RuntimeException{ WatchdogTimerFinishedException(String s){ super(s); }}

public class WatchDog { // A Dog can bite
    
    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class    
    public WatchDog(){
        allow = Utils.allow(allow, this);
    }

    private int std = 18; // standardVal in ms
    private int timer; // timer val to be counted to
    // private boolean on = false; // for toggle function in gui
    
    /*
     * Watchdog timer implementation
     */
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private Runnable task = new Runnable() {
        int counter = 0;
        int timer = MC.tvw.wd.getTimer();
        
        @Override
        public void run() { // System.out.println("Timer tick: " + counter);
            counter++;
            if (counter == timer) { // Timer nach x Wiederholungen beenden
                executor.shutdown();
                throw new WatchdogTimerFinishedException("Watchdog timer has reached zero");
            }
        }
    };
    
    public void startWD() {
        // this.on = true;
        this.timer = this.std;
        if(SFR.getPSA() == 1){
            this.timer *= MC.tvw.ps.getPRS();
        }
        this.executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.MILLISECONDS); // Timer starten, der alle x Zeiteinheiten ausgeführt wird      
    }
    
    public void stopWD() {
        // this.on = false;
        this.executor.shutdownNow();
    }
    
    public int getTimer() {
        return this.timer;
    }
    
    public void toggle() {
        // this.executor.wait();
    }

}
