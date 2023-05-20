package pic16f84_simulator.backend.memory;
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
    }

    @Override
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        checkAddress(indexCell);        
        super.writeSpecificBit(indexCell, indexBit, bit);

        int indxMirrored = mirrorBank(indexCell);
        if(indxMirrored != indexCell) {
            super.writeSpecificBit(indxMirrored, indexBit, bit);
        }        
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


}
