package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public enum ByteOps implements Instruction { // Linus

    ADDWF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MC.alu.AdditionWF(indexFile);
            if(d==1) { // store in RAM
                MC.ram.writeDataCell(indexFile, result);
            }else { // store in W-Reg
                MC.alu.wReg = result;
            }
            MC.control.pc++;
        }
    }, 
    ANDWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg);
            int fAsDec = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            int[] wAndF = Utils.decToBinary(wAsDec & fAsDec, 8);
            
            if(d == 0) {
               MC.alu.wReg = wAndF; 
            }
            else MC.ram.writeDataCell(indexFile, wAndF);
        }
    }, 
    CLRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.ram.writeDataCell(indexFile, new int[] {0,0,0,0,0,0,0,0});
        }
    }, 
    CLRW { // Linus
        @Override
        public void exe(int d, int indexFile) {
           MC.alu.wReg = new int[] {0,0,0,0,0,0,0,0};
           MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 1);
        }
    }, 
    COMF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MC.ram.readDataCell(indexFile);
            for(int i = 0;i<result.length;i++) {
                if(result[i]==0) {
                    result[i]=1;
                }else {
                    result[i]=0;
                }
            }
            if(d==1) {// stored in RAM
                MC.ram.writeDataCell(indexFile, result);
            }else { // stored in W-Reg
                MC.alu.wReg = result;
            }
            MC.control.pc++;
        }
    }, DECF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, DECFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int result = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            result--;
            if(d==1) {// stored in RAM
                MC.ram.writeDataCell(indexFile, Utils.decToBinary(result,8));
            }else { // stored in W-Reg
                MC.alu.wReg = Utils.decToBinary(result,8);
            }
            if(result == 0) {
                ByteOps.NOP.exe(0,0);
            }
            MC.control.pc++;
        }
    }, INCF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, INCFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int result = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            result++;
            if(d==1) {// stored in RAM
                MC.ram.writeDataCell(indexFile, Utils.decToBinary(result,8));
            }else { // stored in W-Reg
                MC.alu.wReg = Utils.decToBinary(result,8);
            }
            if(result == 0) {
                ByteOps.NOP.exe(0,0);
            }
            MC.control.pc++;
        }
    }, IORWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, MOVF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            if(d==0) { // move to instr-Reg
                MC.alu.wReg = MC.ram.readDataCell(indexFile);
            }else { // move to f-Reg itself
                MC.ram.writeDataCell(indexFile, MC.ram.readDataCell(indexFile));
            }
            MC.control.pc++;
        }
    }, MOVWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    }, NOP { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.control.pc++;
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
