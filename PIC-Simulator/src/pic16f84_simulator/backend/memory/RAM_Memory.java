package pic16f84_simulator.backend.memory;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;

/*
 * "index" should be: MemoryLocation (Adresse der Speicherzelle)
 */ 
public class RAM_Memory extends Template_Memory { // Linus

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public RAM_Memory() {
        super(208, 8); // "second unimplemented block" (address d0 - ff) is for obvious reasons not implemented
        allow = Utils.allow(allow, this);
    }   

    public void otherReset() { // Eduard
        // ++++++++++++++++ Bank0 +++++++++++++++++++++++++++
        this.writeDataCell(SFR.INDF.asIndex(), new int[8]); 
        this.writeDataCell(SFR.PCL.asIndex(), new int[8]);
        this.writeSpecificBit(SFR.STATUS.asIndex(), 0, 0);
        this.writeSpecificBit(SFR.STATUS.asIndex(), 1, 0);
        this.writeSpecificBit(SFR.STATUS.asIndex(), 2, 0);
        this.writeDataCell(SFR.PCLATH.asIndex(), new int[8]);
        this.writeDataCell(SFR.INTCON.asIndex(), new int[] {0,0,0,0,0,0,0,this.readSpecificBit(SFR.INTCON.asIndex(), 7)});
        
        // ++++++++++++++++ Bank1 +++++++++++++++++++++++++++
        this.writeDataCell(SFR.OPTION.asIndex(), new int[] {1,1,1,1, 1,1,1,1});
        this.writeDataCell(SFR.TRISA.asIndex(), new int[] {0,0,0,1, 1,1,1,1});
        this.writeDataCell(SFR.TRISB.asIndex(), new int[] {1,1,1,1, 1,1,1,1});
        this.writeDataCell(SFR.EECON1.asIndex(), new int[] {0,0,0,0,this.readSpecificBit(SFR.EECON1.asIndex(), 4),0,0,0});
    }
    
    public void powerOnReset() { // Linus
        this.danger_reset();
        this.writeDataCell(SFR.STATUS.asIndex(), new int[] {0,0,0,1, 1,0,0,0}); // Bank0
        this.writeDataCell(SFR.OPTION.asIndex(), new int[] {1,1,1,1, 1,1,1,1}); // from here on Bank1
        this.writeDataCell(SFR.TRISA.asIndex(), new int[] {0,0,0,1, 1,1,1,1});
        this.writeDataCell(SFR.TRISB.asIndex(), new int[] {1,1,1,1, 1,1,1,1});
    }

    /*
     * writes to specific RamAddress --> gets mirrored automatically
     */
    @Override
    public void writeDataCell(int indexCell, int[] data) {
        checkAddress(indexCell);              
        super.writeDataCell(indexCell, data);
        int indexMirrored = mirrorBank(indexCell);
        if(indexMirrored != indexCell) {
            super.writeDataCell(indexMirrored, data);
        }
        checkUp(indexCell);
    }

    @Override
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        checkAddress(indexCell);        
        super.writeSpecificBit(indexCell, indexBit, bit);

        int indxMirrored = mirrorBank(indexCell);
        if(indxMirrored != indexCell) {
            super.writeSpecificBit(indxMirrored, indexBit, bit);
        }      
        checkUp(indexCell);
    }

    public int[] readDataCell(int indexCell) {
        checkAddress(indexCell);
        return super.readCell(indexCell);
    }

    public int readSpecificBit(int indexCell, int indexBit) {
        checkAddress(indexCell);
        return super.readBit(indexCell, indexBit);
    }



    /*
     * Checks if given address is unimplemented space
     */
    public void checkAddress(int index) {
        if(index == 7 || index == 135 || index >= 80 && index <= 127) { // -> these are all the "unimplemented MemoryLocations".
            throw new NegativeArraySizeException ("This address is not available");
        }
    }    

    // Hilfsmethode
    public int mirrorBank(int indexCell) {        
        int result = 0;

        switch(indexCell) {
        case 1: // fall-through is correct
        case 5: // fall-through is correct
        case 6: // fall-through is correct
        case 8: // fall-through is correct
        case 9: // fall-through is correct            
        case 129: // fall-through is correct
        case 133: // fall-through is correct
        case 134: // fall-through is correct
        case 136: // fall-through is correct
        case 137: result = indexCell; // doesnt get mirrored
        break;
        default: result = (indexCell + 128); // mirror to Bank1
        break;
        }
        // eventl. mirror from Bank1 to Bank0: sonst würden wir Bank1 auf "Bank2" mirroren -> geht net
        if(result >= 256) { 
            result -= 256;
        }      
        return result;
    }


    // sets res to legal scope [0, 255]
    // method affects NO flags --> has to be done elsewhere
    public static int fixScope(int res) { // Linus
        if(res > 255) {
            return res - 256; 
        }
        else if(res < 0) {
            return res + 256;
        }
        else return res;
    }
    
    /* 
     * to be used to do certain things, when a specific cell is written
     *      !! if cell is mirrored, both indize have to be checked down here !!
     */
    private void checkUp(int indx) {   
        switch(indx) {
        case 1 -> { MC.timer.delayBy2Cycles(); MC.timer.clearPRS(); }
        case 129 -> {MC.prescaler.setPRS();}
        default -> {} // has to be empty !!!
        }
    }


}
