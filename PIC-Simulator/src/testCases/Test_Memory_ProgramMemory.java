package testCases;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.tools.TP;

class Test_Memory_ProgramMemory { // Eduard

    @Test
    void testReadTestProgramm() {
        Program_Memory pm = MC.pm;        
        pm.loadTestProgram(TP.s1);
        assertArrayEquals(pm.readDataCell(0), new int[] {1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
        
        pm.loadTestProgram(TP.s5);
        assertArrayEquals(pm.readDataCell(13), new int[] {0,1, 1,1,0,1, 0,0,0,0, 1,1,0,0}); //01 1101 0000 1100
     }
    
    @Test
    void testSpecialCase() {
        Program_Memory pm = MC.pm;
        pm.store("000D1D0C");
        assertArrayEquals(pm.readDataCell(13), new int[] {0,1, 1,1,0,1, 0,0,0,0, 1,1,0,0});
    }

    @Test
    void testStore() {
        Program_Memory pm = MC.pm;
        pm.store("00003011");
        assertArrayEquals(pm.readDataCell(0),new int[] {1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
    }
}
