package pic16f84_simulator.backend.memory;

/*
 * "index" should be: MemoryLocation (Adresse der Speicherzelle)
 */
public class RAM_Memory extends Template_Memory { // Linus

    private static boolean creationAllowed = true; // secures the creation of ONLY ONE instance of this class
    public RAM_Memory() {
        super(208, 8); // "second unimplemented block" (address d0 - ff) is for obvious reasons not implemented
        if(RAM_Memory.creationAllowed == false) {
            throw new IllegalArgumentException("Theres already an instance of this class: ProgramMemory !"); 
        }
        RAM_Memory.creationAllowed = false;
    }   


    /*
     * writes to specific RamAdress --> gets mirrored automatically
     */
    @Override
    public void writeDataCell(int indexCell, int[] data) {
        checkMemoryLocation(indexCell); // checks if unimplemented
        if(data.length != 8) {
            throw new NegativeArraySizeException ("The bit-word has the wrong size");
        }                
        super.writeDataCell(indexCell, data);

        int indexMirrored = mirrorBank(indexCell);
        if(indexMirrored != indexCell) {
            super.writeDataCell(indexMirrored, data);
        }
    }

    @Override
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        checkMemoryLocation(indexCell);
        if(bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Thats not a bit: " + bit);
        }
        super.writeSpecificBit(indexCell, indexBit, bit);
        
        int indxMirrored = mirrorBank(indexCell);
        if(indxMirrored != indexCell) {
            super.writeSpecificBit(indxMirrored, indexBit, bit);
        }        
    }



    // Hilfsmethode
    public void checkMemoryLocation(int index) {
        if(index == 7 || index == 135 || index >= 80 && index <= 127) { // -> des sind alles die "unimplemented MemoryLocations"
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


}
