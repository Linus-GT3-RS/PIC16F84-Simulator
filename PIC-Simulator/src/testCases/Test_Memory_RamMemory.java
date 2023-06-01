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
    void testOtherReset() {
        int[] tmr0 = MC.ram.readDataCell(SFR.TMR0.asIndex());
        int[] fsr = MC.ram.readDataCell(SFR.FSR.asIndex());
        int status3 = MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 3);
        int status4 = MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 4);
        int status5 = MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5);
        int status6 = MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6);
        int status7 = MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7);
        int porta3 = MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 3);
        int porta4 = MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 4);
        int porta5 = MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 5);
        int porta6 = MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 6);
        int porta7 = MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 7);
        int intcon7 = MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 7);
        int eecon14 = MC.ram.readSpecificBit(SFR.EECON1.asIndex(), 4);
      
        MC.ram.otherReset();
        
        assertArrayEquals(new int[8],MC.ram.readDataCell(SFR.INDF.asIndex()));
        assertArrayEquals(tmr0,MC.ram.readDataCell(SFR.TMR0.asIndex()));
        assertArrayEquals(new int[8],MC.ram.readDataCell(SFR.PCL.asIndex()));
        assertArrayEquals(new int[] {0,0,0,status3,status4,status5,status6,status7},MC.ram.readDataCell(SFR.STATUS.asIndex()));
        assertArrayEquals(fsr,MC.ram.readDataCell(SFR.FSR.asIndex()));
        assertArrayEquals(new int[] {0,0,0,porta3,porta4,porta5,porta6,porta7},MC.ram.readDataCell(SFR.PORTA.asIndex()));
        assertArrayEquals(MC.ram.readDataCell(SFR.PORTB.asIndex()),MC.ram.readDataCell(SFR.PORTB.asIndex()));
        assertArrayEquals(MC.ram.readDataCell(SFR.EEDATA.asIndex()),MC.ram.readDataCell(SFR.EEDATA.asIndex()));
        assertArrayEquals(MC.ram.readDataCell(SFR.EEADR.asIndex()),MC.ram.readDataCell(SFR.EEADR.asIndex()));
        assertArrayEquals(new int[8],MC.ram.readDataCell(SFR.PCLATH.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,intcon7},MC.ram.readDataCell(SFR.INTCON.asIndex()));
        assertArrayEquals(new int[] {1,1,1,1,1,1,1,1},MC.ram.readDataCell(SFR.OPTION.asIndex()));
        assertArrayEquals(new int[] {0,0,0,1,1,1,1,1},MC.ram.readDataCell(SFR.TRISA.asIndex()));
        assertArrayEquals(new int[] {1,1,1,1,1,1,1,1},MC.ram.readDataCell(SFR.TRISB.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,eecon14,0,0,0},MC.ram.readDataCell(SFR.EECON1.asIndex()));
        assertArrayEquals(new int[8],MC.ram.readDataCell(SFR.EECON2.asIndex()));
    }

    @Test
    void testPowerOnReset() {
        MC.ram.powerOnReset();
        assertArrayEquals(new int[8], MC.ram.readDataCell(20)); // GPR
        assertArrayEquals(new int[8], MC.ram.readDataCell(150)); // GPR
        assertArrayEquals(new int[8], MC.ram.readDataCell(1)); // SFR
        assertArrayEquals(new int[] {0,0,0,1, 1,0,0,0}, MC.ram.readDataCell(3)); // SFR
        assertArrayEquals(new int[] {0,0,0,1, 1,0,0,0}, MC.ram.readDataCell(131)); // SFR
    }

    @Test
    void testCheckUp() {        
        /*
         * tests for case: 1 == tmr0
         *  - tmr0Delay is tested int Test_timer
         */
        
        

        /*
         *  test for caseX
         */
    }


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

        ram.writeDataCell(129, new int[8]);        
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
