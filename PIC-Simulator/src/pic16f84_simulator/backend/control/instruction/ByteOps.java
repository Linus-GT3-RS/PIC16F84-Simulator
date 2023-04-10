package pic16f84_simulator.backend.control.instruction;

public enum ByteOps implements Instruction { // Linus

    ADDWF, ANDWF, CLRF, CLRW, COMF, DECF, DECFSZ, INCF, INCFSZ, IORWF, MOVF, 
    MOVWF, NOP, RLF, RRF, SUBWF, SWAPF, XORWF;
    
    //public int opCEnd = 5;
    
    public int dBit = 6;
    
    public int fileStart = 7;
    public int fileEnd = 13;


}
 