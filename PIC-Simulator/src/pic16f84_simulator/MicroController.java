package pic16f84_simulator;

<<<<<<< HEAD
import java.util.Arrays;

import pic16f84_simulator.backend.control.ByteOps;
import pic16f84_simulator.backend.control.ControlUnit;
=======
import pic16f84_simulator.backend.UnknownLocationException;
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
import pic16f84_simulator.backend.memory.Program_Memory;
import pic16f84_simulator.backend.memory.RAM_Memory;
<<<<<<< HEAD
import pic16f84_simulator.backend.tools.Converter;
=======
import pic16f84_simulator.backend.memory.Reg;
import pic16f84_simulator.backend.memory.Register;
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git

public class MicroController {
<<<<<<< HEAD
    public static ControlUnit cu = new ControlUnit();
    public static Program_Memory pm = new Program_Memory();
    public static RAM_Memory ram = new RAM_Memory();
    public static int[] instrReg = new int[14];
    public static int[] wReg = new int[8];
=======
    
    private Program_Memory pm;
    private RAM_Memory ram;
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
    
<<<<<<< HEAD
    
    public static void exe(ByteOps instruct) {
        if(instruct == ByteOps.ADDWF) {
            if(instrReg[instruct.dBit] == 1) {
                int[] indexRAM_Binary = Arrays.copyOfRange(instrReg, instruct.fileStart, instruct.fileEnd+1);
                int indexRAM = Converter.binaryToDec(indexRAM_Binary);
                for(int i = 0; i<8;i++) {
                    int result = ram.readSpecificBit(indexRAM, i) + wReg[i];
                    ram.writeSpecificBit(indexRAM, i, result);
                }
            }
        }
=======
    public static Register instrReg = new Register(14);
    
    public MicroController() {
        this.pm = new Program_Memory();
        this.ram = new RAM_Memory();
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
    }
    
<<<<<<< HEAD
=======
    
    
    // unnötig falls Register static
//    public Register getReg(Reg reg) {        
//        switch(reg) {
//        case INSTR -> { return this.instrReg;}
//        default -> { throw new UnknownLocationException("This Register is not known");}
//        }
//    }
    
    
>>>>>>> branch 'main' of https://edugit.hs-offenburg.de/lbruestl1/pic-simulator.git
}
