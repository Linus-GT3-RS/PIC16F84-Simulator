package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public enum LitConOps implements Instruction { // Linus

    ADDLW { // Linus
        @Override
        public void exe(int[] k) {
        }
    },
    ANDLW { // Eduard
        @Override
        public void exe(int[] k) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg.readReg());
            int kAsDec = Utils.binaryToDec(k);
            int[] wAndK = Utils.decToBinary(wAsDec & kAsDec, 8);
            MC.alu.wReg.writeReg(wAndK);
            SFR.updateZflag(wAndK);
        }
    },
    CALL { // Linus
        @Override
        public void exe(int[] k) {
        }
    },
    CLRWDT { // Eduard
        @Override
        public void exe(int[] k) {
            // Not implemnted
        }
    },
    GOTO { // Linus
        @Override
        public void exe(int[] k) {
        }
    },
    IORLW { // Eduard
        @Override
        public void exe(int[] k) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg.readReg());
            int kAsDec = Utils.binaryToDec(k);
            int[] wAndK = Utils.decToBinary(wAsDec ^ kAsDec, 8);
            MC.alu.wReg.writeReg(wAndK);
            SFR.updateZflag(wAndK);
        }
    },
    MOVLW { // Linus
        @Override
        public void exe(int[] k) {
        }
    },
    RETFIE { // Eduard
        @Override
        public void exe(int[] k) {
            // TODO @Eduard RETFIE
            MC.stack.pop();
            MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 0, 1);
            MC.timer.tryIncrInternalTimer();
        }
    },
    RETLW { // Linus
        @Override
        public void exe(int[] k) {
        }
    },
    RETURN { // Eduard
        @Override
        public void exe(int[] k) {
            MC.stack.pop();
        }
    },
    SLEEP { // Linus
        @Override
        public void exe(int[] k) {
            // Not implemented
        }
    },
    SUBLW { // Eduard
        @Override
        public void exe(int[] k) {
            int[] wReg = MC.alu.wReg.readReg();
            int[] result = MC.alu.subtraction(wReg, k);
            MC.alu.wReg.writeReg(result);
        }
    },
    XORLW { // Linus
        @Override
        public void exe(int[] k) {
        }
    };

    public int kStart() {
        if (this == CALL || this == GOTO) {
            return 3;
        } else
            return 6;
    }

    public abstract void exe(int[] k);

}
