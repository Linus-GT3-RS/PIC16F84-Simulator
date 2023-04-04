// Eduard
package pic16f84_simulator.backend.memory;

// Types of addressable Register
public enum SFR {
    INDF, TMR0, PCL, STATUS, FSR, PORTA, PORTB, EEDATA, EEADR, PCLATH, INTCON, // in Bank0
    OPTION, TRISA, TRISB, EECON1, EECON2 ; // in Bank1

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
    
}
