package testCases;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.TP;

class Test_Control_ControlUnit_LitConOps {

    @Test // Eduard
    void testANDLW() {
        MC.pm.loadTestProgram(TP.s1);

        // Case Z-Bit is 0
        MC.control.pc = 1; // 11 1001 0011 0000
        MC.alu.wReg.writeReg(new int[] { 0, 1, 0, 1, 0, 1, 0, 0 });
        MC.control.exe();
        assertArrayEquals(new int[] { 0, 0, 0, 1, 0, 0, 0, 0 }, MC.alu.wReg.readReg());
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag

        // Case Z-Bit is 1
        MC.control.pc = 1;
        MC.alu.wReg.writeReg(new int[8]);
        MC.control.exe();
        assertArrayEquals(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }, MC.alu.wReg.readReg());
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
    }

    @Test // Eduard
    void testIORLW() {
        MC.pm.loadTestProgram(TP.s1);
        
        
        // Case Z-Bit is 0
        MC.control.pc=2; // 11 1000 0000 1101
        MC.alu.wReg.writeReg(new int[] { 0,1,0,1,1,1,1,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,0,1,0,0,1,0}, MC.alu.wReg.readReg());
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        
        // Case Z-Bit is 1
        MC.control.pc=2;
        MC.alu.wReg.writeReg(new int[] { 0,0,0,0,1,1,0,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, MC.alu.wReg.readReg());
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
    }
    
    @Test // Eduard
    void testSUBLW() {
        MC.pm.readTestProgram(TP.s1);
        
        // Case normal Subtraction
        MC.control.pc = 3; // 11 1100 0011 1101 // subtract 61
        MC.alu.wReg = new int[] {1,1,1,1,1,1,1,1};
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,0,0,0,0,1,0},MC.alu.wReg);
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        
        // Case Overflow
        MC.control.pc = 3; // 11 1100 0011 1101 // subtract 61
        MC.alu.wReg = new int[] {0,0,1,1,1,1,0,0};
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,1,1,1,1,1,1},MC.alu.wReg);
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        
        // Case Zero
        MC.control.pc = 3; // 11 1100 0011 1101 // subtract 61
        MC.alu.wReg = new int[] {0,0,1,1,1,1,0,1};
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0},MC.alu.wReg);
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        
    }
}
