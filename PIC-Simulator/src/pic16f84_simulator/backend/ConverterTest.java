package pic16f84_simulator.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConverterTest {

    @Test
    void testHexToBinary() {
        assertArrayEquals(Converter.hexToBinary('A'),new int[] {1,0,1,0});
        assertArrayEquals(Converter.hexToBinary('F'),new int[] {1,1,1,1});
    }
    
    @Test
    void testHexToDec() {
       assertEquals(Converter.hexToDec('0'),0);
       assertEquals(Converter.hexToDec('A'),10);
    }

}
