package pic16f84_simulator;

import pic16f84_simulator.backend.calculation.ALU;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.memory.StackBuffer;
import pic16f84_simulator.backend.tools.Utils;

public class MC {
    
    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public MC() {
        allow = Utils.allow(allow, this);
    }
    
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    public static StackBuffer stack = new StackBuffer();
    
    public static ControlUnit control = new ControlUnit();
    public static ALU alu = new ALU();
    
    
    

}
