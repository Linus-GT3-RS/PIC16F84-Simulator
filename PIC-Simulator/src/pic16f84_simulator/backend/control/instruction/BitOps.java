package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;

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
            MicroC.ram.writeSpecificBit(indexFile, indexBit, 1);  
            ControlUnit.pc++;
        }
    },
    BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MicroC.cu.pc++;
            if(MicroC.pm.readSpecificBit(indexFile, indexBit) == 0) {
               ByteOps.NOP.exe(0, 0);
            }
        }
    }, 
    BTFSS { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {            
            ControlUnit.pc++;
            int b = MicroC.ram.readSpecificBit(indexFile, indexBit);
            if(b == 1) {
                ControlUnit.exe(ByteOps.NOP);
            }
        }
    };
    
    public int dBitStart = 4;
    public int dBitEnd = 6;
    
    public int fileStart = 7;
    
    public abstract void exe(int indexBit, int indexFile);
    
    
}
