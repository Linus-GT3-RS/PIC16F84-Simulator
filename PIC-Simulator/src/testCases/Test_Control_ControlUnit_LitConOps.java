package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.TP;

class Test_Control_ControlUnit_LitConOps {

    @Test // Eduard
    void testANDLW() {
        MC.pm.readTestProgram(TP.s1);

        // Case Z-Bit is 0
        MC.control.pc = 1; // 11 1001 0011 0000
        MC.alu.wReg = new int[] { 0, 1, 0, 1, 0, 1, 0, 0 };
        MC.control.exe();
        assertArrayEquals(new int[] { 0, 0, 0, 1, 0, 0, 0, 0 }, MC.alu.wReg);
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag

        // Case Z-Bit is 1
        MC.control.pc = 1;
        MC.alu.wReg = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        MC.control.exe();
        assertArrayEquals(new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }, MC.alu.wReg);
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
    }

    @Test // Eduard
    void testIORLW() {
        MC.pm.readTestProgram(TP.s1);
        
        
        // Case Z-Bit is 0
        MC.control.pc=2; // 11 1000 0000 1101
        MC.alu.wReg = new int[] { 0,1,0,1,1,1,1,1};
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,0,1,0,0,1,0},MC.alu.wReg);
        assertEquals(0, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
        
        // Case Z-Bit is 1
        MC.control.pc=2;
        MC.alu.wReg = new int[] { 0,0,0,0,1,1,0,1};
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0},MC.alu.wReg);
        assertEquals(1, MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); // Z-Flag
    }
}
