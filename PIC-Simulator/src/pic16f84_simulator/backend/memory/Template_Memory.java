package pic16f84_simulator.backend.memory;

public abstract class Template_Memory { // Eduard + Linus    
    private int[][] memory;
    
    Template_Memory(int numberOfCells,int numberOfBitsInEachCell){
        this.memory = new int[numberOfCells][numberOfBitsInEachCell];
    }
    
    /*
     * Safety HAS to be checked in the calling method, NOT in here !!!
     */ 
        
    public int[] readDataCell(int indexCell) {
        return this.memory[indexCell];
    }
    
    public int readSpecificBit(int indexCell, int indexBit) {
        return this.memory[indexCell][indexBit];
    }
    
    
    public void writeDataCell(int indexCell, int[] data) {
        this.memory[indexCell] = data;
    }
    
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        this.memory[indexCell][indexBit] = bit;
    }
    
}
