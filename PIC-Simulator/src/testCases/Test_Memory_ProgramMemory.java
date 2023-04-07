package testCases;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exception.UnknownLocationException;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.tools.TP;

class Test_Memory_ProgramMemory { // Eduard

    @Test
    void testReadTestPrograme() {
        Program_Memory pm = new Program_Memory();
        pm.readTestProgram(TP.s1);
        assertArrayEquals(pm.readDataCell(0),new int[] {1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
     }

    @Test
    void testStore() {
        Program_Memory pm = new Program_Memory();
        pm.store("00003011");
        assertArrayEquals(pm.readDataCell(0),new int[] {1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
    }
    
    @Test
    void testExceptionHandling() {
        Program_Memory pm = new Program_Memory();
        assertThrows(UnknownLocationException .class, () -> {pm.readDataCell(-1);});
        assertThrows(UnknownLocationException .class, () -> {pm.readDataCell(1024);});
        assertThrows(UnknownLocationException .class, () -> {pm.readSpecificBit(0, -1);});
        assertThrows(UnknownLocationException .class, () -> {pm.readSpecificBit(0, 16);});
        assertThrows(UnknownLocationException .class, () -> {pm.writeDataCell(-1, new int[] {0});});
        assertThrows(UnknownLocationException .class, () -> {pm.writeDataCell(1024, new int[] {0});});
        assertThrows(UnknownLocationException .class, () -> {pm.writeSpecificBit(0, -1, 0);});
        assertThrows(UnknownLocationException .class, () -> {pm.writeSpecificBit(0, 16, 0);});
    }
    
    
    

}
