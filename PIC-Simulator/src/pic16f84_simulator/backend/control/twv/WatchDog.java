package pic16f84_simulator.backend.control.twv;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

@SuppressWarnings("serial")
class WatchdogTimerFinishedException extends RuntimeException{ WatchdogTimerFinishedException(String s){ super(s); }}

public class WatchDog {// TODO @Linus Tests machen WatchDog
    
    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class    
    public WatchDog(){
        allow = Utils.allow(allow, this);
    }

    private int std = 18; // standardVal in ms
    private int timer; // timer val to be counted to
    // private boolean on = false; // for toggle function in gui
    
    
    public void startWD() {
        // this.on = true;
        this.timer = this.std;
        if(SFR.getPSA() == 1){
            this.timer *= MC.prescaler.getPRS();
        }
        WDThread.executor.scheduleAtFixedRate(WDThread.task, 0, 1, TimeUnit.MILLISECONDS); // Timer starten, der alle x Zeiteinheiten ausgeführt wird      
    }
    
    public void stopWD() {
        // this.on = false;
        WDThread.executor.shutdownNow();
    }
    
    public int getTimer() {
        return this.timer;
    }
    
    public void toggle() {
        // this.executor.wait();
    }

}
