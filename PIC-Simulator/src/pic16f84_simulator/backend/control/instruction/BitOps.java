package pic16f84_simulator.backend.control.instruction;
import pic16f84_simulator.MC;

public enum BitOps implements Instruction { // Eduard
    
    BCF { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MC.ram.writeSpecificBit(indexFile, indexBit, 0);
            MC.control.pc++;
        }
    }, 
    BSF { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {            
            MC.ram.writeSpecificBit(indexFile, indexBit, 1);
            MC.control.pc++;
        }
    },
    BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MC.control.pc++;
            if(MC.ram.readSpecificBit(indexFile, indexBit) == 0) {
               ByteOps.NOP.exe(0, 0);
            }
        }
    }, 
    BTFSS { // Linus
        @Override
        public void exe(int indexBit, int indexCell) {            
            MC.control.pc++;
            int valOfb = MC.ram.readSpecificBit(indexCell, indexBit);
            if(valOfb == 1) {
                ByteOps.NOP.exe(0, 0);
            }
        }
    };
    
    public int dBitStart = 4;
    public int dBitEnd = 6;
    
    public int fileStart = 7;
    
    public abstract void exe(int indexBit, int indexFile);
    
    
}
