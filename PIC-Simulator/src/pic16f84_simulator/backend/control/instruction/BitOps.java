package pic16f84_simulator.backend.control.instruction;

public enum BitOps implements Instruction { // Eduard
    
    BCF {
        @Override
        public void exe(int index, String file) {
        }
    }, BSF {
        @Override
        public void exe(int index, String file) {
        }
    }, BTFSC {
        @Override
        public void exe(int index, String file) {
        }
    }, BTFSS {
        @Override
        public void exe(int index, String file) {
        }
    };
    
    //int opCEnd = 3;
    
    int bBitStart = 4;
    int bBitEnd = 6;
    
    int fileDataStart = 7;
    int fileDataEnd = 13;
    
    
    
    public abstract void exe(int index, String file);
    
    
}
