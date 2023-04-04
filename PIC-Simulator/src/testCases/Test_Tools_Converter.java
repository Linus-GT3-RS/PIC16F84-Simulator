package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pic16f84_simulator.backend.tools.Converter;

class Test_Tools_Converter {
    
    @Test
    void testEnlargeArray() {
        assertThrows(NegativeArraySizeException.class, () -> { Converter.enlargeArray(new int[1], 0); });
        assertThrows(NegativeArraySizeException.class, () -> { Converter.enlargeArray(new int[] {1,0}, 1); });
        
        assertArrayEquals(new int[] {1}, Converter.enlargeArray(new int[] {1}, 1));
        
        assertArrayEquals(new int[] {0,1}, Converter.enlargeArray(new int[] {1}, 2));
        assertArrayEquals(new int[] {0,0,1}, Converter.enlargeArray(new int[] {1}, 3));
        
        assertArrayEquals(new int[] {0,1,0}, Converter.enlargeArray(new int[] {1,0}, 3));
    }
    
    

    @Test
    void testHexToDec() {
        assertEquals(0, Converter.hexToDec('0'));
        assertEquals(5, Converter.hexToDec('5'));
        assertEquals(9, Converter.hexToDec('9'));
        
        assertEquals(10, Converter.hexToDec('A'));
        assertEquals(10, Converter.hexToDec('a'));
        assertEquals(11, Converter.hexToDec('B'));
        assertEquals(14, Converter.hexToDec('E'));
        assertEquals(14, Converter.hexToDec('e'));
        assertEquals(15, Converter.hexToDec('F'));
    }
    
    @Test
    void testHexToBinary() {        
        assertArrayEquals(new int[] {1,0,1,0}, Converter.hexToBinary('A'));
        assertArrayEquals(new int[] {1,0,1,0}, Converter.hexToBinary('a'));
        
        assertArrayEquals(new int[] {0,0,0,0}, Converter.hexToBinary('0'));
        
        assertArrayEquals(new int[] {1,0,0,1}, Converter.hexToBinary('9'));
        
        assertArrayEquals(new int[] {1,1,1,1}, Converter.hexToBinary('F'));
        assertArrayEquals(new int[] {1,1,1,1}, Converter.hexToBinary('f'));
        
    }
    
    
    @Test
    void testBinaryToHex() {        
        assertEquals("f", Converter.binaryToHex(new int[] {1,1,1,1}));
        assertEquals("f", Converter.binaryToHex(new int[] {0,0,0,0,1,1,1,1}));
        
        assertEquals("0", Converter.binaryToHex(new int[] {0}));
        assertEquals("0", Converter.binaryToHex(new int[] {0,0,0}));
        
        assertEquals("9", Converter.binaryToHex(new int[] {1,0,0,1}));
        assertEquals("9", Converter.binaryToHex(new int[] {0,0,1,0,0,1}));
        
        assertEquals("6e", Converter.binaryToHex(new int[] {1,1,0,1,1,1,0}));
    }
    

}
