package pic16f84_simulator;

import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;

public class MicroController {
    private Program_Memory pm;
    private RAM_Memory ram;
    private int[] instrReg;
    
    MicroController() {
        this.pm = new Program_Memory();
        this.ram = new RAM_Memory();
        this.instrReg = new int[16];
    }
}
