package testCases.other;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.frontend.Ports;

class Test_Interrupt {

    @Test
    void testRB47Interrupt() {
        SFR.setGIE(0);
        SFR.setRBIE(0);
        SFR.setRBIF(0);
        MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 3, 0); //OUT
        MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 3, 1); //RB4       
        assertEquals(0,SFR.getRBIF());
        MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 3, 0); // changeable Input
        assertEquals(0,SFR.getRBIF());
        
        MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 3, 1); //IN
        MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 3, 1); // changeable Input
        assertEquals(1,SFR.getRBIF());
        SFR.setRBIF(0);
        MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 2, 0);
        MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 2, 1); //changable Output
        assertEquals(0,SFR.getRBIF());
        
        int pc = MC.control.pc();
        MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 3, 0);
        assertEquals(pc,MC.stack.getTOSValue());
        assertEquals(4,MC.control.pc());
    }

}
