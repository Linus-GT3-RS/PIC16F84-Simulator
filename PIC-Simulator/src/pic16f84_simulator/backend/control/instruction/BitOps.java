package pic16f84_simulator.backend.control.instruction;

public enum BitOps implements Instruction { // Eduard
    
    BCF { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    }, BSF { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    }, BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    }, BTFSS { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    };
    
    //int opCEnd = 3;
    
    public int dBitStart = 4;
    public int dBitEnd = 6;
    
    public int fileStart = 7;
//    public int fileEnd = 13;
    
    
    
    public abstract void exe(int indexBit, int indexFile);
    
    
}
