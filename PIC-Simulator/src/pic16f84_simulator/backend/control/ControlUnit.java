package pic16f84_simulator.backend.control;

import java.util.Arrays;

import pic16f84_simulator.MicroController;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.control.instruction.Instruction;
import pic16f84_simulator.backend.tools.Utils;

public class ControlUnit {
    
    


    public static void exe(Instruction instruct) {
        
//        if(instruct == ByteOps.ADDWF) {
//            if(instrReg.readBit(instruct.dBit) == 1) {
//                int[] indexRAM_Binary = Arrays.copyOfRange(instrReg.readReg(), instruct.fileStart, instruct.fileEnd+1);
//                int indexRAM = Utils.binaryToDec(indexRAM_Binary);
//                for(int i = 0; i<8;i++) {
//                    int result = ram.readSpecificBit(indexRAM, i) + wReg[i];
//                    ram.writeSpecificBit(indexRAM, i, result);
//                }
//            }
//        }
    }
}
