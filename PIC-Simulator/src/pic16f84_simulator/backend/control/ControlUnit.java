package pic16f84_simulator.backend.control;
import java.util.Arrays;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.control.instruction.Instruction;
import pic16f84_simulator.backend.control.instruction.InstructionDecoder;
import pic16f84_simulator.backend.control.instruction.LitConOps;
import pic16f84_simulator.backend.control.tmv.Prescaler;
import pic16f84_simulator.backend.memory.Register;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class ControlUnit {

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public ControlUnit() {
        allow = Utils.allow(allow, this);      
    }

    public int pc = 0;
    public Register instrReg = new Register(14);
    public InstructionDecoder instrDecoder = new InstructionDecoder();
    public Prescaler prescaler = new Prescaler();

    
    public void exe() {        
        instrReg.writeReg(MC.pm.readDataCell(pc)); // load instrReg
        Instruction instruct = instrDecoder.extractOpC(instrReg.readReg()); // load OpCode

        if(instruct instanceof ByteOps instr) {            
            int dBit = instrReg.readBit(instr.indexDbit)  ;
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg.readReg(), instr.fileStart, instrReg.readReg().length));    
            if(indexFile == 0)
            {
                indexFile = Utils.binaryToDec(MC.ram.readDataCell(SFR.FSR.asIndex()));
            }
            instr.exe(dBit, indexFile);
        }
        if(instruct instanceof BitOps instr) {            
            int indexBit = Utils.binaryToDec(Arrays.copyOfRange(instrReg.readReg(), instr.dBitStart, instr.dBitEnd+1));
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg.readReg(), instr.fileStart, instrReg.readReg().length));
            if(indexFile == 0)
            {
                indexFile = Utils.binaryToDec(MC.ram.readDataCell(SFR.FSR.asIndex()));
            }
            instr.exe(indexBit, indexFile);            
        }
        if(instruct instanceof LitConOps instr){
            int[] k = new int[14-instr.kStart()];
            System.arraycopy(instrReg.readReg(),instr.kStart(),  k, 0, (14-instr.kStart()));
            instr.exe(k);
        }
        pcpp();
        MC.timer.tryIncrInternalTimer();
    }
    
    // Increase the PC-Counter and load in specific register PCL and PCLATH
    public void pcpp() {
        pc++;
        int[] pclBinary = Utils.decToBinary(pc, 13);
        MC.ram.writeDataCell(SFR.PCL.asIndex(), Arrays.copyOfRange(pclBinary, 5, 13));
        int[] pclLatchBinary = new int[8];
        System.arraycopy(pclBinary, 0, pclLatchBinary, 3, 5);
        MC.ram.writeDataCell(SFR.PCLATH.asIndex(),pclLatchBinary);
    }
    
    
}
