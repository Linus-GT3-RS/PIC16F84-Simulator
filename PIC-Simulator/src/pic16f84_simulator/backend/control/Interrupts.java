package pic16f84_simulator.backend.control;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.control.twv.WatchDog;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.control.twv.*;


public abstract class Interrupts {
    
    public static void stdResponseRoutine() {
        SFR.setGIE(0);
        MC.stack.push(-1);
        MC.control.pc = 4;
    }
    
    
    // WTDOG abgelaufenInterrupt -> Reset Eduard
    public static void WatchDogTimeOut(){
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 3, 0);
        MC.control.pc++;
        System.out.println("Watchdog timer has overflowed"); 

        
    }

}
