package pic16f84_simulator.backend.memory;

/*
 * "index" should be: MemoryLocation (Adresse der Speicherzelle)
 */
public class RAM_Memory extends Template_Memory { // Linus

    public RAM_Memory() {
        super(208, 8); // "second unimplemented block" (address d0 - ff) is for obvious reasons not implemented
    }   
    
    
    // changes one specificBit of one SFR-Register (in both Banks if possible)
    public void setSFRBit (SFR sfr, int indexBit, int bit) {
        super.writeSpecificBit(sfr.asIndex(), indexBit, bit); // writes Bank0
        
        int mirroredIndex = mirrorBank(sfr.asIndex());
        if(mirroredIndex != sfr.asIndex()) {
            super.writeSpecificBit(mirroredIndex, indexBit, bit); // writes Bank1
        }
    }
    
    
    // write to specific RamAdress --> gets mirrored automatically
    public void writeRAM (int index, int[] data) {
        if(data.length != 8) {
            throw new NegativeArraySizeException ("The bit-word has the wrong size");
        }                
        checkMemoryLocation(index); // checks if unimplemented
        
        super.writeDataCell(index, data);
        
        int indexMirrored = mirrorBank(index);
        if(indexMirrored != index) {
            super.writeDataCell(indexMirrored, data);
        }
    }
    
    
    // Hilfsmethode
    public void checkMemoryLocation(int index) {
        if(index == 7 || index == 135 || index >= 80 && index <= 127) { // -> des sind alles die "unimplemented MemoryLocations"
            throw new NegativeArraySizeException ("This address is not available");
        }
    }
    
    
    // Hilfsmethode
    public int mirrorBank(int index) {        
        int result = 0;
        
        switch(index) {
        case 1: // fall-through is correct
        case 5: // fall-through is correct
        case 6: // fall-through is correct
        case 8: // fall-through is correct
        case 9: // fall-through is correct            
        case 129: // fall-through is correct
        case 133: // fall-through is correct
        case 134: // fall-through is correct
        case 136: // fall-through is correct
        case 137: result = index; // doesnt get mirrored
        break;
        default: result = (index + 128); // mirror to Bank1
        break;
        }
        
        if(result >= 256) { // dann hätten wir Bank1 auf "Bank2" gemirrored -> geht net
            result -= 256; // mirror to Bank0
        }        
        return result;
    }


}
