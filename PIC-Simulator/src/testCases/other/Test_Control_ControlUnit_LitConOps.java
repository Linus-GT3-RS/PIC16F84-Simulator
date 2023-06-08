package testCases.other;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.Utils;

class Test_Control_ControlUnit_LitConOps {
    
    @Test
    void testADDLW() {
        MC.pm.loadTestProgram(TP.s4);        
        MC.control.pc = 2; // 111110 00010001=17
        MC.alu.wReg.write(new int[] {1,0,0,1, 1,1,1,0}); // =158
        MC.control.exe();
        assertArrayEquals(Utils.decToBinary(175, 8), MC.alu.wReg.read());        
    }

    @Test // Eduard
    void testANDLW() {
        MC.pm.loadTestProgram(TP.s1);

        // Case Z-Bit is 0
        MC.control.pc = 1; // 11 1001 0011 0000
        MC.alu.wReg.write(new int[] { 0, 1, 0, 1, 0, 1, 0, 0 });
        MC.control.exe();
        assertArrayEquals(new int[] { 0, 0, 0, 1, 0, 0, 0, 0 }, MC.alu.wReg.read());
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag

        // Case Z-Bit is 1
        MC.control.pc = 1;
        MC.alu.wReg.write(new int[8]);
        MC.control.exe();
        assertArrayEquals(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }, MC.alu.wReg.read());
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
    }

    @Test // Eduard
    void testIORLW() {
        MC.pm.loadTestProgram(TP.s1);
        
        
        // Case Z-Bit is 0
        MC.control.pc=2; // 11 1000 0000 1101
        MC.alu.wReg.write(new int[] { 0,1,0,1,1,1,1,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,0,1,0,0,1,0}, MC.alu.wReg.read());
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        
        // Case Z-Bit is 1
        MC.control.pc=2;
        MC.alu.wReg.write(new int[] { 0,0,0,0,1,1,0,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, MC.alu.wReg.read());
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
    }
    
    @Test
    void testMOVLW() {
        MC.pm.loadTestProgram(TP.s4);
        MC.control.pc = 0; // 110000 00010001
        MC.alu.wReg.write(new int[8]);
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,1, 0,0,0,1}, MC.alu.wReg.read());
    }
        
    @Test //Eduard
    void testRETURN() {
        MC.pm.loadTestProgram(TP.s2);
        
        // Case tos is 0
        MC.control.pc = 7;
        MC.control.exe();
        assertEquals(1,MC.control.pc);
        
        // Case tos is 2
        MC.stack.push();
        MC.control.pc = 7;
        MC.control.exe();
        assertEquals(3,MC.control.pc);
    }
    
    @Test //Eduard
    void testRETFIE() {
        MC.pm.loadTestProgram(TP.s8);
        
        MC.control.pc = 27;
        MC.stack.push();
        
        MC.control.exe();
        
        assertEquals(28,MC.control.pc);
        assertEquals(1,MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 0));
    }
    
    @Test
    void testRETLW() {
        // setup
        MC.pm.loadTestProgram(TP.s10);        
        MC.alu.wReg.write(new int[8]);
        MC.control.pc = 15;
        MC.stack.push();
        
        SFR.setTOCS(0); // selects intClock
        SFR.setPSA(1); // psa -> wdog
        MC.ram.writeDataCell(SFR.TMR0.asIndex(), new int[8]);
        MC.timer.debug_clearDelay();
        MC.timer.debug_clearIncrCheck();
        
        // test
        MC.control.pc = 266; // 110100 01100000
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,1,0, 0,0,0,0}, MC.alu.wReg.read());
        assertEquals(16, MC.control.pc);
        assertArrayEquals(Utils.decToBinary(2, 8), MC.ram.readDataCell(SFR.TMR0.asIndex()));
    }
    
    @Test // Eduard
    void testSUBLW() {
        MC.pm.loadTestProgram(TP.s1);
        
        // Case normal Subtraction
        MC.control.pc = 3; // 11 1100 0011 1101 // subtract 61
        MC.alu.wReg.write(new int[] {1,1,1,1,1,1,1,1});
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,0,0,0,0,1,0}, MC.alu.wReg.read());
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        
        // Case Overflow
        MC.control.pc = 3; // 11 1100 0011 1101 // subtract 61
        MC.alu.wReg.write(new int[] {0,0,1,1,1,1,0,0});
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,1,1,1,1,1,1}, MC.alu.wReg.read());
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        
        // Case Zero
        MC.control.pc = 3; // 11 1100 0011 1101 // subtract 61
        MC.alu.wReg.write(new int[] {0,0,1,1,1,1,0,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, MC.alu.wReg.read());
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag        
    }
    
    @Test
    void testXORLW() {
        MC.pm.loadTestProgram(TP.s12);        
        MC.control.pc = 4; // 111010 11111111
        MC.alu.wReg.write(new int[] {0,0,1,1, 0,0,1,0});
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,0,0, 1,1,0,1}, MC.alu.wReg.read());
    }
}
