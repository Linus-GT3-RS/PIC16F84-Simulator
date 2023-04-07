package pic16f84_simulator.backend.control;

public enum LitConOps implements Instruction { // Linus

    ADDLW, ANDLW, CALL, CLRWDT, GOTO, IORLW, MOVLW, RETFIE, RETLW, RETURN, 
    SLEEP, SUBLW, XORLW;
        
    public int opCEnd() {
        if(this == CALL || this == GOTO) {
            return 2;
        }
        else return 5;
    }    
    
    
    public int kStart() {
        if(this == CALL || this == GOTO) {
            return 3;
        }
        else return 6;
    }    
    int kEnd = 13;
    
}
