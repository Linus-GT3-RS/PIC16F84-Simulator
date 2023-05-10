package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.Utils;

class Test_Control_ControlUnit_ByteOps {

    @Test // Eduard
    void testADDWF() { 
        MC.pm.readTestProgram(TP.s3);

        //Case 1: Store in w-Reg #w = 0; #f = 0
        MC.control.pc=3; // 00 0111 0000 1100 d=0 -> Store in w-Reg
        MC.control.exe();
        assertArrayEquals(MC.alu.wReg,new int[] {0,0,0,0,0,0,0,0});

        //Case 2: Store in f-Reg #w = 1; #f = 1
        // pc -> 00 0111 1000 1100
        MC.control.pc=3;
        MC.ram.writeDataCell(12, new int[] {0,0,0,0,0,0,0,1});
        MC.alu.wReg = new int[] {0,0,0,0,0,0,0,1};
        MC.control.exe();
        assertArrayEquals(MC.alu.wReg,new int[] {0,0,0,0,0,0,1,0});
    }

    @Test // Linus
    void testANDWF() { 
        MC.pm.readTestProgram(TP.s3);

        // Case1: dBit=0
        MC.control.pc = 5; // 000101 0 0001100
        MC.alu.wReg = new int[] {1,0,1,0,1,0,1,0};
        MC.ram.writeDataCell(12, new int[] {0,0,1,0,1,0,1,0});
        MC.control.exe();
        assertArrayEquals(MC.alu.wReg, new int[] {0,0,1,0,1,0,1,0});

        // Case2: dBit=1
        MC.pm.writeDataCell(5, new int[] {0,0,0,1,0,1, 1 ,0,0,0,1,1,0,0}); // overrides current instruction in pm cause there is no other testCase
        MC.control.pc = 5;
        MC.ram.writeDataCell(12, new int[] {0,1,0,0,1,0,0,1}); // wReg from previous op remains
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 1);
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12), new int[] {0,0,0,0,1,0,0,0});
        assertEquals(MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5), 0);

        // second test for Z-Bit in Status
        MC.pm.writeDataCell(5, new int[] {0,0,0,1,0,1, 1 ,0,0,0,1,1,0,0}); // overrides current instruction in pm cause there is no other testCase
        MC.control.pc = 5;
        MC.ram.writeDataCell(12, new int[8]); // wReg from previous op remains
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 0);
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12), new int[8]);
        assertEquals(MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5), 1);
    }

    @Test // Eduard
    void testCLRF() {
        MC.pm.readTestProgram(TP.s3);
        MC.ram.writeDataCell(12, new int[] {1,1,1,1,1,1,1,1});
        assertArrayEquals(MC.ram.readDataCell(12),new int[] {1,1,1,1,1,1,1,1});
        MC.control.pc=7; // 00 0001 1000 1100
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12),new int[] {0,0,0,0,0,0,0,0});
    }

    @Test // Linus
    void testCLRW() {
        MC.pm.readTestProgram(TP.s3);        
        MC.control.pc = 16;        
        MC.alu.wReg = new int[] {1,0,1,0,0,1,1,0}; // random val
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 0);
        MC.control.exe();

        assertArrayEquals(MC.alu.wReg, new int[8]);
        assertEquals(MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5), 1);
    }

    @Test // Eduard
    void testCOMF() {
        MC.pm.readTestProgram(TP.s3);
        MC.ram.writeDataCell(13, new int[] {0,1,1,1,0,0,1,0});
        MC.control.pc=8; // 00 1001 0000 1101 -> d=0 f=13
        MC.control.exe();
        assertArrayEquals(new int[] {1,0,0,0,1,1,0,1},MC.alu.wReg);
    }
    
    @Test
    void testDECF() {
        MC.pm.readTestProgram(TP.s3);
        MC.control.pc = 9; // 000011 0 0001100
        int oldContentF = Utils.binaryToDec(MC.ram.readDataCell(12));
        MC.control.exe(); // ihn hauts im conv decToBin raus, weil wir ne neg. Zahl versuchen zu konvertieren -> mit Eduard klären
        int res = Utils.binaryToDec(MC.alu.wReg);
        assertEquals(oldContentF - 1, res);
    }
    
    @Test  // Eduard
    void testDECFSZ() {
        MC.pm.readTestProgram(TP.s4);

        // Case result is not 0
        MC.ram.writeDataCell(12,new int[] {0,0,0,0,0,0,1,0});
        MC.control.pc=18;// 00 1011 1000 1100 -> d=1 f=12
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,1},MC.ram.readDataCell(12));
        assertEquals(MC.control.pc,19);

        // Case result is 0
        MC.control.pc=18;
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0},MC.ram.readDataCell(12));
        assertEquals(MC.control.pc,20);
    }

    @Test  // Eduard
    void testINCFSZ() {
        MC.pm.readTestProgram(TP.s4);

        // Case result is not 0
        MC.ram.writeDataCell(12, new int[] {0,0,0,0,0,0,1,1});
        MC.control.pc=26;// 00 1111 1000 1100
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,0},MC.ram.readDataCell(12));
        assertEquals(MC.control.pc,27);
    }

    @Test // Eduard
    void testMOVF() {
        MC.pm.readTestProgram(TP.s6);

        MC.ram.writeDataCell(12, new int[] {0,1,1,0,1,0,1,0});
        MC.control.pc=5;// 00 1000 0000 1100
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,0},MC.alu.wReg);
    }
    
    @Test // Eduard
    void testRRF() {
        MC.pm.readTestProgram(TP.s6);
        
        // Case 1: Cary is 1
        MC.ram.writeDataCell(0, new int[] {0,0,0,0,1,1,1,1});
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 0, 1);
        MC.control.pc=22;// 00 1100 1000 0000
        MC.control.exe();
        assertArrayEquals(new int[] {1,0,0,0,0,1,1,1},MC.ram.readDataCell(0));
        assertEquals(SFR.status_getC(),1);
        
        // Case 2: Carry is 0
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 0, 0);
        MC.control.pc=22;
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,0,0,0,0,1,1},MC.ram.readDataCell(0));
    }
    
    @Test // Eduard
    void testSWAPF() {
        MC.pm.readTestProgram(TP.s6);
        
        MC.ram.writeDataCell(0, new int[] {1,1,0,0,0,0,1,1});
        MC.control.pc=30;// 00 1110 1000 0000
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,1,1,1,1,0,0},MC.ram.readDataCell(0));
    }
}
