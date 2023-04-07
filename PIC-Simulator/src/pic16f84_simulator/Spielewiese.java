package pic16f84_simulator;

import java.util.Arrays;

import pic16f84_simulator.backend.control.ByteOps;
import pic16f84_simulator.backend.control.Instruction;
import pic16f84_simulator.backend.control.LitConOps;

enum Test {
    
    T0, T1, T2;
    
    int startOpC = 0;
    int endOpC;
    
    public Instruction test() {
        
        Instruction i1;
        
        if(this == T0) {
            i1 = ByteOps.ADDWF;
        }
        else {
            i1 = LitConOps.RETFIE;
        }
        
        return i1;
        
    }
    
    
}

public class Spielewiese {
    

    public static void main(String[] args) {
        
        System.out.println(Test.T0.test().getClass());
        
        
    }

}
