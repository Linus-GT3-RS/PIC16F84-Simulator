package testCases;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.calculation.ALU;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.InstructionDecoder;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.tools.Utils;

class Test_Tools_Utils {
    
    @Test
    void testOnlyOne() {
        new MC();
        assertThrows(IllegalArgumentException.class, () -> { new MC(); });
        assertThrows(IllegalArgumentException.class, () -> { new ALU(); });
        assertThrows(IllegalArgumentException.class, () -> { new ControlUnit(); });
        assertThrows(IllegalArgumentException.class, () -> { new InstructionDecoder(); });
        assertThrows(IllegalArgumentException.class, () -> { new Program_Memory(); });
        assertThrows(IllegalArgumentException.class, () -> { new RAM_Memory(); });
    }
    
    @Test
    void testCutArray() {
        int[] test = new int[] {0,1,1,0,1,0,0,1};
        
        assertEquals("0110", Utils.cutArray(test, 0, 3));
        assertEquals("110", Utils.cutArray(test, 1, 3));
        assertEquals("0", Utils.cutArray(test, 3, 3));
        assertEquals("00", Utils.cutArray(test, 5, 6));        
    }
        
    
    @Test
    void testEnlargeArray() {
        assertThrows(NegativeArraySizeException.class, () -> { Utils.enlargeArray(new int[1], 0); });
        assertThrows(NegativeArraySizeException.class, () -> { Utils.enlargeArray(new int[] {1,0}, 1); });
        
        assertArrayEquals(new int[] {1}, Utils.enlargeArray(new int[] {1}, 1));
        
        assertArrayEquals(new int[] {0,1}, Utils.enlargeArray(new int[] {1}, 2));
        assertArrayEquals(new int[] {0,0,1}, Utils.enlargeArray(new int[] {1}, 3));
        
        assertArrayEquals(new int[] {0,1,0}, Utils.enlargeArray(new int[] {1,0}, 3));
    }
    
    
    @Test
    void testHexToDec() {
        assertEquals(0, Utils.hexToDec('0'));
        assertEquals(5, Utils.hexToDec('5'));
        assertEquals(9, Utils.hexToDec('9'));
        
        assertEquals(10, Utils.hexToDec('A'));
        assertEquals(10, Utils.hexToDec('a'));
        assertEquals(11, Utils.hexToDec('B'));
        assertEquals(14, Utils.hexToDec('E'));
        assertEquals(14, Utils.hexToDec('e'));
        assertEquals(15, Utils.hexToDec('F'));
    }
    
    
    @Test
    void testHexToBinary() {        
        assertArrayEquals(new int[] {1,0,1,0}, Utils.hexToBinary('A'));
        assertArrayEquals(new int[] {1,0,1,0}, Utils.hexToBinary('a'));
        
        assertArrayEquals(new int[] {0,0,0,0}, Utils.hexToBinary('0'));
        
        assertArrayEquals(new int[] {1,0,0,1}, Utils.hexToBinary('9'));
        
        assertArrayEquals(new int[] {1,1,1,1}, Utils.hexToBinary('F'));
        assertArrayEquals(new int[] {1,1,1,1}, Utils.hexToBinary('f'));
        
    }
    
    
    @Test
    void testBinaryToHex() {        
        assertEquals("f", Utils.binaryToHex(new int[] {1,1,1,1}));
        assertEquals("f", Utils.binaryToHex(new int[] {0,0,0,0,1,1,1,1}));
        
        assertEquals("0", Utils.binaryToHex(new int[] {0}));
        assertEquals("0", Utils.binaryToHex(new int[] {0,0,0}));
        
        assertEquals("9", Utils.binaryToHex(new int[] {1,0,0,1}));
        assertEquals("9", Utils.binaryToHex(new int[] {0,0,1,0,0,1}));
        
        assertEquals("6e", Utils.binaryToHex(new int[] {1,1,0,1,1,1,0}));
    }
    
    @Test
    void testBinaryToDec() {
        assertEquals(15, Utils.binaryToDec(new int[] {1,1,1,1}));
    }
    
    @Test
    void testDecToBinary() {
        assertArrayEquals(new int[] {1,1,1,1},Utils.decToBinary(15,4));
    }
    
}
