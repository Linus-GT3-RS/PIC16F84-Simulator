package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;

public enum BitOps implements Instruction { // Eduard
    
    BCF { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
        }
    }, 
    BSF { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {            
            MicroC.ram.writeSpecificBit(indexFile, indexBit, 1);            
        }
    }, 
    BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
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
