package pic16f84_simulator;

import java.util.Arrays;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.backend.memory.Register;

public class MicroC {
    
    private static boolean creationAllowed = true; // secures the creation of ONLY ONE instance of this class
    public MicroC() {
        if(MicroC.creationAllowed == false) {
            throw new IllegalArgumentException("Theres already an instance of this class!"); 
        }
        MicroC.creationAllowed = false;      
    }
    
    public static ControlUnit cu = new ControlUnit();
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();    
    
    public static int[] wReg = new int[8];

    
    
    // unnötig falls Register static
//    public Register getReg(Reg reg) {        
//        switch(reg) {
//        case INSTR -> { return this.instrReg;}
//        default -> { throw new UnknownLocationException("This Register is not known");}
//        }
//    }
    
    

}
