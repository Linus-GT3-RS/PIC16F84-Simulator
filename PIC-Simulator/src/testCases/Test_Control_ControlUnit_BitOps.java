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
    void testBCF() { // Eduard
        MicroC.pm.readTestProgram(TP.s5);
        MicroC.control.pc = 5; // 01 0010 0000 1100 -> 01 00bb bfff ffff 
        MicroC.control.exe();
        MicroC.ram.checkBit(Utils.binaryToDec(new int[] {0,0,0,1,1,0,0}),0);
    }
    
    @Test
    void testBSF() { // Linus
        System.out.println(MicroC.control.pc); 
        // write pm int[] an Index 500
        // pc = 500
        // exe 
        MicroC.control.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        MicroC.control.exe();
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
    void testBTFSC() { // Eduard
        MicroC.pm.readTestProgram(TP.s5);
        
        //Fall 1: bit ist 0
        MicroC.control.pc = 7; // 01 1000 0000 1100
        MicroC.control.exe();
        assertEquals(MicroC.control.pc,9);
        
        //Fall 2: bit ist 1
        MicroC.control.pc = 4; // 01 0101 1000 1100 -> BSF on 1100
        MicroC.control.exe();
        MicroC.control.pc = 10; // 01 1001 1000 1100
        MicroC.control.exe();
        assertEquals(MicroC.control.pc,11);
        
        
    }

}
