package pic16f84_simulator.frontend.collections;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

//May useless
enum Type {
    IN,OUT;

    public int asValue() {
        switch(this) {
        case IN -> {return 1;}
        case OUT -> {return 0;}
        default -> {return -1;} // not reachable
        }
    }   
}

public enum Ports {
    // Port A
    RA0, RA1, RA2, RA3, RA4,
    
    // Port B
    RB0, RB1, RB2, RB3, RB4, RB5, RB6, RB7;
    
    public int asValue() {
        switch(this) {
        case RA0 -> {return MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 7);}
        case RA1 -> {return MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 6);}
        case RA2 -> {return MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 5);}
        case RA3 -> {return MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 4);}
        case RA4 -> {return MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 3);}
        case RB0 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 7);}
        case RB1 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 6);}
        case RB2 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 5);}
        case RB3 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 4);}
        case RB4 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 3);}
        case RB5 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 2);}
        case RB6 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 1);}
        case RB7 -> {return MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 0);}
        default -> {return -1;} // never reachable
        }
    }
    
    public int direction() {
        switch(this) {
        case RA0 -> {return MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 7);}
        case RA1 -> {return MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 6);}
        case RA2 -> {return MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 5);}
        case RA3 -> {return MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 4);}
        case RA4 -> {return MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 3);}
        case RB0 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 7);}
        case RB1 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 6);}
        case RB2 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 5);}
        case RB3 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 4);}
        case RB4 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 3);}
        case RB5 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 2);}
        case RB6 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 1);}
        case RB7 -> {return MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 0);}
        default -> {return -1;} // never reachable
        }
    }
    
    // method is used in GUI for pin switching
    public void toggle(int selectedRow) {
        if(selectedRow == 1) {
            switch (this) {
            case RA0 -> {MC.ram.writeSpecificBit(SFR.PORTA.asIndex(), 7, inverse(MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 7)));}
            case RA1 -> {MC.ram.writeSpecificBit(SFR.PORTA.asIndex(), 6, inverse(MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 6)));}
            case RA2 -> {MC.ram.writeSpecificBit(SFR.PORTA.asIndex(), 5, inverse(MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 5)));}
            case RA3 -> {MC.ram.writeSpecificBit(SFR.PORTA.asIndex(), 4, inverse(MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 4)));}
            case RA4 -> {MC.ram.writeSpecificBit(SFR.PORTA.asIndex(), 3, inverse(MC.ram.readSpecificBit(SFR.PORTA.asIndex(), 3)));}
            case RB0 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 7, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 7)));}
            case RB1 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 6, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 6)));}
            case RB2 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 5, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 5)));}
            case RB3 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 4, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 4)));}
            case RB4 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 3, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 3)));}
            case RB5 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 2, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 2)));}
            case RB6 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 1, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 1)));}
            case RB7 -> {MC.ram.writeSpecificBit(SFR.PORTB.asIndex(), 0, inverse(MC.ram.readSpecificBit(SFR.PORTB.asIndex(), 0)));}
            default -> {}
            }
        } else {
            switch(this) {
            case RA0 -> {MC.ram.writeSpecificBit(SFR.TRISA.asIndex(), 7, inverse( MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 7)));}   
            case RA1 -> {MC.ram.writeSpecificBit(SFR.TRISA.asIndex(), 6, inverse( MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 6)));}
            case RA2 -> {MC.ram.writeSpecificBit(SFR.TRISA.asIndex(), 5, inverse( MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 5)));}
            case RA3 -> {MC.ram.writeSpecificBit(SFR.TRISA.asIndex(), 4, inverse( MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 4)));}
            case RA4 -> {MC.ram.writeSpecificBit(SFR.TRISA.asIndex(), 3, inverse( MC.ram.readSpecificBit(SFR.TRISA.asIndex(), 3)));}
            case RB0 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 7, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 7)));}
            case RB1 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 6, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 6)));}
            case RB2 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 5, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 5)));}
            case RB3 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 4, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 4)));}
            case RB4 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 3, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 3)));}
            case RB5 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 2, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 2)));}
            case RB6 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 1, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 1)));}
            case RB7 -> {MC.ram.writeSpecificBit(SFR.TRISB.asIndex(), 0, inverse( MC.ram.readSpecificBit(SFR.TRISB.asIndex(), 0)));}
            default -> {}
            }
        }
        
        
 }

    private int inverse(int value) {
        if(value == 0) {
            return 1;
        }else {
            return 0;
        }
 }
}
