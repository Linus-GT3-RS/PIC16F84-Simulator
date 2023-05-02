package pic16f84_simulator;

import pic16f84_simulator.backend.calculation.ALU;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;

public class MC {
    
    private static boolean creationAllowed = true; // secures the creation of ONLY ONE instance of this class
    public MC() {
        if(MC.creationAllowed == false) {
            throw new IllegalArgumentException("Theres already an instance of this class!"); 
        }
        MC.creationAllowed = false;      
    }
    
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    
    public static ControlUnit control = new ControlUnit();
    public static ALU alu = new ALU();
    
    
    

}
