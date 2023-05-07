package pic16f84_simulator.backend.calculation;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

public class ALU {
    public int[] wReg = new int[8];

    public int[] AdditionWF(int indexCell) {
        int[] result = new int[8];
        int sum = 0;
        for (int i = wReg.length - 1; i >= 0; i--) {
            sum = sum + wReg[i] + MC.ram.readSpecificBit(indexCell, i);
            if (sum == 2) { // Fall des Überlaufs/Übertrags
                if (i == 4) {
                    SFR.status_setDC(1);
                } else if (i == 0) {
                    SFR.status_setC(1);
                }
                result[i] = 0;
                sum = 1;
            } else {
                if (i == 4) {
                    SFR.status_setDC(0);
                } else if (i == 0) {
                    SFR.status_setC(0);
                }
                if (sum == 0) {
                    result[i] = 0;
                } else { // sum == 1
                    result[i] = 1;
                    sum = 0;
                }
            }

        }
        return result;
    }
}
