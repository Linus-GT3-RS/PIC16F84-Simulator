package pic16f84_simulator;

import java.util.Arrays;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.backend.memory.Register;

public class MicroController {
    public static ControlUnit cu = new ControlUnit();
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    public static Register instrReg = new Register(14);
    public static int[] wReg = new int[8];

    
    
    public static void exe(ByteOps instruct) {
        if(instruct == ByteOps.ADDWF) {
            if(instrReg.readBit(instruct.dBit) == 1) {
                int[] indexRAM_Binary = Arrays.copyOfRange(instrReg.readReg(), instruct.fileStart, instruct.fileEnd+1);
                int indexRAM = Utils.binaryToDec(indexRAM_Binary);
                for(int i = 0; i<8;i++) {
                    int result = ram.readSpecificBit(indexRAM, i) + wReg[i];
                    ram.writeSpecificBit(indexRAM, i, result);
                }
            }
        }
    


    
    
    // unnötig falls Register static
//    public Register getReg(Reg reg) {        
//        switch(reg) {
//        case INSTR -> { return this.instrReg;}
//        default -> { throw new UnknownLocationException("This Register is not known");}
//        }
//    }
    
    

    }}
