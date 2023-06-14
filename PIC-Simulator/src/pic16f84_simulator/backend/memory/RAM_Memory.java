package pic16f84_simulator.backend.memory;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.control.Interrupts;
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

    /*
     * -------------------------------------------- Resets -----------------------------------------------------------------
     */
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
     * -------------------------------------------- Read & Write -----------------------------------------------------------------
     */

    /*
     * writes to specific RamAdress --> gets mirrored automatically
     * autom. calls checkUp()
     */
    @Override
    public void writeDataCell(int indexCell, int[] data) {
        writeDataCell(indexCell, data, true);
    }

    /*
     * is used to NOT call checkup()
     */
    public void writeDataCell(int indexCell, int[] data, boolean checkUp) {
        checkAddress(indexCell);
        if(indexCell == 6) {
            for(int i = 0; i < 4;i++) {
                if(super.readBit(indexCell, i) != data[i]) {
                    checkRBInterupt(i);
                    break;
                }
            }
        }
        super.writeDataCell(indexCell, data);
        int indexMirrored = mirrorBank(indexCell);
        if(indexMirrored != indexCell) {
            super.writeDataCell(indexMirrored, data);
        }
        if(checkUp)
        {
            checkUp(indexCell);
        }
    }

    @Override
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        checkAddress(indexCell);
        if(super.readCell(indexCell)[indexBit] != bit && indexCell == 6) {
            checkRBInterupt(indexBit);
        }
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
     * -------------------------------------------- Tools -----------------------------------------------------------------
     */

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
        case 1 -> { MC.timer.delayBy2Cycles(); MC.timer.clearPRS(); } // TMR0
        case 2 -> { MC.control.updatePC(); } // PCL
        case 129 -> { MC.prescaler.setPRS(); } // OPTION
        default -> {} // has to be empty !!!
        }
    }

    private void checkRBInterupt(int indexBit) {
        boolean trisIn = false;
        switch(indexBit) {
        case 0 -> {
            if(MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 0) == 1) {
                trisIn = true;}
        }
        case 1 -> { if(MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 1) == 1) {
            trisIn = true;}
        }
        case 2 -> { if(MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 2) == 1) {
            trisIn = true;}
        }
        case 3 -> { if(MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 3) == 1) {
            trisIn = true;}
        }
        default -> {}
        }
        if(trisIn) {
            SFR.setRBIF(1);
            Interrupts.stdResponseRoutine();
        }
    }


    /*
     * --------------------------------------------- GUI -----------------------------------------------
     */

    public Object[][] getGPR_bank0(){ // TODO Tests machen: dann ist gui:panel_ram fertig
        Object[][] gpr = new Object[68][9];
        for(int i = 0; i < gpr.length; i++) {
            String addr = Integer.toHexString(i + 12).toUpperCase();
            if(addr.length() == 1) {
                addr = "0" + addr;
            }
            gpr[i][0] = "0x" + addr;
            for(int j = 0; j < 8; j++) {
                gpr[i][j + 1] = readSpecificBit(i + 12, j);
            }
        }
        return gpr;
    }

    public Object[][] getsfr(){ // TODO Tests machen: dann ist gui:panel_ram fertig
        Object[][] fsr = new Object[16][9];        
        String[] registerNames = new String[] {
            "0x00 INDF", "0x01 TMR0", "0x02 PCL", "0x03 STATUS", "0x04 FSR", "0x05 PORTA", 
            "0x06 PORTB", "0x08 EEDATA", "0x09 EEADR", "0x0A PCLATCH", "0x0B INTCON",
            
            // Bank 1
            "0x81 OPTION", "0x85 TRISA", "0x86 TRISB", "0x88 EECON1", "0x89 EECON2"};
        for(int i = 0; i < fsr.length; i++) {
            fsr[i][0] = registerNames[i];
        }
        
        for(int i = 0; i < 7; i++) {                 
            for(int j = 0; j < 8; j++) {
                fsr[i][j + 1] = readSpecificBit(i, j);
            }
        }
        for(int i = 7; i < 11; i++) {                 
            for(int j = 0; j < 8; j++) {
                fsr[i][j + 1] = readSpecificBit(i + 1, j);
            }
        }
        for(int j = 0; j < 8; j++) {
            fsr[11][j + 1] = readSpecificBit(129, j);
        }
        for(int j = 0; j < 8; j++) {
            fsr[12][j + 1] = readSpecificBit(133, j);
        }
        for(int j = 0; j < 8; j++) {
            fsr[13][j + 1] = readSpecificBit(134, j);
        }
        for(int j = 0; j < 8; j++) {
            fsr[14][j + 1] = readSpecificBit(136, j);
        }
        for(int j = 0; j < 8; j++) {
            fsr[15][j + 1] = readSpecificBit(137, j);
        }
        return fsr;
    }








}
