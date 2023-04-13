package pic16f84_simulator.backend.control.instruction;

public enum ByteOps implements Instruction { // Linus

    ADDWF {
        @Override
        public void exe(int d, int indexFile) {
//            if(instruct == ByteOps.ADDWF) {
//              if(instrReg.readBit(instruct.dBit) == 1) {
//                  int[] indexRAM_Binary = Arrays.copyOfRange(instrReg.readReg(), instruct.fileStart, instruct.fileEnd+1);
//                  int indexRAM = Utils.binaryToDec(indexRAM_Binary);
//                  for(int i = 0; i<8;i++) {
//                      int result = ram.readSpecificBit(indexRAM, i) + wReg[i];
//                      ram.writeSpecificBit(indexRAM, i, result);
//                  }
//              }
//          }
            
        }
    }, ANDWF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, CLRF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, CLRW {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, COMF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, DECF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, DECFSZ {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, INCF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, INCFSZ {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, IORWF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, MOVF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, 
    MOVWF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, NOP {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, RLF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, RRF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, SUBWF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, SWAPF {
        @Override
        public void exe(int d, int indexFile) {
        }
    }, XORWF {
        @Override
        public void exe(int d, int indexFile) {
        }
    };
    
    //public int opCEnd = 5;
    
    public int indexDbit = 6;
    
    public int fileStart = 7;
//    public int fileEnd = 13;
    
    
    public abstract void exe(int d, int indexFile);


}
 