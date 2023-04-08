package pic16f84_simulator.backend.control.instruction;

public enum BitOps implements Instruction { // Eduard
    
    BCF, BSF, BTFSC, BTFSS;
    
    int opCEnd = 3;
    
    int bBitStart = 4;
    int bBitEnd = 6;
    
    int fileDataStart = 7;
    int fileDataEnd = 13;
}
