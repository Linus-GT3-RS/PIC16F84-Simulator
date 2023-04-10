package pic16f84_simulator.backend.control.instruction;

public enum ByteOps implements Instruction { // Linus

    ADDWF, ANDWF, CLRF, CLRW, COMF, DECF, DECFSZ, INCF, INCFSZ, IORWF, MOVF, 
    MOVWF, NOP, RLF, RRF, SUBWF, SWAPF, XORWF;
    
    int opCEnd = 5;
    
    int dBit = 6;
    
    int fileStart = 7;
    int fileEnd = 13;


}
 