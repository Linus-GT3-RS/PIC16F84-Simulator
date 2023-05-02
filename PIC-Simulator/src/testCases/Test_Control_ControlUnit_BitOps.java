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
        MicroC.pm.readTestProgram(TP.s5);
        MicroC.control.pc = 3; // 0101 111 0001100
        MicroC.control.exe(); 
        assertArrayEquals(new int[] {0,0,0,0,0,0,0, 1}, MicroC.ram.readDataCell(12));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0, 1}, MicroC.ram.readDataCell(140));
    }
    
    @Test
    void testBTFSS() { // Linus
        MicroC.pm.readTestProgram(TP.s5);
        ControlUnit cu = MicroC.control;
        
     // Fall 1: bBit=0 --> next instruction gets executed (no change in programm sequence) -> pc+1
        cu.pc = 13; // 0111 010 0001100
        MicroC.ram.writeDataCell(12, new int[] {0,0, 0 ,0,0,0,0,1});
        cu.exe();
        assertEquals(14, cu.pc);
        
     // Fall 2: bBit=1 --> next instruction gets skipped -> pc+2
        cu.pc = 16; // 0111 111 0001100
        MicroC.ram.writeDataCell(140, new int[] {0,0,0,0,0,0,0, 1});
        cu.exe();
        assertEquals(18, cu.pc);
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
