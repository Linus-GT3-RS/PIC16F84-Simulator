package pic16f84_simulator;

import java.util.Arrays;

import pic16f84_simulator.backend.control.ByteOps;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.tools.Converter;

public class MicroController {
    public static ControlUnit cu = new ControlUnit();
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    public static int[] instrReg = new int[14];
    public static int[] wReg = new int[8];
    
    
    public static void exe(ByteOps instruct) {
        if(instruct == ByteOps.ADDWF) {
            if(instrReg[instruct.dBit] == 1) {
                int[] indexRAM_Binary = Arrays.copyOfRange(instrReg, instruct.fileStart, instruct.fileEnd+1);
                int indexRAM = Converter.binaryToDec(indexRAM_Binary);
                for(int i = 0; i<8;i++) {
                    int result = ram.readSpecificBit(indexRAM, i) + wReg[i];
                    ram.writeSpecificBit(indexRAM, i, result);
                }
            }
        }
    }
    
}
