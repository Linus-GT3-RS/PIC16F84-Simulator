package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.backend.UnknownLocationException;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.memory.SFR;

class Test_Memory_RamMemory {

    @Test
    void testSetSFRBit() {
        RAM_Memory ram = new RAM_Memory();
        
        // Bank0
        ram.setSFRBit(SFR.STATUS, 7, 1);
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,1}, ram.readDataCell(3));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,1}, ram.readDataCell(131));
        
        ram.setSFRBit(SFR.EEADR, 0, 1);
        assertArrayEquals(new int[] {1,0,0,0,0,0,0,0}, ram.readDataCell(9));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, ram.readDataCell(137));
        
        // Bank1
        ram.setSFRBit(SFR.TRISA, 1, 1);
        assertArrayEquals(new int[] {0,1,0,0,0,0,0,0}, ram.readDataCell(133));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, ram.readDataCell(5));        
    }
    
    
    @Test
    void testWriteRam() {
        RAM_Memory ram = new RAM_Memory();
        
        // SFR
        ram.writeRAM(0, new int[] {0,1,1,0,1,0,1,1});
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(0));
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(128));
        
        ram.writeRAM(SFR.INDF.asIndex(), new int[] {0,0,0,0,0,0,1,1});
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(0));
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(128));
        
        ram.writeRAM(1, new int[] {1,1,1,1,1,0,1,1});
        assertArrayEquals(new int[] {1,1,1,1,1,0,1,1}, ram.readDataCell(1));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, ram.readDataCell(129));
        
        ram.writeRAM(SFR.STATUS.asIndex(), new int[] {0,1,1,0,1,0,1,1});
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(SFR.STATUS.asIndex()));
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(131));

        ram.writeRAM(SFR.TRISB.asIndex(), new int[] {0,1,1,0,0,0,0,0});
        assertArrayEquals(new int[] {0,1,1,0,0,0,0,0}, ram.readDataCell(SFR.TRISB.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, ram.readDataCell(6));

        ram.writeRAM(129, new int[] {0,0,0,0,0,0,1,1});
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(129));
        assertArrayEquals(new int[] {1,1,1,1,1,0,1,1}, ram.readDataCell(1));
        
        ram.writeRAM(128, new int[] {1,1,0,0,0,0,1,1});
        assertArrayEquals(new int[] {1,1,0,0,0,0,1,1}, ram.readDataCell(128));
        assertArrayEquals(new int[] {1,1,0,0,0,0,1,1}, ram.readDataCell(0));

        
        // GPR
        ram.writeRAM(12, new int[] {1,0,0,0,0,0,0,1});
        assertArrayEquals(new int[] {1,0,0,0,0,0,0,1}, ram.readDataCell(12));
        assertArrayEquals(new int[] {1,0,0,0,0,0,0,1}, ram.readDataCell(140));

        ram.writeRAM(14, new int[] {0,0,0,0,0,1,0,1});
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,1}, ram.readDataCell(14));
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,1}, ram.readDataCell(142));

        ram.writeRAM(79, new int[] {0,0,0,0,0,0,1,1});
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(79));
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(207));
        
        ram.writeRAM(140, new int[] {1,0,1,0,1,0,0,0}); 
        assertArrayEquals(new int[] {1,0,1,0,1,0,0,0}, ram.readDataCell(140));
        assertArrayEquals(new int[] {1,0,1,0,1,0,0,0}, ram.readDataCell(12));
        
    }
    
    
    
    @Test
    void testCheckMemoryLocation() {
        RAM_Memory ram = new RAM_Memory();
        
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkMemoryLocation(7);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkMemoryLocation(135);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkMemoryLocation(80);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkMemoryLocation(90);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkMemoryLocation(127);});    
    }
    
    
    @Test
    void testtryToMirrorBank() {
        RAM_Memory ram = new RAM_Memory();
        
        // SFR
        assertEquals(1, ram.mirrorBank(1));
        assertEquals(129, ram.mirrorBank(129));//
        assertEquals(5, ram.mirrorBank(5));
        assertEquals(133, ram.mirrorBank(133));//
        assertEquals(9, ram.mirrorBank(9));
        assertEquals(137, ram.mirrorBank(137));//
        
        // GPR
        assertEquals(128, ram.mirrorBank(0));
        assertEquals(0, ram.mirrorBank(128));//        
        assertEquals(130, ram.mirrorBank(2));
        assertEquals(2, ram.mirrorBank(130));//        
        assertEquals(138, ram.mirrorBank(10));
        assertEquals(10, ram.mirrorBank(138));//        
        assertEquals(207, ram.mirrorBank(79));        
        assertEquals(79, ram.mirrorBank(207));//      
    }
    
    

}
