package testCases.other;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_Control_TWV_Timer {

    @Test
    void testDelayAndNoPrescaler() {
        setup(0, 1, new int[3], new int[8]); // internalClock select | assign Prescaler to WDOG | irrelv. | irrelv.
        
        MC.ram.writeDataCell(SFR.TMR0.asIndex(), new int[] {0,0,0,0,0,0,0,1}); // to activate delay by 2
        MC.timer.tryIncrInternalTimer(); // no change in tmr0
        MC.timer.tryIncrInternalTimer(); // no change in tmr0
        MC.timer.tryIncrInternalTimer(); // tmr0 gets incr
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,0}, MC.ram.readDataCell(SFR.TMR0.asIndex()));
    }
    
    @Test
    void testTimerWithPrescaler() {
        setup(0, 0, new int[] {0,0,1}, new int[] {0,0,0,0, 0,0,0,1});
        
        MC.timer.tryIncrInternalTimer(); assertArrayEquals(new int[] {0,0,0,0, 0,0,0,1}, MC.ram.readDataCell(SFR.TMR0.asIndex()));
        MC.timer.tryIncrInternalTimer(); assertArrayEquals(new int[] {0,0,0,0, 0,0,0,1}, MC.ram.readDataCell(SFR.TMR0.asIndex()));
        MC.timer.tryIncrInternalTimer(); assertArrayEquals(new int[] {0,0,0,0, 0,0,0,1}, MC.ram.readDataCell(SFR.TMR0.asIndex()));
        MC.timer.tryIncrInternalTimer(); // tmr0 should be incremented once here
        assertArrayEquals(new int[] {0,0,0,0, 0,0,1,0}, MC.ram.readDataCell(SFR.TMR0.asIndex()));
        
        MC.timer.tryIncrInternalTimer();
        MC.timer.tryIncrInternalTimer();
        MC.timer.tryIncrInternalTimer();
        MC.timer.tryIncrInternalTimer(); // tmr0 should be incremented once here
        assertArrayEquals(new int[] {0,0,0,0, 0,0,1,1}, MC.ram.readDataCell(SFR.TMR0.asIndex()));
    }
    
    @Test
    void testTMR0Interrupt() {
        setup(0, 1, new int[3], new int[] {1,1,1,1, 1,1,1,1}); // intClock | noPrescaler | irrlv. | 255        
        MC.control.pc = 15;
        SFR.setGIE(1); SFR.setTOIE(1);
        MC.timer.tryIncrInternalTimer(); // causes interrupt        
        assertArrayEquals(new int[8], MC.ram.readDataCell(SFR.TMR0.asIndex())); // overflow
        assertEquals(4, MC.control.pc);
        MC.stack.pop(); 
        assertEquals(15, MC.control.pc);
        
        MC.control.pc = 15;
        setup(0, 1, new int[3], new int[] {1,1,1,1, 1,1,1,1}); // intClock | noPrescaler | irrlv. | 255        
        SFR.setGIE(1); SFR.setTOIE(0);
        MC.timer.tryIncrInternalTimer(); // causes no interrupt        
        assertArrayEquals(new int[8], MC.ram.readDataCell(SFR.TMR0.asIndex())); // overflow
        assertEquals(15, MC.control.pc);
    }
    
    private void setup(int tocs, int psa, int[] ps2to0, int[] tmr0) {
        SFR.setTOCS(tocs); // selects intClock/extClock
        SFR.setPSA(psa); // assigns prescaler to timer/wdog
        SFR.setPS2To0(ps2to0); // prs 1:x
        MC.ram.writeDataCell(SFR.TMR0.asIndex(), tmr0); // startVal
        MC.timer.debug_clearDelay();
        MC.timer.debug_clearIncrCheck();
    }

}
