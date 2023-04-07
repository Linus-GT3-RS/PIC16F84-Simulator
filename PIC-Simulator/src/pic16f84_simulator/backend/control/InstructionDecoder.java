package pic16f84_simulator.backend.control;

import pic16f84_simulator.MicroController;
import pic16f84_simulator.backend.UnknownOpCodeException;
import pic16f84_simulator.backend.tools.Converter;

public class InstructionDecoder {

    public Instruction extractOpC(int[] instr) {
        Instruction result = null;

        switch(Converter.cutArray(instr, 0, 1)) { // first 2 Bits of instr
        case "00" -> { result = specialCase(Converter.cutArray(instr, 0, 5));}
        case "01" -> { result = giveBitOpC(Converter.cutArray(instr, 0, 3));}
        case "10" -> { result = giveLitConOpC(Converter.cutArray(instr, 0, 2));}
        case "11" -> { result = giveLitConOpC(Converter.cutArray(instr, 0, 5));}
        default -> { throw new UnknownOpCodeException("Instruction word unknown: this OpC does not exist in this CPU");}
        }
        return result;
    }
    
    
    
    
    // Hilfsmethode für extractOpC()
    Instruction specialCase (String opC) {
        if(opC.substring(0, 5).equals("00000")) { // Einer der 8 Sonderfälle --> ersten 5 Bits = 0
            return getSonderfallOpC(opC);
        }
        else return getByteOpC(opC); // kein Sonderfall --> einer der "normalen" ByteOpCodes
    }
    
    // Hilfsmethode für specialCase()
    Instruction getSonderfallOpC (String opC) {
        Instruction result;
        
        // Sonderfall_1 u. Sonderfall_2  aus ByteOp
        if(opC.equals("000001")) {             
            if(MicroController.instrReg.readBit(6) == 1) {
                result = ByteOps.CLRF;
            }
            else result = ByteOps.CLRW;            
        }
        
        // Sonderfall_3 bis Sonderfall_8 --> ersten 6Bits sind alle 0
        else {             
            int[] valInstrReg = MicroController.instrReg.readReg();
            String fullOpC = Converter.cutArray(valInstrReg, 0, valInstrReg.length-1);
            
            if(valInstrReg[6] == 1) { // Sonderfall_3 aus ByteOps
                result = ByteOps.MOVWF;
            }
            else { // Sonderfall_4 bis Sonderfall_8
                
                switch(fullOpC) {
                case "00000000000000" -> { result = ByteOps.NOP;} // Sonderfall_4 aus ByteOps
                
                // ab hier sind S_5 bis S_8 aus LitConOps
                case "00000001100100" -> { result = LitConOps.CLRWDT;}
                case "00000000001001" -> { result = LitConOps.RETFIE;}
                case "00000000001000" -> { result = LitConOps.RETURN;}
                case "00000001100011" -> { result = LitConOps.SLEEP;}
                default -> { throw new UnknownOpCodeException("OpCode unknown!");}
                }                
            } 
        }
        return result;        
    }
    
    
    // Hilfsmethode für specialCase()
    ByteOps getByteOpC (String opC) {        
        ByteOps result;
        
        switch(opC) {
        case "000111" -> { result = ByteOps.ADDWF;}
        case "000101" -> { result = ByteOps.ANDWF;}
        case "001001" -> { result = ByteOps.COMF;}
        case "000011" -> { result = ByteOps.DECF;}
        case "001011" -> { result = ByteOps.DECFSZ;}
        case "001010" -> { result = ByteOps.INCF;}
        case "001111" -> { result = ByteOps.INCFSZ;}
        case "000100" -> { result = ByteOps.IORWF;}
        case "001000" -> { result = ByteOps.MOVF;}
        case "001101" -> { result = ByteOps.RLF;}
        case "001100" -> { result = ByteOps.RRF;}
        case "000010" -> { result = ByteOps.SUBWF;}
        case "001110" -> { result = ByteOps.SWAPF;}
        case "000110" -> { result = ByteOps.XORWF;}
        default -> { throw new UnknownOpCodeException("Byte-oriented OpCode unknown!");}
        }
        return result;
    }
    
        
    
    
    
    // Hilfsmethode für extractOpC()
    BitOps giveBitOpC (String opC) {
        BitOps result;

        switch(opC) {
        case "0100" -> { result = BitOps.BCF;}
        case "0101" -> { result = BitOps.BSF;}
        case "0110" -> { result = BitOps.BTFSC;}
        case "0111" -> { result = BitOps.BTFSS;}
        default -> { throw new UnknownOpCodeException("Bit-oriented OpCode unknown!");}
        }
        return result;        
    }


    // Hilfsmethode für extractOpC()
    LitConOps giveLitConOpC (String opC) {
        LitConOps result;

        switch(opC) {

        // ab hier ist xx = 10 => OpC nur 3Bits lang
        case "100" -> { result = LitConOps.CALL;} 
        case "101" -> { result = LitConOps.GOTO;}

        // ab hier ist xx = 11 => OpC ist 6Bits lang
        case "111110" -> { result = LitConOps.ADDLW;}
        case "111001" -> { result = LitConOps.ANDLW;}
        case "111000" -> { result = LitConOps.IORLW;}
        case "110000" -> { result = LitConOps.MOVLW;}
        case "110100" -> { result = LitConOps.RETLW;}
        case "111100" -> { result = LitConOps.SUBLW;}
        case "111010" -> { result = LitConOps.XORLW;}
        default -> { throw new UnknownOpCodeException("Lit&Con-oriented OpCode unknown!");}
        }
        return result;
    }
    

    
}
