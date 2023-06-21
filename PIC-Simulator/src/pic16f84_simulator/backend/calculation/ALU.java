package pic16f84_simulator.backend.calculation;
import pic16f84_simulator.backend.memory.Register;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class ALU {

    private static boolean allow = true;
    public ALU() {
        allow = Utils.allow(allow, this);
    }

    public Register wReg = new Register(8);


    // returns: a + b (while handling overflow issues) --> result is between [0, 255]
    // updates following flags in StatusReg: ZeroFlag, CarryFlag, DigitCaryFlag
    public int[] addition(int[] arr1, int[] arr2) { // Linus
        if(arr1.length != 8 || arr2.length != 8) {
            throw new IllegalArgumentException("Cant use substract() on an array with length !=8");
        }
        int a = Utils.binaryToDec(arr1); 
        int b = Utils.binaryToDec(arr2);
        int res = a + b;

        SFR.updateCflag(res); // has to be called before correcting the scope
        SFR.updateDCflag(a, b);
        if(res > 255) {
            res -= 256;
        }
        SFR.updateZflag(res); // has to be called after correcting the scope
        return Utils.decToBinary(res, 8);
    }

    
    /*
     *  returns: a - b (while handling underflow issues) -
     *  -> result is between [0, 255]
     *      does that by calling addition(a, b) with Zweierkomplement
 *      
 *      following flags in StatusReg get updated: ZeroFlag, CarryFlag, DigitCaryFlag
 *      
 *      Note:
 *      PIC16F84 does not invert C-flag or DC-flag after subtraction 
 *      --> manufactoring defect !
 *      
     */
    public int[] subtraction(int[] arr1, int[] arr2) { // Linus
        int b = Utils.binaryToDec(arr2);
        int zweierkomplement = (~b) + 1;
        if(zweierkomplement < 0) {
            zweierkomplement += 256;
        }
        return addition(arr1, Utils.decToBinary(zweierkomplement, 8));
    }

    
    /**
     * - for GUI ---------------------------------- reset -------------------------------------------
     */

    public void resetW_Reg() {
        this.wReg = new Register(8);
    }


}
