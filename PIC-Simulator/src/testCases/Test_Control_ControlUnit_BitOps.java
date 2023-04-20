package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;

class Test_Control_ControlUnit_BitOps {

    @Test
    void testBCF() {
        MicroC mc = new MicroC();
        mc.pm.writeSpecificBit(15, 3, 0); // 01 00
        mc.pm.
        mc.cu.exe();
    }

}
