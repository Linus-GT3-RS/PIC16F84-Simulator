package pic16f84_simulator.backend.control.instruction;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public enum ByteOps implements Instruction { // Linus

    ADDWF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] operand = MC.ram.readDataCell(indexFile);
            int[] result = MC.alu.AdditionWF(operand);
            if (d == 1) { // store in RAM
                MC.ram.writeDataCell(indexFile, result);
            } else { // store in W-Reg
                MC.alu.wReg = result;
            }
            SFR.status_setZ(result);
            MC.control.pcpp();
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
            SFR.status_setZ(wAndF);  
            MC.control.pcpp();
        }
    },
    CLRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.ram.writeDataCell(indexFile, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
            SFR.status_setZ(new int[8]);
            MC.control.pc++;
            MC.control.pcpp();
        }
    },
    CLRW { // Linus
        @Override
        public void exe(int d, int indexFile) {
            MC.alu.wReg = new int[] {0,0,0,0,0,0,0,0};
            SFR.status_setZ(new int[8]);
            MC.control.pcpp();
        }
    },
    COMF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MC.ram.readDataCell(indexFile);
            for (int i = 0; i < result.length; i++) {
                if (result[i] == 0) {
                    result[i] = 1;
                } else {
                    result[i] = 0;
                }
                MC.control.pcpp();
            }
            if (d == 1) {// stored in RAM
                MC.ram.writeDataCell(indexFile, result);
            } else { // stored in W-Reg
                MC.alu.wReg = result;
            }
            SFR.status_setZ(result);
            MC.control.pc++;
        }
    },
    DECF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int res = Utils.binaryToDec(MC.ram.readDataCell(indexFile)) - 1;
            storeResult(d, indexFile, res);
            SFR.status_setZ(res);
            MC.control.pc++;
        }
    },
    DECFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int result = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            result--;
            if (result < 0) {
                result = result + 256;
            }
            if (d == 1) {// stored in RAM
                MC.ram.writeDataCell(indexFile, Utils.decToBinary(result, 8));
            } else { // stored in W-Reg
                MC.alu.wReg = Utils.decToBinary(result, 8);
            }
            if (result == 0) {
                ByteOps.NOP.exe(0, 0);
            }
            MC.control.pcpp();
        }
    },
    INCF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    },
    INCFSZ { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int result = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            result++;
            if (d == 1) {// stored in RAM
                MC.ram.writeDataCell(indexFile, Utils.decToBinary(result, 8));
            } else { // stored in W-Reg
                MC.alu.wReg = Utils.decToBinary(result, 8);
            }
            if (result == 0) {
                ByteOps.NOP.exe(0, 0);
            }
            MC.control.pcpp();
        }
    },
    IORWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    },
    MOVF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MC.ram.readDataCell(indexFile);
            if (d == 0) { // move to instr-Reg
                MC.alu.wReg = result;
            } else { // move to f-Reg itself
                MC.ram.writeDataCell(indexFile, result);
            }
            SFR.status_setZ(result);
            MC.control.pcpp();
        }
    },
    MOVWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    },
    NOP { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.control.pc++;
        }
    },
    RLF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    },
    RRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = new int[8];
            System.arraycopy(MC.ram.readDataCell(indexFile), 0, result, 1, 7);
            if (SFR.status_getC() == 0) {
                result[0] = 0;
            } else {
                result[0] = 1;
            }
            SFR.status_setC(MC.ram.readSpecificBit(indexFile, 7));
            if (d == 1) { // store in RAM
                MC.ram.writeDataCell(indexFile, result);
            } else { // store in W-Reg
                MC.alu.wReg = result;
            }
            MC.control.pcpp();
        }
    },
    SUBWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    },
    SWAPF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = new int[8];
            System.arraycopy(MC.ram.readDataCell(indexFile), 4, result, 0, 4);
            System.arraycopy(MC.ram.readDataCell(indexFile), 0, result, 4, 4);
            if (d == 1) { // store in RAM
                MC.ram.writeDataCell(indexFile, result);
            } else { // store in W-Reg
                MC.alu.wReg = result;
            }
            MC.control.pcpp();
        }
    },
    XORWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
        }
    };

    public int indexDbit = 6;
    public int fileStart = 7;

    public abstract void exe(int d, int indexFile);
    
    
    private static void storeResult(int d, int indxFile, int res) {
        int[] conv = Utils.decToBinary(res, 8);
        storeResult(d, indxFile, conv);
    }
    
    private static void storeResult(int d, int indxFile, int[] res) {
        if(d == 0) {
            MC.alu.wReg = res;
        }
        else MC.ram.writeDataCell(indxFile, res);
    }

}
