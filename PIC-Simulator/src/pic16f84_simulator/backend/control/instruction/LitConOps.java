package pic16f84_simulator.backend.control.instruction;

public enum LitConOps implements Instruction { // Linus

    ADDLW {
        @Override
        public void exe(Object k) {
        }
    }, ANDLW {
        @Override
        public void exe(Object k) {
        }
    }, CALL {
        @Override
        public void exe(Object k) {
        }
    }, CLRWDT {
        @Override
        public void exe(Object k) {
        }
    }, GOTO {
        @Override
        public void exe(Object k) {
        }
    }, IORLW {
        @Override
        public void exe(Object k) {
        }
    }, MOVLW {
        @Override
        public void exe(Object k) {
        }
    }, RETFIE {
        @Override
        public void exe(Object k) {
        }
    }, RETLW {
        @Override
        public void exe(Object k) {
        }
    }, RETURN {
        @Override
        public void exe(Object k) {
        }
    }, 
    SLEEP {
        @Override
        public void exe(Object k) {
        }
    }, SUBLW {
        @Override
        public void exe(Object k) {
        }
    }, XORLW {
        @Override
        public void exe(Object k) {
        }
    };
        
//    public int opCEnd() {
//        if(this == CALL || this == GOTO) {
//            return 2;
//        }
//        else return 5;
//    }    
    
    
    public int kStart() {
        if(this == CALL || this == GOTO) {
            return 3;
        }
        else return 6;
    }    
    int kEnd = 13;
    
    
    public abstract void exe(Object k); // keine Ahnung was k für einen Datentyp haben soll --> rausfinden
    
}
