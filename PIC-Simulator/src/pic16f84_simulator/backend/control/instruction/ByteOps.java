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
            SFR.updateZflag(result);
        }
    },
    ANDWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg.read());
            int fAsDec = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            int[] wAndF = Utils.decToBinary(wAsDec & fAsDec, 8);
            storeResult(d, indexFile, wAndF);         
            SFR.updateZflag(wAndF); 
        }
    },
    CLRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.ram.writeDataCell(indexFile, new int[8]);
            SFR.updateZflag(0);
        }
    },
    CLRW { // Linus
        @Override
        public void exe(int d, int indexFile) {
            MC.alu.wReg.write(new int[8]);
            SFR.updateZflag(0);
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
            SFR.updateZflag(result);
        }
    },
    DECF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int res = Utils.binaryToDec(MC.ram.readDataCell(indexFile)) - 1;
            res = fixScope(res);
            SFR.updateZflag(res);
            storeResult(d, indexFile, res);
        }
    },
    DECFSZ { // Eduard TODO timerTrypp
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
            int[] f = MC.ram.readDataCell(indexFile);
            int res = Utils.binaryToDec(f) + 1;
            res = fixScope(res);
            SFR.updateZflag(res);
            storeResult(d, indexFile, res);
        }
    },
    INCFSZ { // Eduard TODO timerTrypp
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
        public void exe(int d, int indexFile) { // Inclusive OR: A or B or both --> |
            int w = Utils.binaryToDec(MC.alu.wReg.read());
            int f = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            int[] res = Utils.decToBinary((w | f), 8);
            SFR.updateZflag(res);
            storeResult(d, indexFile, res);
        }
    },
    MOVF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = MC.ram.readDataCell(indexFile);
            storeResult(d,indexFile,result);
            SFR.updateZflag(result);
        }
    },
    MOVWF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            storeResult(1, indexFile, MC.alu.wReg.read());
        }
    },
    NOP { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            MC.timer.tryIncrInternalTimer();
        }
    },
    RLF { // Linus
        @Override
        public void exe(int d, int indexFile) {
            int[] f = MC.ram.readDataCell(indexFile);
            int cFlag = SFR.getCflag();
            
            SFR.setCflag(f[0]);            
            for(int i = 0; i < 7; i++) {
                f[i] = f[i + 1];
            }
            f[7] = cFlag;
            storeResult(d, indexFile, f);
        }
    },
    RRF { // Eduard
        @Override
        public void exe(int d, int indexFile) {
            int[] result = new int[8];
            System.arraycopy(MC.ram.readDataCell(indexFile), 0, result, 1, 7);
            if (SFR.getCflag() == 0) {
                result[0] = 0;
            } else {
                result[0] = 1;
            }
            SFR.setCflag(MC.ram.readSpecificBit(indexFile, 7));
            storeResult(d,indexFile,result);
        }
    },
    SUBWF { // Linus
        @Override
        public void exe(int d, int indexFile) {            
            int[] f = MC.ram.readDataCell(indexFile);
            int[] w = MC.alu.wReg.read();
            int[] res = MC.alu.subtraction(f, w);
            storeResult(d, indexFile, res);  
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
            int f = Utils.binaryToDec(MC.ram.readDataCell(indexFile));
            int w = Utils.binaryToDec(MC.alu.wReg.read());
            int xorWF = f ^ w;
            SFR.updateZflag(xorWF);
            storeResult(d, indexFile, xorWF);
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
            MC.alu.wReg.write(res);
        }
        else MC.ram.writeDataCell(indxFile, res);
    }
    
    // sets res to legal scope [0, 255]
    // method affects NO flags --> has to be done elsewhere
    public static int fixScope(int res) { // Linus
        if(res > 255) {
           return res - 256; 
        }
        else if(res < 0) {
            return res + 256;
        }
        else return res;
    }

}
