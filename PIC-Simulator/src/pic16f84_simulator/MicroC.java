package pic16f84_simulator;

import pic16f84_simulator.backend.calculation.CalcUnit;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;

public class MicroC {
    
    private static boolean creationAllowed = true; // secures the creation of ONLY ONE instance of this class
    public MicroC() {
        if(MicroC.creationAllowed == false) {
            throw new IllegalArgumentException("Theres already an instance of this class!"); 
        }
        MicroC.creationAllowed = false;      
    }
    
    public static ControlUnit control = new ControlUnit();
    public static CalcUnit calc = new CalcUnit();
    
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    
    

}
