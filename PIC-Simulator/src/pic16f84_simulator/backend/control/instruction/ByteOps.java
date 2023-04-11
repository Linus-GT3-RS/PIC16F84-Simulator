package pic16f84_simulator.backend.control.instruction;

public enum ByteOps implements Instruction { // Linus

    ADDWF {
        @Override
        public void exe(int d, String file) {
        }
    }, ANDWF {
        @Override
        public void exe(int d, String file) {
        }
    }, CLRF {
        @Override
        public void exe(int d, String file) {
        }
    }, CLRW {
        @Override
        public void exe(int d, String file) {
        }
    }, COMF {
        @Override
        public void exe(int d, String file) {
        }
    }, DECF {
        @Override
        public void exe(int d, String file) {
        }
    }, DECFSZ {
        @Override
        public void exe(int d, String file) {
        }
    }, INCF {
        @Override
        public void exe(int d, String file) {
        }
    }, INCFSZ {
        @Override
        public void exe(int d, String file) {
        }
    }, IORWF {
        @Override
        public void exe(int d, String file) {
        }
    }, MOVF {
        @Override
        public void exe(int d, String file) {
        }
    }, 
    MOVWF {
        @Override
        public void exe(int d, String file) {
        }
    }, NOP {
        @Override
        public void exe(int d, String file) {
        }
    }, RLF {
        @Override
        public void exe(int d, String file) {
        }
    }, RRF {
        @Override
        public void exe(int d, String file) {
        }
    }, SUBWF {
        @Override
        public void exe(int d, String file) {
        }
    }, SWAPF {
        @Override
        public void exe(int d, String file) {
        }
    }, XORWF {
        @Override
        public void exe(int d, String file) {
        }
    };
    
    //public int opCEnd = 5;
    
    public int indexDbit = 6;
    
    public int fileStart = 7;
    public int fileEnd = 13;
    
    
    public abstract void exe(int d, String file);


}
 