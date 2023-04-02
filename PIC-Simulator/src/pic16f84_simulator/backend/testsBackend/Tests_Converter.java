package pic16f84_simulator.backend.testsBackend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.backend.BinaryArrayIsToSmall;
import pic16f84_simulator.backend.Converter;

class Tests_Converter {

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
        assertThrows(BinaryArrayIsToSmall.class, () -> { Converter.hexToBinary('F', 2); } );
        
        assertArrayEquals(new int[] {1,0,1,0}, Converter.hexToBinary('A', 4));
        assertArrayEquals(new int[] {0,0,0,0,0,0, 1,0,1,0}, Converter.hexToBinary('A', 10));
        assertArrayEquals(new int[] {0,0,0,0,0,0, 1,0,1,0}, Converter.hexToBinary('a', 10));
        
        assertArrayEquals(new int[] {0,0,0,0,0,0, 0,0,0,0}, Converter.hexToBinary('0', 10));
        assertArrayEquals(new int[] {0,0,0,0}, Converter.hexToBinary('0', 4));
        
        assertArrayEquals(new int[] {1,0,0,1}, Converter.hexToBinary('9', 4));
        assertArrayEquals(new int[] {0,0,0,0, 1,0,0,1}, Converter.hexToBinary('9', 8));
        
        assertArrayEquals(new int[] {0,0, 1,1,1,1}, Converter.hexToBinary('F', 6));
        
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
