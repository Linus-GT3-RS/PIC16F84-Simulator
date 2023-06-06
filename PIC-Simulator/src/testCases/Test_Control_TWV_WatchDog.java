package testCases;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;

import pic16f84_simulator.backend.control.*;
import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_Control_TWV_WatchDog {

    @Test
    void testWatchDogEduard() { // TODO Eduard WDog
        MC.wdog.start();// -> WatchDog TimeOut occured -> read Console throw Exception
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 3));
    }
    
    @Test
    void testTimerValPossibilities() {
        setup(0, new int[3]); // without Prescaler
        MC.wdog.start(); // timerVal remains in this case whether WDOG is already done or still running
        assertEquals(18, MC.wdog.getWDogTimerVal());
        
        setup(1, new int[] {0,1,1}); // with Prescaler: 1:8
        MC.wdog.start(); // timerVal remains in this case whether WDOG is already done or still running
        assertEquals(144, MC.wdog.getWDogTimerVal());
    }
    
    /*
     * tests different situations when trying to start() WDog
     * 1. WDog not yet running --> no problem
     * 2. WDog already running --> is problem
     */
    @Test
    void testIsRunningFunctionality() throws InterruptedException {
        // 1.
        assertDoesNotThrow(() -> {
            MC.wdog.stop();
            for(int i = 0; i < 100; i++) {
                MC.wdog.start();
                MC.wdog.stop();
                if(MC.wdog.isRunning()) {
                    throw new RuntimeException();
                }
            }
        });
        
        // 2.
        setup(1, new int[] {1,1,1}); // prescaler 1:128
        for(int i = 0; i < 100; i++) {
            MC.wdog.start();        
            while(MC.wdog.isRunning() == false) { Thread.sleep(5); }
            assertThrows(IllegalArgumentException.class, () -> { MC.wdog.start(); });
            MC.wdog.stop();
        }
    }
    
    @Test
    void testStartAfterOverflow() throws InterruptedException {
        setup(1, new int[] {0,1,0}); // w_prescaler
        MC.wdog.start();
        while(MC.wdog.isRunning()) {
            assertThrows(IllegalArgumentException.class, () -> { MC.wdog.start(); });
            Thread.sleep(10);
        } // waiting until WDog overflows
        assertDoesNotThrow(() -> { MC.wdog.start(); });        
    }
    
    @Test
    void testWDogRuntime() throws InterruptedException {
        /*
         *  wout_prescaler
         */
        setup(0, new int[3]); 
        assertEquals(18, getAverage_ms());
        
        setup(0, new int[] {1,1,1});
        assertEquals(18, getAverage_ms());
        
        /*
         *  w_prescaler
         */
        setup(1, new int[] {0,0,0}); 
        assertEquals(18, getAverage_ms());

        setup(1, new int[] {0,1,0});
        assertEquals(72, getAverage_ms());

        setup(1, new int[] {1,0,1});
        assertEquals(576, getAverage_ms());
        
        setup(1, new int[] {1,1,1});
        assertEquals(2_304, getAverage_ms());        
    }
           
    
    private long getAverage_ms() throws InterruptedException {
        System.out.println("Still running");
        long sum_ns = 0;
        int reps = 150;
        for(int i = 0; i < reps; i++) {
            MC.wdog.start();
            while(MC.wdog.isRunning()) { Thread.sleep(10); }
            sum_ns += MC.wdog.debug_lastRuntime();
        }
        long sum_ms = TimeUnit.NANOSECONDS.toMillis(sum_ns);
        return sum_ms / reps;
    }
    
    private void setup(int psa, int[] ps2to0) {
        SFR.setPSA(psa); // assigns prescaler to timer/wdog
        SFR.setPS2To0(ps2to0); // prs 1:x
        MC.wdog.stop();
    }
    
}
