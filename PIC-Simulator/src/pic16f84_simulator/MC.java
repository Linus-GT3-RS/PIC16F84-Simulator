package pic16f84_simulator;
import pic16f84_simulator.backend.calculation.ALU;
import pic16f84_simulator.backend.control.ControlUnit;
import pic16f84_simulator.backend.control.twv.Prescaler;
import pic16f84_simulator.backend.control.twv.Timer;
import pic16f84_simulator.backend.control.twv.WatchDog;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.memory.StackBuffer;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;

public class MC {
    
    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
     public MC() {
         synchronized(this) {
             allow = Utils.allow(allow, this);             
         }
    }
    
    
    /*
     * Memory
     */
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    public static StackBuffer stack = new StackBuffer();
    
    /*
     * CPU
     */
    public static ControlUnit control = new ControlUnit();
    public static ALU alu = new ALU();
    
    /*
     * Timing
     */
    public static WatchDog wdog = new WatchDog();
    public static Prescaler prescaler = new Prescaler();
    public static Timer timer = new Timer();
    
    
    

}
