package pic16f84_simulator.backend.memory;

import exception.UnknownLocationException;

public abstract class Template_Memory { // Eduard + Linus
    
    private int[][] memory;
    
    Template_Memory(int numberOfCells,int numberOfBitsInEachCell){
        this.memory = new int[numberOfCells][numberOfBitsInEachCell];
    }
    
    /*
     * Safety HAS to be checked in the calling method, NOT in here !!!
     */ 
        
    public int[] readDataCell(int indexCell) {
        checkMemoryLocation(indexCell);
        return this.memory[indexCell];
    }
    
    public int readSpecificBit(int indexCell, int indexBit) {
        checkBit(indexCell,indexBit);
        return this.memory[indexCell][indexBit];
    }
    
    public void writeDataCell(int indexCell, int[] data) {
        checkMemoryLocation(indexCell);
        this.memory[indexCell] = data;
    }
    
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        checkBit(indexCell,indexBit);
        this.memory[indexCell][indexBit] = bit;
    }
    
    public void checkMemoryLocation(int indexCell) {
        if(indexCell < 0 || indexCell >= this.memory.length) {
            throw new UnknownLocationException("Ungültiger Speicherzugriff - Speicheradresse nicht gültig");
        }
    }
    
    public void checkBit(int indexCell, int indexBit) {
        checkMemoryLocation(indexCell);
        if(indexBit < 0 || indexCell >= this.memory[indexCell].length) {
            throw new UnknownLocationException("Zugriff auf Bit nicht möglich");
        }
    }
    
}
