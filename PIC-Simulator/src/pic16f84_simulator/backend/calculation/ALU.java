package pic16f84_simulator.backend.calculation;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class ALU {

    private static boolean allow = true;
    public ALU() {
        allow = Utils.allow(allow, this);
    }

    public int[] wReg = new int[8];

    public int[] AdditionWF(int[] Arr) {
        int[] result = new int[8];
        int sum = 0;
        for (int i = wReg.length - 1; i >= 0; i--) {
            sum = sum + wReg[i] + Arr[i];
            if (sum == 2) { // Fall des Überlaufs/Übertrags
                if (i == 4) {
                    SFR.setDCflag(1);
                } 
                else if (i == 0) {
                    SFR.setCflag(1);
                }
                result[i] = 0;
                sum = 1;
            } 
            else {
                if (i == 4) {
                    SFR.setDCflag(0);
                } 
                else if (i == 0) {
                    SFR.setCflag(0);
                }
                if (sum == 0) {
                    result[i] = 0;
                } 
                else { // sum == 1
                    result[i] = 1;
                    sum = 0;
                }
            }

        }
        return result;
    }


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

    // returns: a - b (while handling underflow issues) --> result is between [0, 255]
    // updates following flags in StatusReg: ZeroFlag, CarryFlag, DigitCaryFlag
    public int[] substraction(int[] arr1, int[] arr2) { // Linus
        //        if(arr1.length != 8 || arr2.length != 8) {
        //            throw new IllegalArgumentException("Cant use substract() on an array with length !=8");
        //        } --> wird in Addition getestet
        int a = Utils.binaryToDec(arr1); 
        int b = Utils.binaryToDec(arr2);
        int res = a - b;
        if(res < 0) { // fix scope
            res += 256;
        }    


        /*
         * neue Idee: Subtract durch Addition mim Zweierkomplement abbilden => eig addition() einfach schreiben
         * => NACH add einfach C und DC invertieren (so wie es der PIC eig machen sollte), dann passt alles
         */
        // STEHEN DIE an der richtigen Position? vom result her
        SFR.updateZflag(res);
        // FIXME C
        // FIXME DC


        return Utils.decToBinary(res, 8); 
    }




}
