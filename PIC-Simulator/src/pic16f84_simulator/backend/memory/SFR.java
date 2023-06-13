// Eduard
package pic16f84_simulator.backend.memory;

import java.util.Arrays;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;

// Types of addressable Register
public enum SFR {
    // registers from Bank0
    INDF, TMR0, PCL, STATUS, FSR, PORTA, PORTB, EEDATA, EEADR, PCLATH, INTCON,

    // registers from Bank1
    OPTION, TRISA, TRISB, EECON1, EECON2 ; 

    public int asIndex() {
        switch(this) {
        // Bank0 
        case INDF -> {return 0;} 
        case TMR0 -> {return 1;} // Timer counter
        case PCL -> {return 2;} // Little Endian Program counter
        case STATUS -> {return 3;} // Flag of calculation result
        case FSR -> {return 4;} // indirect adress memory
        case PORTA -> {return 5;} // porta data i/o
        case PORTB -> {return 6;} // portb data i/o
        case EEDATA -> {return 8;} // data for EEPROM
        case EEADR -> {return 9;} // address for EEPROM
        case PCLATH -> {return 10;} // write buffer for upper 5 bits of the program counter
        case INTCON -> {return 11;} // interrupt control

        // Bank1
        case OPTION -> {return 129;} // mode set
        case TRISA -> {return 133;} // mode set for porta
        case TRISB -> {return 134;} // mode set for portb
        case EECON1 -> {return 136;} // control register for EEPROM
        case EECON2 -> {return 137;} // write protection register for EEPROM
        default -> {return -1;} //not possible
        }
    }


    /*
     * 
     * Status Register
     * 
     * 
     */

    public static void setDCflag(int bit) {
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(),6,bit);
    }
    public static int getDCflag() {
        return MC.ram.readBit(SFR.STATUS.asIndex(), 6);
    }

    public static void setCflag(int bit) {
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 7, bit);
    }
    public static int getCflag() {
        return MC.ram.readBit(SFR.STATUS.asIndex(), 7);
    }

    public static int getZflag() {
        return MC.ram.readBit(SFR.STATUS.asIndex(), 5);
    }

    /*
     * can be called before or after correcting scope
     * adds nibbles of a to nibbles of b and updates DC-flag
     */
    public static void updateDCflag(int a, int b) { // Linus
        if(a < 0 || b < 0) {
            throw new IllegalArgumentException("Cant use negative numbers here");
        }
        int res = (a & 15) + (b & 15);
        if(res > 15) {
            setDCflag(1);
        } else setDCflag(0);
    }

    /*
     * updateCflag() has to be called BEFORE correcting the scope
     */
    public static void updateCflag(int res) { // Linus
        if(res > 255) {
            MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 7, 1);
        } else MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 7, 0);
    }

    /*
     * updateZflag() has to be called AFTER correcting the scope
     */
    public static void updateZflag(int res) { // Linus
        int[] conv = Utils.decToBinary(res, 8);
        updateZflag(conv);
    }
    public static void updateZflag(int[] result) { // Linus
        if(Arrays.equals(result, new int[8])) {
            MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 1);
        }
        else MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 5, 0);
    }



    /*
     * 
     * FSR Register
     * 
     * 
     */

    // get SFR.FSR address
    public static int[] getFSR() { // Eduard
        int[] result = MC.ram.readDataCell(SFR.FSR.asIndex());
        return result;
    }

    // set SFR.FSR address
    public static void setFSR(int Address) {
        MC.ram.writeDataCell(SFR.FSR.asIndex(), Utils.decToBinary(Address, 8));
    }


    /*
     * 
     * Option Register
     * 
     * 
     */
    public static int getPSA() {
        return MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4);
    }
    public static void setPSA(int val) {
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 4, val);
    }

    public static int getTOCS() {
        return MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 2);
    }    
    public static void setTOCS(int val) {
        MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 2, val);
    }
    
    public static void setPS2To0(int[] vals){
       MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 5, vals[0]); // PS2
       MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 6, vals[1]); // PS1
       MC.ram.writeSpecificBit(SFR.OPTION.asIndex(), 7, vals[2]); // PS0
    }
    


    /*
     * 
     * INTCON Register
     * 
     * 
     */
    public static void setTOIF() {
        MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 5, 1);
    }
    public static int getTOIF() {
        return MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 5);
    }
    
    public static void setGIE(int val) {
        MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 0, val);
    }
    public static int getGIE() {
       return MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 0); 
    }
    
    public static void setTOIE(int val) {
        MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 2, val);
    }
    public static int getTOIE() {
        return MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 2);
    }
    
    public static int getRBIE() {
        return MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 4);
    }
    
    public static void setRBIE(int value) {
        MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 4, value);
    }
    
    public static void setRBIF(int value) {
        MC.ram.writeSpecificBit(SFR.INTCON.asIndex(), 7, value);
    }
    
    public static int getRBIF() {
        return MC.ram.readSpecificBit(SFR.INTCON.asIndex(), 7);
    }
}
