package testCases.other;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_Control_ControllUnit {

    @Test
    void testPCPP() {
        MC.ram.writeDataCell(SFR.PCLATH.asIndex(), new int[] {0,0,0, 1,1, 1,1,1}); // pclatch will stay the same
        MC.control.pc(1022);
        MC.control.pcpp();
        assertEquals(1023, MC.control.pc()); // pc = 00_011 1111_1111
        assertArrayEquals(new int[] {1,1,1,1, 1,1,1,1}, MC.ram.readDataCell(SFR.PCL.asIndex()));
        assertArrayEquals(new int[] {0,0,0, 1,1, 1,1,1}, MC.ram.readDataCell(SFR.PCLATH.asIndex()));
        
        MC.control.pcpp();
        assertEquals(0, MC.control.pc());
        assertArrayEquals(new int[8], MC.ram.readDataCell(SFR.PCL.asIndex()));
        assertArrayEquals(new int[] {0,0,0, 1,1, 1,1,1}, MC.ram.readDataCell(SFR.PCLATH.asIndex()));
    }
    
    @Test
    void testPC() {        
        MC.control.pc(10);
        assertEquals(10, MC.control.pc());
        assertArrayEquals(new int[] {0,0,0,0, 1,0,1,0}, MC.ram.readDataCell(SFR.PCL.asIndex()));
        
        MC.control.pc(566);
        assertEquals(566, MC.control.pc());
        assertArrayEquals(new int[] {0,0,1,1, 0,1,1,0}, MC.ram.readDataCell(SFR.PCL.asIndex()));
    }
    
    @Test
    void testUpdatePC() {
        MC.control.pc(664); // 00_010 1001_1000
        MC.ram.writeDataCell(SFR.PCLATH.asIndex(), new int[] {0,0,0, 1,1, 0,1,1}); // (PIC ignores pclatch 4-3)
        assertEquals(664, MC.control.pc());
        MC.ram.writeDataCell(SFR.PCL.asIndex(), new int[] {0,1,1,0, 1,1,1,0}); // now pc gets updated
        assertEquals(878, MC.control.pc()); // 00_011 0110_1110
        
        MC.ram.writeDataCell(SFR.PCL.asIndex(), new int[] {0,0,0,0, 1,1,1,0}); // now pc gets updated
        assertEquals(782, MC.control.pc()); // 00_011 0000_1110
    }
    

}
