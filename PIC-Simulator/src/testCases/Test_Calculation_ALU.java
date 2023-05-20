package testCases;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_Calculation_ALU {
    
    
    @Test
    /*
     * tests for
     * - result (thereby legal scope: [0, 255])
     * - flags: Z, C, DC
     */
    void testAddition() {        
        assertArrayEquals(new int[] {1,0,0,1,1,0,0,0}, MC.alu.addition(new int[] {1,0,0,1, 1,0,0,1}, new int[] {1,1,1,1, 1,1,1,1}));
        assertEquals(0, SFR.getZflag()); 
        assertEquals(1, SFR.getCflag());
        assertEquals(1, SFR.getDCflag());
        
        assertArrayEquals(new int[] {1,0,1,0, 1,0,1,0}, MC.alu.addition(new int[] {1,0,0,1, 1,0,0,1}, new int[] {0,0,0,1, 0,0,0,1}));
        assertEquals(0, SFR.getZflag()); 
        assertEquals(0, SFR.getCflag());
        assertEquals(0, SFR.getDCflag());
        
        assertArrayEquals(new int[8], MC.alu.addition(new int[8], new int[8]));
        assertEquals(1, SFR.getZflag()); 
        assertEquals(0, SFR.getCflag());
        assertEquals(0, SFR.getDCflag());
    }
    

    @Test
    /*
     * tests for
     * - result (thereby legal scope: [0, 255])
     * - flags: Z, C, DC
     */
    void testSubtract() { // Minuend - Subtrahend        
        assertThrows(IllegalArgumentException.class, () -> { MC.alu.subtraction(new int[9], new int[8]); });
        assertThrows(IllegalArgumentException.class, () -> { MC.alu.subtraction(new int[8], new int[] {1,1,1,0,0,0,1,1,0,1}); });
        
        assertArrayEquals(MC.alu.subtraction(new int[8], new int[8]), new int[8]);
        assertEquals(SFR.getZflag(), 1);
        assertEquals(SFR.getCflag(), 0);
        assertEquals(SFR.getDCflag(), 0);
        
        assertArrayEquals(MC.alu.subtraction(new int[8], new int[] {0,0,0,0,0,0,0,1}), new int[] {1,1,1,1,1,1,1,1});
        assertEquals(SFR.getZflag(), 0);
        assertEquals(SFR.getCflag(), 0);
        assertEquals(SFR.getDCflag(), 0);
        
        assertArrayEquals(MC.alu.subtraction(new int[] {0,0,0,0,0,0,0,1}, new int[8]), new int[] {0,0,0,0,0,0,0,1});
        assertEquals(SFR.getZflag(), 0);
        assertEquals(SFR.getCflag(), 0);
        assertEquals(SFR.getDCflag(), 0);
        
        assertArrayEquals(MC.alu.subtraction(new int[] {1,1,1,1, 1,1,1,1}, new int[] {1,1,1,1, 1,1,1,1}), new int[] {0,0,0,0,0,0,0,0});
        assertEquals(SFR.getZflag(), 1);
        assertEquals(SFR.getCflag(), 1);
        assertEquals(SFR.getDCflag(), 1);        
        
        assertArrayEquals(MC.alu.subtraction(new int[] {1,1,1,0, 1,0,1,0}, new int[] {0,1,0,0, 1,0,0,1}), new int[] {1,0,1,0,0,0,0,1});
        assertEquals(SFR.getZflag(), 0);
        assertEquals(SFR.getCflag(), 1);
        assertEquals(SFR.getDCflag(), 1);        
        
        assertArrayEquals(MC.alu.subtraction(new int[] {0,1,0,0, 1,0,0,1}, new int[] {1,1,1,0, 1,0,1,0}), new int[] {0,1,0,1,1,1,1,1});
        assertEquals(SFR.getZflag(), 0);
        assertEquals(SFR.getCflag(), 0);
        assertEquals(SFR.getDCflag(), 0);
    }

}
