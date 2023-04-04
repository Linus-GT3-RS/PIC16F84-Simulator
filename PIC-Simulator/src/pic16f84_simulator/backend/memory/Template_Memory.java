// Eduard
package pic16f84_simulator.backend.memory;

public abstract class Template_Memory {
    
    private int[][] memory;
    
    Template_Memory(int numberOfCells,int numberOfBitsInEachCell){
        this.memory = new int[numberOfCells][numberOfBitsInEachCell];
    }
    
    /*
     * Safety HAS to be checked in the calling method, NOT in here !!!
     */
        
    public int[] readDataCell(int index) {
        return this.memory[index];
    }
    
    public int readSpecificBit(int indexData, int indexBit) {
        return this.memory[indexData][indexBit];
    }
    
    public void writeDataCell(int index, int[] data) {
        this.memory[index] = data;
    }
    
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        this.memory[indexCell][indexBit] = bit;
    }
    
    
    
}
