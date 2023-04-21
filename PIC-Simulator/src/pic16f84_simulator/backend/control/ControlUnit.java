package pic16f84_simulator.backend.control;

import java.util.Arrays;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.control.instruction.Instruction;
import pic16f84_simulator.backend.control.instruction.InstructionDecoder;
import pic16f84_simulator.backend.control.instruction.LitConOps;
import pic16f84_simulator.backend.memory.Register;
import pic16f84_simulator.backend.tools.Utils;

public class ControlUnit {

    private static boolean creationAllowed = true; // secures the creation of ONLY ONE instance of this class
    public ControlUnit() {
        if(ControlUnit.creationAllowed == false) {
            throw new IllegalArgumentException("Theres already an instance of this class!"); 
        }
        ControlUnit.creationAllowed = false;      
    }

    public static Register instrReg = new Register(14);
    public static int pc = 0;
    public static InstructionDecoder instrDecoder = new InstructionDecoder();

    
    public static void exe() { 
        // load instrReg
        instrReg.writeReg(MicroC.pm.readDataCell(pc));
        
        // load OpCode
        Instruction instruct = instrDecoder.extractOpC(instrReg.readReg());

        // execute OpCode
        if(instruct instanceof ByteOps instr) {            
            int dBit = instrReg.readBit(instr.indexDbit)  ;
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg.readReg(), instr.fileStart, instrReg.readReg().length));          
            instr.exe(dBit, indexFile);

        }
        else if(instruct instanceof BitOps instr) {            
            int indexBit = Utils.binaryToDec(Arrays.copyOfRange(instrReg.readReg(), instr.dBitStart, instr.dBitEnd+1));
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg.readReg(), instr.fileStart, instrReg.readReg().length));
            instr.exe(indexBit, indexFile);            
        }
        else if(instruct  instanceof LitConOps instr){
            String k = Utils.cutArray(instrReg.readReg(), instr.kStart(), instrReg.readReg().length-1);
            instr.exe(k);
        }
    }
}
