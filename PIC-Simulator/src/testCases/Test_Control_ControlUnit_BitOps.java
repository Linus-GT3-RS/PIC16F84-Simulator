package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.Utils;

class Test_Control_ControlUnit_BitOps {

    @Test
    void testBCF() {
        MicroC mc = new MicroC();
        MicroC.pm.readTestProgram(TP.s5);
        MicroC.cu.loadInstrReg(5);
        assertArrayEquals(mc.pm.readDataCell(5), mc.cu.instrReg.readReg()); // 01 0010 0000 1100 -> 01 00bb bfff ffff
        MicroC.cu.exe();
        mc.ram.checkBit(Utils.binaryToDec(new int[] {0,0,0,1,1,0,0}),0);
    }
    
    @Test
    void testBSF() {
        ControlUnit.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        ControlUnit.exe(BitOps.BSF);
        assertArrayEquals(new int[] {0,1,0,0,0,0,0,0}, MicroC.ram.readDataCell(32));
    }
    
    @Test
    void testBTFSS() {
        ControlUnit.instrReg.writeReg(new int[] {0,0,0,0, 0,0,1, 0,1,0,0,0,0,0});
        ControlUnit.exe();
    }
    
    @Test
    void testBTFSC() {
        MicroC mc = new MicroC();
        MicroC.pm.readTestProgram(TP.s5);
        MicroC.cu.pc = 7;
        MicroC.cu.loadInstrReg(7);
        MicroC.cu.exe();
        assertEquals(MicroC.cu.pc,9);
        
    }

}
