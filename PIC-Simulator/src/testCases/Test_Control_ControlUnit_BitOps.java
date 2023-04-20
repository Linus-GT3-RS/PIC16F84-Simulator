package testCases;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.Utils;

class Test_Control_ControlUnit_BitOps {

    @Test
    void testBCF() {
        MicroC mc = new MicroC();
        MicroC.pm.readTestProgram(TP.s5);
        MicroC.cu.loadInstrReg(5);
        assertArrayEquals(mc.pm.readDataCell(5), mc.cu.instrReg.readReg()); // 01 0010 0000 1100 -> 01 00bb bfff ffff
        MicroC.cu.exe();
        mc.ram.checkBit(Utils.binaryToDec(new int[] {0,0,0,1,1,0,0}),0);
    }
    
    @Test
    void testBSF() { // Linus
        ControlUnit.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        ControlUnit.exe();
        assertArrayEquals(new int[] {0,1,0,0,0,0,0,0}, MicroC.ram.readDataCell(32));
    }
    
    @Test
    void testBTFSS() { // Linus
     // Fall 1: bBit=0 --> next instruction gets (just as normal) executed
        int pcBeforeExe = ControlUnit.pc;
        MicroC.ram.writeSpecificBit(14, 1, 0); 
        ControlUnit.instrReg.writeReg(new int[] {0,1,1,1, 0,0,1, 0,0,0,1,1,1,0});
        ControlUnit.exe();
        assertEquals((pcBeforeExe + 1), ControlUnit.pc);
     
     // Fall 2: bBit=1 --> next instruction gets skipped 
        pcBeforeExe = ControlUnit.pc;
        MicroC.ram.writeSpecificBit(14, 1, 1); 
        ControlUnit.instrReg.writeReg(new int[] {0,1,1,1, 0,0,1, 0,0,0,1,1,1,0});
        ControlUnit.exe();
        assertEquals((pcBeforeExe + 2), ControlUnit.pc);
    }
    
    @Test
    void testBTFSC() {
        MicroC mc = new MicroC();
        MicroC.pm.readTestProgram(TP.s5);
        
        //Fall 1: 
        MicroC.cu.pc = 7;
        MicroC.cu.loadInstrReg(7);
        MicroC.cu.exe();
        assertEquals(MicroC.cu.pc,9);
    }

}
