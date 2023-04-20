package testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.memory.RAM_Memory;

class Test_Control_ControlUnit_BitOps {    
    
    @Test
    void testBSF() { // Linus
        ControlUnit.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        ControlUnit.exe(BitOps.BSF);
        assertArrayEquals(new int[] {0,1,0,0,0,0,0,0}, MicroC.ram.readDataCell(32));
    }
    
    @Test
    void testBTFSS() { // Linus
     // Fall 1: bBit=0 --> next instruction gets (just as normal) executed
        int pcBeforeExe = ControlUnit.pc;
        MicroC.ram.writeSpecificBit(14, 1, 0); 
        ControlUnit.instrReg.writeReg(new int[] {0,1,1,1, 0,0,1, 0,0,0,1,1,1,0});
        ControlUnit.exe(BitOps.BTFSS);
        assertEquals((pcBeforeExe + 1), ControlUnit.pc);
     
     // Fall 2: bBit=1 --> next instruction gets skipped 
        pcBeforeExe = ControlUnit.pc;
        MicroC.ram.writeSpecificBit(14, 1, 1); 
        ControlUnit.instrReg.writeReg(new int[] {0,1,1,1, 0,0,1, 0,0,0,1,1,1,0});
        ControlUnit.exe(BitOps.BTFSS);
        assertEquals((pcBeforeExe + 2), ControlUnit.pc);
    }

}
