package testCases;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.UnknownLocationException;

class Test_Memory_ProgramMemory { // Eduard

    @Test
    void testReadTestProgramm() {
        Program_Memory pm = MicroC.pm;        
        pm.readTestProgram(TP.s1);
        assertArrayEquals(pm.readDataCell(0), new int[] {1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
        
        pm.readTestProgram(TP.s5);
        assertArrayEquals(pm.readDataCell(13), new int[] {0,1, 1,1,0,1, 0,0,0,0, 1,1,0,0}); //01 1101 0000 1100
     }
    
    @Test
    void testSpecialCase() {
        Program_Memory pm = MicroC.pm;
        pm.store("000D1D0C");
        assertArrayEquals(pm.readDataCell(13), new int[] {0,1, 1,1,0,1, 0,0,0,0, 1,1,0,0});
    }

    @Test
    void testStore() {
        Program_Memory pm = MicroC.pm;
        pm.store("00003011");
        assertArrayEquals(pm.readDataCell(0),new int[] {1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
    }
    
    @Test
    void testExceptionHandling() {
        Program_Memory pm = MicroC.pm;
        assertThrows(UnknownLocationException.class, () -> {pm.readDataCell(-1);});
        assertThrows(UnknownLocationException.class, () -> {pm.readDataCell(1024);});
        assertThrows(UnknownLocationException.class, () -> {pm.readSpecificBit(0, -1);});
        assertThrows(UnknownLocationException.class, () -> {pm.readSpecificBit(0, 14);});
        assertThrows(UnknownLocationException.class, () -> {pm.writeDataCell(-1, new int[] {0});});
        assertThrows(UnknownLocationException.class, () -> {pm.writeDataCell(1024, new int[] {0});});
        assertThrows(UnknownLocationException.class, () -> {pm.writeSpecificBit(0, -1, 0);});
        assertThrows(UnknownLocationException.class, () -> {pm.writeSpecificBit(0, 14, 0);});
    }    

}
