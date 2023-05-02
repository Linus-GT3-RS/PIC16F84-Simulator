package testCases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pic16f84_simulator.*;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.ByteOps;

class Test_MicroController_ControllUnit {

    @Test
    void testInstructionADDWF() {
        MC.alu.wReg = new int[] {0,0,1,1,0,0,1,1};
        MC.ram.writeDataCell(17, new int[] {1,1,0,0,1,1,0,0});
        MC.control.instrReg.writeReg(new int[] {0,0,0,1,1,1,1,0,0,1,0,0,0,1});
//        MicroController.exe(ByteOps.ADDWF);
        assertArrayEquals(MC.ram.readDataCell(17),new int[] {1,1,1,1,1,1,1,1});
    }

}
