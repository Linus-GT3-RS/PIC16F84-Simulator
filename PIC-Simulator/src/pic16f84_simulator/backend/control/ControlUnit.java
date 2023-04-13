package pic16f84_simulator.backend.control;

import java.util.Arrays;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.control.instruction.Instruction;
import pic16f84_simulator.backend.control.instruction.LitConOps;
import pic16f84_simulator.backend.memory.Register;
import pic16f84_simulator.backend.tools.Utils;

public class ControlUnit {
    
    public static Register instrReg = new Register(14);
    public static int pc;


    public static void exe(Instruction instruct) {        
        int[] instrReg = ControlUnit.instrReg.readReg(); 
        
        if(instruct instanceof ByteOps instr) {            
          int dBit = instrReg[instr.indexDbit]  ;
          int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg, instr.fileStart, instrReg.length));          
          instr.exe(dBit, indexFile);
            
        }
        else if(instruct instanceof BitOps instr) {            
            int indexBit = Utils.binaryToDec(Arrays.copyOfRange(instrReg, instr.dBitStart, instr.dBitEnd+1));
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg, instr.fileStart, instrReg.length));
            instr.exe(indexBit, indexFile);            
        }
        else if(instruct  instanceof LitConOps instr){
            String k = Utils.cutArray(instrReg, instr.kStart(), instrReg.length-1);
            instr.exe(k);
        }
        ControlUnit.pc++;
    }
}
