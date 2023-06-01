package pic16f84_simulator.backend.control.twv;

import java.util.Arrays;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class Prescaler {
  
    // return Prescaler-Rate;
    public int getPRS() {
        int[] optionReg = MC.ram.readDataCell(SFR.OPTION.asIndex());
        optionReg = Arrays.copyOfRange(optionReg, 5, 8);
        int n = Utils.binaryToDec(optionReg); // get n from OptionReg PS0-PS2
        
        if(MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4) == 1) { // case 1 -> WDT
            return (int)Math.pow(2,n); // 1 * 2^n
        }
        else { // case 0 -> TMR0
            return 2 * (int)Math.pow(2, n); // 2 * 2^n
        }
    }
}
