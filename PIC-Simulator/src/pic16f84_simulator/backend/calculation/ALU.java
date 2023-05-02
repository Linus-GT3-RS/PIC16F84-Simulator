package pic16f84_simulator.backend.calculation;

import pic16f84_simulator.MC;

public class ALU {
    public int[] wReg = new int[8];
    
    public int[] AdditionWF(int indexCell) {
        int[] result = new int[8];
        int sum = 0;
        for(int i = wReg.length-1;i>=0;i--) {
            sum = sum + wReg[i] + MC.ram.readSpecificBit(indexCell, i);
            if(sum == 2) {
                result[i] = 0;
                sum = 1;
            }else {
                if(sum == 0) {
                    result[i] = 0;
                }else { // sum == 1
                    result[i] = 1;
                    sum = 0;
                }
            }
        }
        return result;
    }
}
