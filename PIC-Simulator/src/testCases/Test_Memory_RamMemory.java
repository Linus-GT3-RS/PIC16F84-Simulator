package testCases;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.memory.SFR;

class Test_Memory_RamMemory {
    
    
    @Test
    void testWriteRam() {
        RAM_Memory ram = MC.ram;
        
        // SFR
        ram.writeDataCell(0, new int[] {0,1,1,0,1,0,1,1});
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(0));
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(128));
        
        ram.writeDataCell(SFR.INDF.asIndex(), new int[] {0,0,0,0,0,0,1,1});
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(0));
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(128));
        
        ram.writeDataCell(1, new int[] {1,1,1,1,1,0,1,1});
        assertArrayEquals(new int[] {1,1,1,1,1,0,1,1}, ram.readDataCell(1));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, ram.readDataCell(129));
        
        ram.writeDataCell(SFR.STATUS.asIndex(), new int[] {0,1,1,0,1,0,1,1});
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(SFR.STATUS.asIndex()));
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,1}, ram.readDataCell(131));

        ram.writeDataCell(SFR.TRISB.asIndex(), new int[] {0,1,1,0,0,0,0,0});
        assertArrayEquals(new int[] {0,1,1,0,0,0,0,0}, ram.readDataCell(SFR.TRISB.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, ram.readDataCell(6));

        ram.writeDataCell(129, new int[] {0,0,0,0,0,0,1,1});
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(129));
        assertArrayEquals(new int[] {1,1,1,1,1,0,1,1}, ram.readDataCell(1));
        
        ram.writeDataCell(128, new int[] {1,1,0,0,0,0,1,1});
        assertArrayEquals(new int[] {1,1,0,0,0,0,1,1}, ram.readDataCell(128));
        assertArrayEquals(new int[] {1,1,0,0,0,0,1,1}, ram.readDataCell(0));

        
        // GPR
        ram.writeDataCell(12, new int[] {1,0,0,0,0,0,0,1});
        assertArrayEquals(new int[] {1,0,0,0,0,0,0,1}, ram.readDataCell(12));
        assertArrayEquals(new int[] {1,0,0,0,0,0,0,1}, ram.readDataCell(140));

        ram.writeDataCell(14, new int[] {0,0,0,0,0,1,0,1});
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,1}, ram.readDataCell(14));
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,1}, ram.readDataCell(142));

        ram.writeDataCell(79, new int[] {0,0,0,0,0,0,1,1});
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(79));
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,1}, ram.readDataCell(207));
        
        ram.writeDataCell(140, new int[] {1,0,1,0,1,0,0,0});
        assertArrayEquals(new int[] {1,0,1,0,1,0,0,0}, ram.readDataCell(140));
        assertArrayEquals(new int[] {1,0,1,0,1,0,0,0}, ram.readDataCell(12));
        
    }
    
    
    
    @Test
    void testCheckMemoryLocation() {
        RAM_Memory ram = MC.ram;        
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkAddress(7);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkAddress(135);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkAddress(80);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkAddress(90);});
        assertThrows(NegativeArraySizeException .class, () -> {ram.checkAddress(127);});    
    }
    
    
    @Test
    void testtryToMirrorBank() {
        RAM_Memory ram = MC.ram;        
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
