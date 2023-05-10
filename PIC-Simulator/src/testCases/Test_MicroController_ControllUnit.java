package testCases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pic16f84_simulator.*;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.memory.SFR;

class Test_MicroController_ControllUnit {

    @Test
    void testPCPP() {
        
        // Case: other
        MC.control.pc=1000;// 11 1110 1000
        MC.control.instrReg.writeReg(new int[] {1,1,1,0,1,0,1,1,0,0,0,0,0,0});
        MC.control.pcpp();
        assertArrayEquals(new int[] {1,1,1,0,1,0,0,1},MC.ram.readDataCell(SFR.PCL.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1},MC.ram.readDataCell(SFR.PCLATH.asIndex()));
        
        // Case: NOP
        MC.control.instrReg.writeReg((new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        MC.control.pcpp();
        assertArrayEquals(new int[] {1,1,1,0,1,0,0,1},MC.ram.readDataCell(SFR.PCL.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1},MC.ram.readDataCell(SFR.PCLATH.asIndex()));
    }

}
