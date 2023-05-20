package pic16f84_simulator.backend.memory;

import java.util.Arrays;

public abstract class Template_Memory { // Eduard + Linus    
    
    private int[][] memory;
    
    Template_Memory(int numberOfCells, int bitsPerCell){
        this.memory = new int[numberOfCells][bitsPerCell];
    }
    
    /*
     * Safety regarding legal address HAS to be checked in the calling method, NOT in here !!!
     * ONLY the correct bitWordSize and correct bitFormat (0 and 1) is checked in here
     */ 
        
    int[] readCell(int indexCell) {
        int[] cell = this.memory[indexCell];
        return Arrays.copyOf(cell, cell.length);
    }
    
    int readBit(int indexCell, int indexBit) {
        return this.memory[indexCell][indexBit];
    }
    
    
    void writeDataCell(int indexCell, int[] data) {
        checkBitWord(data);
        int[] copy = Arrays.copyOf(data, data.length);
        this.memory[indexCell] = copy;
    }
    
    void writeSpecificBit(int indexCell, int indexBit, int bit) {
        checkBit(bit);
        this.memory[indexCell][indexBit] = bit;
    }
    
    
    private void checkBit(int bit) {
        if(bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Thats not a bit: " + bit);
        }
    }
    
    private void checkBitWord(int[] data) {
        if(data.length != this.memory[0].length) { // test for correct size
            throw new IllegalArgumentException("Invalid size of new dataArray: " + data.length + 
                    ". Has to be of size: " + this.memory[0].length);
        }        
        for(int bit : data) { // test for correct number format
            if(bit != 0 && bit != 1) {
                throw new IllegalArgumentException("Thats not a bit: " + bit);
            } 
        }
    }
    
    
    
}
