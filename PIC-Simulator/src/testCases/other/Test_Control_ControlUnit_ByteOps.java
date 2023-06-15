package testCases.other;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.Utils;

class Test_Control_ControlUnit_ByteOps {

    @Test // Eduard
    void testADDWF() {
        MC.pm.loadTestProgram(TP.s3);

        //Case 1: Store in w-Reg #w = 8; #f = 8
        MC.alu.wReg.write(new int[] {0,0,0,0,1,0,0,0});
        MC.ram.writeDataCell(12, new int[] {0,0,0,0,1,0,0,0});
        MC.control.pc(3); // 00 0111 0000 1100 d=0 -> Store in w-Reg
        MC.control.exe();
        assertArrayEquals(MC.alu.wReg.read(), new int[] {0,0,0,1,0,0,0,0});
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); //Z-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag

        //Case 2: Store in f-Reg #w = 1; #f = 1
        MC.control.pc(4);//00 0111 1000 1100 d=1 -> Store in f-Reg
        MC.ram.writeDataCell(12, new int[] {0,0,0,0,0,0,0,1});
        MC.alu.wReg.write(new int[] {0,0,0,0,0,0,0,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,1,0},MC.ram.readDataCell(12));
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 6)); //DC-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); //Z-Flag
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,1},MC.ram.readDataCell(SFR.PCL.asIndex()));
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0},MC.ram.readDataCell(SFR.PCLATH.asIndex()));
    }

    @Test // Linus
    void testANDWF() { 
        MC.pm.loadTestProgram(TP.s3);

        // Case1: dBit=0
        MC.control.pc(5); // 000101 0 0001100
        MC.alu.wReg.write(new int[] {1,0,1,0,1,0,1,0});
        MC.ram.writeDataCell(12, new int[] {0,0,1,0,1,0,1,0});
        MC.control.exe();
        assertArrayEquals(MC.alu.wReg.read(), new int[] {0,0,1,0,1,0,1,0});

        // Case2: dBit=1
        MC.pm.writeDataCell(5, new int[] {0,0,0,1,0,1, 1 ,0,0,0,1,1,0,0}); // overrides current instruction in pm cause there is no other testCase
        MC.control.pc(5);
        MC.ram.writeDataCell(12, new int[] {0,1,0,0,1,0,0,1}); // wReg from previous op remains
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 1);
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12), new int[] {0,0,0,0,1,0,0,0});
        assertEquals(MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5), 0);

        // second test for Z-Bit in Status
        MC.pm.writeDataCell(5, new int[] {0,0,0,1,0,1, 1 ,0,0,0,1,1,0,0}); // overrides current instruction in pm cause there is no other testCase
        MC.control.pc(5);
        MC.ram.writeDataCell(12, new int[8]); // wReg from previous op remains
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 0);
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12), new int[8]);
        assertEquals(MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5), 1);
    }

    @Test // Eduard
    void testCLRF() {
        MC.pm.loadTestProgram(TP.s3);
        MC.ram.writeDataCell(12, new int[] {1,1,1,1,1,1,1,1});
        assertArrayEquals(MC.ram.readDataCell(12),new int[] {1,1,1,1,1,1,1,1});
        MC.control.pc(7); // 00 0001 1000 1100
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12),new int[] {0,0,0,0,0,0,0,0});
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); //Z-Flag
    }

    @Test // Linus
    void testCLRW() {
        MC.pm.loadTestProgram(TP.s3);        
        MC.control.pc(16);        
        MC.alu.wReg.write(new int[] {1,0,1,0,0,1,1,0}); // random val
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 0);
        MC.control.exe();

        assertArrayEquals(MC.alu.wReg.read(), new int[8]);
        assertEquals(MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5), 1);
    }

    @Test // Eduard
    void testCOMF() {
        MC.pm.loadTestProgram(TP.s3);
        MC.ram.writeDataCell(13, new int[] {0,1,1,1,0,0,1,0});
        MC.control.pc(8); // 00 1001 0000 1101 -> d=0 f=13
        MC.control.exe();
        assertArrayEquals(new int[] {1,0,0,0,1,1,0,1}, MC.alu.wReg.read());
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); //Z-Flag
    }

    @Test
    void testDECF() { // Linus
        MC.pm.loadTestProgram(TP.s3);

        MC.control.pc(9); // 000011 0 0001100
        MC.ram.writeDataCell(12, new int[8]);
        MC.control.exe();
        assertEquals(255, Utils.binaryToDec(MC.alu.wReg.read()));

        MC.control.pc(9); // 000011 0 0001100
        MC.ram.writeDataCell(12, new int[] {1,0,0,1,1,0,1,1});
        MC.control.exe();
        assertEquals(154, Utils.binaryToDec(MC.alu.wReg.read()));
    }

    @Test  // Eduard
    void testDECFSZ() {
        MC.pm.loadTestProgram(TP.s4);

        // Case result is not 0
        MC.ram.writeDataCell(12,new int[] {0,0,0,0,0,0,1,0});
        MC.control.pc(18);// 00 1011 1000 1100 -> d=1 f=12
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,1},MC.ram.readDataCell(12));
        assertEquals(MC.control.pc(), 19);

        // Case result is 0
        MC.control.pc(18);
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0},MC.ram.readDataCell(12));
        assertEquals(20, MC.control.pc());

        // Case result is -1
        MC.control.pc(18);
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,1,1,1,1,1,1}, MC.ram.readDataCell(12));
    }

    @Test
    void testINCF() { // Linus
        MC.pm.loadTestProgram(TP.s3);        
        MC.control.pc(10); // 001010 1 0001101
        int expc1 = Utils.binaryToDec(MC.ram.readDataCell(13)) + 1;
        expc1 = fixScope(expc1);
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(13), Utils.decToBinary(expc1, 8));

        MC.control.pc(10); // 001010 1 0001101
        MC.ram.writeDataCell(13, new int[] {1,1,1,1,1,1,1,1});
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(13), new int[8]);
        assertEquals(SFR.getZflag(), 1);
    }

    @Test  // Eduard
    void testINCFSZ() {
        MC.pm.loadTestProgram(TP.s4);

        // Case result is not 0
        MC.ram.writeDataCell(12, new int[] {0,0,0,0,0,0,1,1});
        MC.control.pc(26);// 00 1111 1000 1100
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,1,0,0},MC.ram.readDataCell(12));
        assertEquals(MC.control.pc(), 27);
    }

    @Test
    void testIORWF() { // Linus
        MC.pm.loadTestProgram(TP.s3);    

        MC.control.pc(12); // 000100 1 0001100=12
        MC.alu.wReg.write(new int[8]);
        MC.ram.writeDataCell(12, new int[] {1,0,0,1, 1,1,0,1});
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12), new int[] {1,0,0,1, 1,1,0,1});

        MC.control.pc(12); // 000100 1 0001100=12
        MC.alu.wReg.write(new int[] {0,1,1,1, 0,0,0,1});
        MC.control.exe();
        assertArrayEquals(MC.ram.readDataCell(12), new int[] {1,1,1,1, 1,1,0,1});
    }

    @Test // Eduard
    void testMOVF() {
        MC.pm.loadTestProgram(TP.s6);

        //Case: Z-Flag is 0
        MC.ram.writeDataCell(12, new int[] {0,1,1,0,1,0,1,0});
        MC.control.pc(5);// 00 1000 0000 1100
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,1,0,1,0,1,0}, MC.alu.wReg.read());
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); //Z-Flag

        //Case: Z-Flag is 1
        MC.ram.writeDataCell(12, new int[] {0,0,0,0,0,0,0,0});
        MC.control.pc(5);// 00 1000 0000 1100
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, MC.alu.wReg.read());
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 5)); //Z-Flag
    }

    @Test
    void testMOVWF() { // Linus
        MC.pm.loadTestProgram(TP.s3);

        MC.control.pc(1); // 000000 1 0001100=12
        MC.alu.wReg.write(new int[] {1,1,0,0,1,1,0,1});
        MC.control.exe();
        assertArrayEquals(new int[] {1,1,0,0,1,1,0,1}, MC.ram.readDataCell(12));

        MC.control.pc(6); // 000000 1 0001101=13
        MC.alu.wReg.write(new int[] {1,0,0,0,1,1,1,1});
        MC.control.exe();
        assertArrayEquals(new int[] {1,0,0,0,1,1,1,1}, MC.ram.readDataCell(13));
    }

    @Test
    void testRLF() { // Linus
        MC.pm.loadTestProgram(TP.s4);        
        MC.control.pc(3); // 001101 1 0001100=12

        MC.ram.writeDataCell(12, new int[] {1,0,1,0,1,0,1,0});
        SFR.setCflag(0);
        MC.control.exe();
        assertEquals(1, SFR.getCflag());
        assertArrayEquals(new int[] {0,1,0,1,0,1,0, 0}, MC.ram.readDataCell(12));
    }

    @Test // Eduard
    void testRRF() {
        MC.pm.loadTestProgram(TP.s6);

        // Case 1: Carry is 1

        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 7, 1);
        MC.control.pc(22);// 00 1100 1000 0000 // Indirect Adressing -> 0
        MC.ram.writeDataCell(0, new int[] {0,0,0,0,1,1,1,0});
        MC.control.exe();
        assertArrayEquals(new int[] {1,0,0,0,0,1,1,1},MC.ram.readDataCell(0));
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag

        // Case 2: Carry is 0
        MC.control.pc(22);
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,0,0,0,0,1,1},MC.ram.readDataCell(0));
        assertEquals(1,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 7)); //C-Flag
    }

    @Test
    void testSUBWF() { // Linus
        MC.pm.loadTestProgram(TP.s3);        
        MC.control.pc(17); // 000010 0 0001100=12
        MC.ram.writeDataCell(12, new int[] {1,1,1,1, 1,1,1,1}); // 255
        MC.alu.wReg.write(new int[] {1,0,1,0, 1,0,1,0}); // 170
        MC.control.exe();
        assertArrayEquals(new int[] {0,1,0,1, 0,1,0,1}, MC.alu.wReg.read()); // f - w = 85
    }

    @Test // Eduard
    void testSWAPF() {
        MC.pm.loadTestProgram(TP.s6);

        MC.control.pc(30);// 00 1110 1000 0000 // -> indirect address
        SFR.setFSR(15);
        MC.ram.writeDataCell(15, new int[] {1,1,0,0,0,0,1,1});
        MC.control.exe();
        assertArrayEquals(new int[] {0,0,1,1,1,1,0,0},MC.ram.readDataCell(15));
    }

    @Test
    void testXORWF() {
        MC.pm.loadTestProgram(TP.s6);        
        MC.control.pc(31); // 000110 1 0000000
        SFR.setFSR(25);
        MC.ram.writeDataCell(25, new int[] {0,1,0,1, 1,1,1,0});
        MC.alu.wReg.write(new int[] {1,1,0,0, 0,0,1,1});
        MC.control.exe();
        assertArrayEquals(new int[] {1,0,0,1, 1,1,0,1}, MC.ram.readDataCell(25));
    }


    private static int fixScope(int res) { // Linus
        if(res > 255) {
            return res - 256; 
        }
        else if(res < 0) {
            return res + 256;
        }
        else return res;
    }

}
