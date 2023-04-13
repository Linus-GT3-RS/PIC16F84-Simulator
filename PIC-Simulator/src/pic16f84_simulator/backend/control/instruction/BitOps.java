package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MicroC;

public enum BitOps implements Instruction { // Eduard
    
    BCF { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MicroC.pm.writeSpecificBit(indexFile, indexBit, 0);
            MicroC.cu.pc++;
        }
    }, 
    BSF { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    }, 
    BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MicroC.cu.pc++;
            if(MicroC.pm.readSpecificBit(indexFile, indexBit) == 0) {
                MicroC.cu.exe(ByteOps.NOP);
            }
        }
    }, 
    BTFSS { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    };
    
    public int dBitStart = 4;
    public int dBitEnd = 6;
    
    public int fileStart = 7;
    
    public abstract void exe(int indexBit, int indexFile);
    
    
}
