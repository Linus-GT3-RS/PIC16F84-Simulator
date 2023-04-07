package pic16f84_simulator;

import pic16f84_simulator.backend.UnknownLocationException;
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.memory.Reg;
import pic16f84_simulator.backend.memory.Register;

public class MicroController {
    
    private Program_Memory pm;
    private RAM_Memory ram;
    
    public static Register instrReg;
    
    MicroController() {
        this.pm = new Program_Memory();
        this.ram = new RAM_Memory();
        
        MicroController.instrReg = new Register(14);
    }
    
    
    
    // unnötig falls Register static
//    public Register getReg(Reg reg) {        
//        switch(reg) {
//        case INSTR -> { return this.instrReg;}
//        default -> { throw new UnknownLocationException("This Register is not known");}
//        }
//    }
    
    
}
