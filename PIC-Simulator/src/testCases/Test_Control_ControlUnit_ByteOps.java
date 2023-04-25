package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.tools.TP;

class Test_Control_ControlUnit_ByteOps {

    @Test
    void testADDWF() {
        MicroC.pm.readTestProgram(TP.s3);
        
        //Case 1: Store in w-Reg #w = 0; #f = 0
        MicroC.control.pc=3; // 00 0111 0000 1100 d=0 -> Store in w-Reg
        MicroC.control.exe();
        assertArrayEquals(MicroC.calc.wReg,new int[] {0,0,0,0,0,0,0,0});
        
        //Case 2: Store in f-Reg #w = 1; #f = 1
        // pc -> 00 0111 1000 1100
        MicroC.ram.writeDataCell(12, new int[] {0,0,0,0,0,0,0,1});
        MicroC.calc.wReg = new int[] {0,0,0,0,0,0,0,1};
        MicroC.control.exe();
        assertArrayEquals(MicroC.calc.wReg,new int[] {0,0,0,0,0,0,1,0});
    }

}
