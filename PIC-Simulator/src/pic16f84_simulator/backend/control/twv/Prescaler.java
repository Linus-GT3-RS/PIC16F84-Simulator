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
        int value = (int)Math.pow(2, Utils.binaryToDec(optionReg)); // get n from OptionReg PS0-PS2
        if(MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4) == 0) { // case 0 -> TMR)
           prescaler = 2 * value;
        }else {
            prescaler = value;
        }
    
    }
    
    public int getPRS(int psa) {
        if(psa == MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4)) {
            return prescaler;
        }else {
                return 1;
        }
        
    }
    
    // When write in timer you've to clear
    // only accessible via timer
    public void clearPRS() {
        setPRS();
    }
    
    public void decrementPRS() {
        prescaler--;
        if(prescaler == 0) {
           clearPRS();
        }
    }
}
