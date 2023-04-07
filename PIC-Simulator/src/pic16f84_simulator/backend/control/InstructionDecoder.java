package pic16f84_simulator.backend.control;

import pic16f84_simulator.backend.UnknownOpCodeException;

public class InstructionDecoder {
    
    public Instruction extractOpC(int[] instr) {
        
        Instruction result;
        String xx = ""; // first 2 Bits of instr
        
        if(xx.equals("00")) {
           if(first6bits equals "00 0000") {
               
           }
        }
        else if(xx.equals("01")) {
            
        }
        else if(xx.equals("11") || xx.equals("10")) {
            
        }
        else throw new UnknownOpCodeException("Instruction word unknown: this OpC does not exist in this CPU");
        
        return result;        
    }
    
    
    
    public BitOps giveBitOpC(String opC) {
        BitOps result;
        
        switch(opC) {
        case "0100" -> { result = BitOps.BCF; }
        case "0101" -> { result = BitOps.BSF; }
        case "0110" -> { result = BitOps.BTFSC; }
        case "0111" -> { result = BitOps.BTFSS; }
        default -> { throw new UnknownOpCodeException("Bit-oriented opCode unknown!"); }
        }
        return result;        
    }
    
    
    
    
    

}
