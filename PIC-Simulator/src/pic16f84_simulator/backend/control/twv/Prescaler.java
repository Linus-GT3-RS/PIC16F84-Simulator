package pic16f84_simulator.backend.control.twv;

import java.util.Arrays;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class Prescaler {
    
    private static int prescaler; // Initialcase
    
    public Prescaler(){
        prescaler = 1;
    }
  
    // After Writing in Option-Reg is to call
    public void setPRS() {
        int[] optionReg = MC.ram.readDataCell(SFR.OPTION.asIndex());
        optionReg = Arrays.copyOfRange(optionReg, 5, 8);
        prescaler = (int)Math.pow(2, Utils.binaryToDec(optionReg)); // get n from OptionReg PS0-PS2
    }
    
    public int getPRS() {
        return prescaler;
    }
    
    // When write in timer you've to clear
    // only accessible via timer
    void clearPRS() {
        setPRS();
    }
}
