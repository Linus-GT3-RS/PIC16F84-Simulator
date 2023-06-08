package pic16f84_simulator.backend.control.twv;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.control.Interrupts;
import pic16f84_simulator.backend.memory.RAM_Memory;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;

public class Timer {

    private int delay = 0; // is used to realise the (2) waiting cycles after writing to TMR0 --> count of delays
    private double incrCheck;

    // has to be called after writing to TMR0 to offset timer by 2 cycles
    public void delayBy2Cycles() {
        this.delay += 2;  
    }

    /*
     * is used to increment TMRO every instruction cycle
     *     --> does that only IF internalClock is selected: TOCS=0    
     */
    public void tryIncrInternalTimer() {
        if(SFR.getTOCS() == 1) {
            return; // external clock is currently selected --> no implementation in this project
        }
        if(this.delay > 0) {
            this.delay--;
            return; // waiting cycles are not over yet --> cant increase TMR0
        }
        int tmr0New = incrTimer(); // overflow is not yet corrected !!
        MC.ram.writeDataCell(SFR.TMR0.asIndex(), Utils.decToBinary(RAM_Memory.fixScope(tmr0New), 8));
        this.delay -= 2; // has to be done to compensate for autom. delay +=2 caused by writeRam()

        if(tmr0New == 256) { // checks for overflow from 255 to 0
            SFR.setTOIF();
            tmr0Interrupt();
        }
    }

    /*
     *  returns the adjusted timer:
     *      - value depends whether prescaler is assigned to timer or not
     */        
    private int incrTimer() {
        int tmr0 = Utils.binaryToDec(MC.ram.readDataCell(SFR.TMR0.asIndex()));
        if(SFR.getPSA() == 0) { // Prescaler is assigned to Timer
            this.incrCheck += 1.0 / (double)MC.timer.getPRS();
            if((int)this.incrCheck != 1) {
                return tmr0;               
            }
            this.incrCheck = 0.0;
        }
        return tmr0 + 1;
    }


    private void tmr0Interrupt() {
        if(SFR.getGIE() == 1 && SFR.getTOIE() == 1) {
            Interrupts.stdResponseRoutine();
        }
    }
    
    public int getPRS() {
        return 2*MC.prescaler.getPRS();

    }

    // When write in timer
    public void clearPRS() {
        if(MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4) == 0) { // case 0 -> TMR
            MC.prescaler.clearPRS();
        }
    }


    /*
     * for debugging purpose only
     */
    public void debug_clearDelay() {
        this.delay = 0;
    }    
    public void debug_clearIncrCheck() {
        this.incrCheck = 0.0;
    }

}
