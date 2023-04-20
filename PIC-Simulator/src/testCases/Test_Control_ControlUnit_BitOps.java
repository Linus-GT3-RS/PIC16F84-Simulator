package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.BitOps;

class Test_Control_ControlUnit_BitOps {

    @Test
    void test() {
//        fail("Not yet implemented");
    }
    
    
    @Test
    void testBSF() {
        ControlUnit.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        ControlUnit.exe(BitOps.BSF);
        assertArrayEquals(new int[] {0,1,0,0,0,0,0,0}, MicroC.ram.readDataCell(32));
    }
    
    @Test
    void testBTFSS() {
        ControlUnit.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        ControlUnit.exe();
    }

}
