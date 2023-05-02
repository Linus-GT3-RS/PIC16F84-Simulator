package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MicroC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.tools.Utils;

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
            MicroC.control.pc++;
        }
    }, ANDWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, CLRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MicroC.ram.writeDataCell(indexFile, new int[] {0,0,0,0,0,0,0,0});
        }
    }, CLRW { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, COMF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MicroC.ram.readDataCell(indexFile);
            for(int i = 0;i<result.length;i++) {
                if(result[i]==0) {
                    result[i]=1;
                }else {
                    result[i]=0;
                }
            }
            if(d==1) {// stored in RAM
                MicroC.ram.writeDataCell(indexFile, result);
            }else { // stored in W-Reg
                MicroC.calc.wReg = result;
            }
            MicroC.control.pc++;
        }
    }, DECF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, DECFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int result = Utils.binaryToDec(MicroC.ram.readDataCell(indexFile));
            result--;
            if(d==1) {// stored in RAM
                MicroC.ram.writeDataCell(indexFile, Utils.decToBinary(result,8));
            }else { // stored in W-Reg
                MicroC.calc.wReg = Utils.decToBinary(result,8);
            }
            if(result == 0) {
                ByteOps.NOP.exe(0,0);
            }
            MicroC.control.pc++;
        }
    }, INCF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, INCFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int result = Utils.binaryToDec(MicroC.ram.readDataCell(indexFile));
            result++;
            if(d==1) {// stored in RAM
                MicroC.ram.writeDataCell(indexFile, Utils.decToBinary(result,8));
            }else { // stored in W-Reg
                MicroC.calc.wReg = Utils.decToBinary(result,8);
            }
            if(result == 0) {
                ByteOps.NOP.exe(0,0);
            }
            MicroC.control.pc++;
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
 