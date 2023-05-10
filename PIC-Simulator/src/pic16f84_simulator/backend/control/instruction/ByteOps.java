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
            storeResult(d,indexFile,result);
            SFR.status_setZ(result);
        }
    },
    ANDWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg);
            int fAsDec = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            int[] wAndF = Utils.decToBinary(wAsDec & fAsDec, 8);
            storeResult(d, indexFile, wAndF);         
            SFR.status_setZ(wAndF); 
        }
    },
    CLRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.ram.writeDataCell(indexFile, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
            SFR.status_setZ(new int[8]);
            MC.control.pc++;
        }
    },
    CLRW { // Linus
        @Override
        public void exe(int d, int indexFile) {
            MC.alu.wReg = new int[] {0,0,0,0,0,0,0,0};
            SFR.status_setZ(new int[8]);
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
            }
            storeResult(d,indexFile,result);
            SFR.status_setZ(result);
        }
    },
    DECF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int res = Utils.binaryToDec(MC.ram.readDataCell(indexFile)) - 1;
            storeResult(d, indexFile, res);
            SFR.status_setZ(res);
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
            storeResult(d,indexFile,result);
            if (result == 0) {
                ByteOps.NOP.exe(0, 0);
            }
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
            storeResult(d,indexFile,result);
            if (result == 0) {
                ByteOps.NOP.exe(0, 0);
            }
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
            storeResult(d,indexFile,result);
            SFR.status_setZ(result);
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
            MC.control.pcpp(); // dont remove
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
            storeResult(d,indexFile,result);
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
            storeResult(d,indexFile,result);
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
