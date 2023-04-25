package testCases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.control.instruction.InstructionDecoder;
import pic16f84_simulator.backend.control.instruction.LitConOps;
import pic16f84_simulator.backend.tools.UnknownOpCodeException;

class Test_Control_InstructionDecoder {

    @Test
    void test_ExtractOpC_SpecialCases() { // 8 Sonderfälle
        ControlUnit cUnit = MicroC.control;
        InstructionDecoder decoder =  cUnit.instrDecoder;
        
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,1 ,1 ,0,0,0,0,0,1,0});
        assertEquals(ByteOps.CLRF, decoder.extractOpC(new int[] {0,0,0,0,0,1 ,1 ,0,0,0,0,0,1,0}));
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,1 ,1 ,0,1,1,1,0,1,0});
        assertEquals(ByteOps.CLRF, decoder.extractOpC(new int[] {0,0,0,0,0,1 ,1 ,0,1,1,1,0,1,0}));
        
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,1 ,0 ,0,1,1,1,0,1,0});
        assertEquals(ByteOps.CLRW, decoder.extractOpC(new int[] {0,0,0,0,0,1 ,0 ,0,1,1,1,0,1,0}));
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,1 ,0 ,1,0,0,1,0,1,0});
        assertEquals(ByteOps.CLRW, decoder.extractOpC(new int[] {0,0,0,0,0,1 ,0 ,1,0,0,1,0,1,0}));
        
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,1 ,1,0,0,1,0,1,0});
        assertEquals(ByteOps.MOVWF, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,1 ,1,0,0,1,0,1,0}));
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,1 ,0,1,1,1,0,1,0});
        assertEquals(ByteOps.MOVWF, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,1 ,0,1,1,1,0,1,0}));
        
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,0,0,0,0,0,0,0,0});
        assertEquals(ByteOps.NOP, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,0,0,0,0,0,0,0,0}));
        
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,0,1,1,0,0,1,0,0});
        assertEquals(LitConOps.CLRWDT, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,0,1,1,0,0,1,0,0}));
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,0,0,0,0,1,0,0,1});
        assertEquals(LitConOps.RETFIE, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,0,0,0,0,1,0,0,1}));
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,0,0,0,0,1,0,0,0});
        assertEquals(LitConOps.RETURN, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,0,0,0,0,1,0,0,0}));
        cUnit.instrReg.writeReg(new int[] {0,0,0,0,0,0 ,0,1,1,0,0,0,1,1});
        assertEquals(LitConOps.SLEEP, decoder.extractOpC(new int[] {0,0,0,0,0,0 ,0,1,1,0,0,0,1,1}));   
    }
    
    
    @Test
    void test_ExtractOpC_NormalCases() {
        InstructionDecoder decoder = MicroC.control.instrDecoder;        
        assertThrows(IllegalArgumentException.class, () -> 
                        { decoder.extractOpC(new int[] {1,1,0}); });
                
        assertEquals(ByteOps.DECF, decoder.extractOpC(new int[] {0,0,0,0,1,1 ,1,0,0,1,0,1,0,1}));
        assertEquals(ByteOps.DECF, decoder.extractOpC(new int[] {0,0,0,0,1,1 ,0,1,1,1,0,1,0,1}));
        assertEquals(ByteOps.MOVF, decoder.extractOpC(new int[] {0,0,1,0,0,0 ,0,1,1,1,0,1,0,1}));
        assertEquals(ByteOps.MOVF, decoder.extractOpC(new int[] {0,0,1,0,0,0 ,1,0,0,1,0,1,0,1}));
        
        assertEquals(BitOps.BSF, decoder.extractOpC(new int[] {0,1,0,1 ,1,0,1,0,0,1,0,1,0,1}));
        assertEquals(BitOps.BSF, decoder.extractOpC(new int[] {0,1,0,1 ,0,1,1,0,0,1,0,1,0,1}));
        assertEquals(BitOps.BTFSS, decoder.extractOpC(new int[] {0,1,1,1 ,1,0,1,0,0,1,0,1,0,1}));
        assertEquals(BitOps.BTFSS, decoder.extractOpC(new int[] {0,1,1,1 ,1,1,0,0,0,1,0,1,0,1}));
        
        assertEquals(LitConOps.CALL, decoder.extractOpC(new int[] {1,0,0 ,1,1,1,0,0,0,1,0,1,0,1}));
        assertEquals(LitConOps.CALL, decoder.extractOpC(new int[] {1,0,0 ,0,1,1,1,0,0,1,0,1,0,1}));
        assertEquals(LitConOps.GOTO, decoder.extractOpC(new int[] {1,0,1 ,0,1,1,1,0,0,1,0,1,0,1}));
        assertEquals(LitConOps.GOTO, decoder.extractOpC(new int[] {1,0,1 ,1,1,1,1,1,0,1,0,1,0,1}));
        
        assertEquals(LitConOps.MOVLW, decoder.extractOpC(new int[] {1,1,0,0,0,0 ,1,1,0,1,0,1,0,1}));
        assertEquals(LitConOps.MOVLW, decoder.extractOpC(new int[] {1,1,0,0,0,0 ,1,0,0,0,1,1,0,1}));
        assertEquals(LitConOps.XORLW, decoder.extractOpC(new int[] {1,1,1,0,1,0 ,1,0,0,0,1,1,0,1}));
        assertEquals(LitConOps.XORLW, decoder.extractOpC(new int[] {1,1,1,0,1,0 ,0,0,0,0,1,0,0,1}));
        
        assertThrows(UnknownOpCodeException.class, () -> { decoder.extractOpC(new int[] {1,2,0,1,0,1,0,1,1,0,1,1,0,0}); });
    }

}
