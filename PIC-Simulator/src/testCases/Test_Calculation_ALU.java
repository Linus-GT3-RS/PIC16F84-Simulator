package testCases;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_Calculation_ALU {
    
    
    @Test
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
    

    @Disabled
    void testSubstract() {
        /*
         * test for right scope: [0, 255]
         * test for flags: Z, C, DC FIXME
         */
        assertThrows(IllegalArgumentException.class, () -> { MC.alu.substraction(new int[9], new int[8]); });
        assertThrows(IllegalArgumentException.class, () -> { MC.alu.substraction(new int[8], new int[2]); });
        
        assertArrayEquals(MC.alu.substraction(new int[8], new int[8]), new int[8]);
        assertArrayEquals(MC.alu.substraction(new int[8], new int[] {0,0,0,0,0,0,0,1}), new int[] {1,1,1,1,1,1,1,1});
        assertArrayEquals(MC.alu.substraction(new int[] {0,0,0,0,0,0,0,1}, new int[8]), new int[] {0,0,0,0,0,0,0,1});
        assertArrayEquals(MC.alu.substraction(new int[] {1,1,1,1,1,1,1,1}, new int[] {1,1,1,1,1,1,1,1}), new int[] {0,0,0,0,0,0,0,0}); 
        assertArrayEquals(MC.alu.substraction(new int[] {1,1,1,0,1,0,1,0}, new int[] {0,1,0,0,1,0,0,1}), new int[] {1,0,1,0,0,0,0,1});
        assertArrayEquals(MC.alu.substraction(new int[] {0,1,0,0,1,0,0,1}, new int[] {1,1,1,0,1,0,1,0}), new int[] {0,1,0,1,1,1,1,1});
    }

}
