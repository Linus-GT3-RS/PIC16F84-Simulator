package pic16f84_simulator.backend.control.instruction;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.frontend.controller.ButtonInteraction;

public enum LitConOps implements Instruction { // Linus

    ADDLW { // Linus
        @Override
        public void exe(int[] k) {
            int[] w = MC.alu.wReg.read();
            int[] res = MC.alu.addition(w, k);
            MC.alu.wReg.write(res);
        }
    },
    ANDLW { // Eduard
        @Override
        public void exe(int[] k) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg.read());
            int kAsDec = Utils.binaryToDec(k);
            int[] wAndK = Utils.decToBinary(wAsDec & kAsDec, 8);
            MC.alu.wReg.write(wAndK);
            SFR.updateZflag(wAndK);
        }
    },
    CALL { // Linus
        @Override
        public void exe(int[] k) {
            MC.stack.push();
            int k_dec = Utils.binaryToDec(k) - 1; // -1 to counteract pcpp() after this instruction
            MC.control.pc(k_dec); // pclatch 4-3 is ignored in PIC16F8x
            MC.timer.tryIncrInternalTimer();
            ButtonInteraction.timer++;
        }
    },
    CLRWDT {
        @Override
        public void exe(int[] k) {
            // Not implemented
        }
    },
    GOTO { // Linus 
        @Override
        public void exe(int[] k) {
            int k_dec = Utils.binaryToDec(k); // -1 to counteract pcpp() after this instruction
            MC.control.pc(k_dec); // pclatch 4-3 is ignored in PIC16F8x
            MC.timer.tryIncrInternalTimer();
            ButtonInteraction.timer++;
        }
    },
    IORLW { // Eduard
        @Override
        public void exe(int[] k) {
            int wAsDec = Utils.binaryToDec(MC.alu.wReg.read());
            int kAsDec = Utils.binaryToDec(k);
            int[] wAndK = Utils.decToBinary(wAsDec ^ kAsDec, 8);
            MC.alu.wReg.write(wAndK);
            SFR.updateZflag(wAndK);
        }
    },
    MOVLW { // Linus
        @Override
        public void exe(int[] k) {
            MC.alu.wReg.write(k);
        }
    },
    RETFIE { // Eduard
        @Override
        public void exe(int[] k) {
            MC.stack.pop();
            MC.control.pc(MC.control.pc() - 1);
            MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 0, 1);
            MC.timer.tryIncrInternalTimer(); // has to be at the end of code !!!
            ButtonInteraction.timer++;
        }
    },
    RETLW { // Linus
        @Override
        public void exe(int[] k) {
            MC.alu.wReg.write(k);
            MC.stack.pop();
            MC.control.pc(MC.control.pc() - 1); // to compensate for pcpp() in exe()
            MC.timer.tryIncrInternalTimer(); // has to be at the end of code !!!
            ButtonInteraction.timer++;
        }
    },
    RETURN { // Eduard
        @Override
        public void exe(int[] k) {
            MC.stack.pop();
            MC.timer.tryIncrInternalTimer();
            ButtonInteraction.timer++;
        }
    },
    SLEEP {
        @Override
        public void exe(int[] k) {
          //Not implemented 
        }
    },
    SUBLW { // Eduard
        @Override
        public void exe(int[] k) {
            int[] wReg = MC.alu.wReg.read();
            int[] result = MC.alu.subtraction(k, wReg);
            MC.alu.wReg.write(result);
        }
    },
    XORLW { // Linus
        @Override
        public void exe(int[] k) {
            int w = Utils.binaryToDec(MC.alu.wReg.read());
            int l = Utils.binaryToDec(k);
            int[] res = Utils.decToBinary(w ^ l, 8);
            SFR.updateZflag(res);
            MC.alu.wReg.write(res);
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
