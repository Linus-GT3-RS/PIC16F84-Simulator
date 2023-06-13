package testCases.other;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_Control_Prescaler {

    @Test
    void testGetPrescaler() {
        // ++++++ Case for lower Prescaler +++++++++ 
        // Case TMR0
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 4, 0);
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 5, 0);
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 6, 0);
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 7, 0);
        assertEquals(2,MC.timer.getPRS()); 
        
        // Case WatchDog
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 4, 1);
        assertEquals(1,MC.wdog.getPRS());
        
        // ++++++ Case for higher Prescaler  +++++++++
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 5, 1);
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 6, 1);
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 7, 1);
        // Case WatchDog
        assertEquals(128,MC.prescaler.getPRS());
        assertEquals(128,MC.wdog.getPRS());
        
        // Case TMR0
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 4, 0);
        assertEquals(128,MC.prescaler.getPRS());
        assertEquals(256,MC.timer.getPRS());
    }

}
