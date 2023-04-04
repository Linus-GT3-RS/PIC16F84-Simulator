package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.tools.TP;

class Test_Memory_ProgramMemory {

    @Test
    void testReadTestPrograme() {
        Program_Memory pm = new Program_Memory();
        pm.readTestProgram(TP.s1);
        assertArrayEquals(pm.readDataCell(0),new int[] {0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
     }

    @Test
    void testStore() {
        Program_Memory pm = new Program_Memory();
        pm.Store("00003011");
        assertArrayEquals(pm.readDataCell(0),new int[] {0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
    }

}
