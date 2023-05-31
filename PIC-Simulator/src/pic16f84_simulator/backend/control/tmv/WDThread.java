package pic16f84_simulator.backend.control.tmv;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import pic16f84_simulator.MC;

abstract class WDThread {
    
    /*
     * Watchdog timer implementation
     */
    static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    static Runnable task = new Runnable() {
        int counter = 0;
        int timer = MC.wdog.getTimer();
        
        @Override
        public void run() { // System.out.println("Timer tick: " + counter);
            counter++;
            if (counter == timer) { // Timer nach x Wiederholungen beenden
                executor.shutdown();
                throw new WatchdogTimerFinishedException("Watchdog timer has reached zero");
            }
        }
    };

}
