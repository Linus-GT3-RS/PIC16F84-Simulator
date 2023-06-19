package pic16f84_simulator.backend.control.instruction;
import pic16f84_simulator.MC;
import pic16f84_simulator.frontend.ButtonInteraction;

public enum BitOps implements Instruction { // Eduard
    
    BCF { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            MC.ram.writeSpecificBit(indexFile, indexBit, 0);
        }
    }, 
    BSF { // Linus
        @Override
        public void exe(int indexBit, int indexFile) {            
            MC.ram.writeSpecificBit(indexFile, indexBit, 1);
        }
    },
    BTFSC { // Eduard
        @Override
        public void exe(int indexBit, int indexFile) {
            if(MC.ram.readSpecificBit(indexFile, indexBit) == 0) {
               ByteOps.NOP.exe(0, 0); // discarded 
               MC.control.pcpp();
               ButtonInteraction.timer++;
            }
        }
    }, 
    BTFSS {
        @Override
        public void exe(int indexBit, int indexCell) {  
            int valOfBInF = MC.ram.readSpecificBit(indexCell, indexBit);
            if(valOfBInF == 1) {
                ByteOps.NOP.exe(0, 0);
                MC.control.pcpp();
                ButtonInteraction.timer++;
            }
        }
    };
    
    public int dBitStart = 4;
    public int dBitEnd = 6;
    
    public int fileStart = 7;
    
    public abstract void exe(int indexBit, int indexFile);
    
    
}
