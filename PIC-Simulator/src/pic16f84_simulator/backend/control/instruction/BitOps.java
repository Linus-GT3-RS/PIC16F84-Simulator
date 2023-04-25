package pic16f84_simulator.backend.control.instruction;

import java.util.Arrays;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;

public enum BitOps implements Instruction { // Eduard
    
    BCF { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MicroC.ram.writeSpecificBit(indexFile, indexBit, 0);
            MicroC.control.pc++;
        }
    }, 
    BSF { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {            
            MicroC.ram.writeSpecificBit(indexFile, indexBit, 1);
            MicroC.control.pc++;
        }
    },
    BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MicroC.control.pc++;
            if(MicroC.ram.readSpecificBit(indexFile, indexBit) == 0) {
               ByteOps.NOP.exe(0, 0);
            }
        }
    }, 
    BTFSS { // Linus
        @Override
        public void exe(int indexBit, int indexCell) {            
            MicroC.control.pc++;
            int valOfb = MicroC.ram.readSpecificBit(indexCell, indexBit);
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
