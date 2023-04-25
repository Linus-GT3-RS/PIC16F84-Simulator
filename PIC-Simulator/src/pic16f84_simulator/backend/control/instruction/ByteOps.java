package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;

public enum ByteOps implements Instruction { // Linus

    ADDWF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MicroC.calc.AdditionWF(indexFile);
            if(d==1) { // store in RAM
                MicroC.ram.writeDataCell(indexFile, result);
            }else { // store in W-Reg
                MicroC.calc.wReg = result;
            }
        }
    }, ANDWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, CLRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, CLRW { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, COMF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, DECF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, DECFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, INCF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, INCFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, IORWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, MOVF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, MOVWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, NOP { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MicroC.control.pc++;
        }
    }, RLF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, RRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, SUBWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, SWAPF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
        }
    }, XORWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    };
    
    public int indexDbit = 6;    
    public int fileStart = 7;
        
    public abstract void exe(int d, int indexFile);


}
 