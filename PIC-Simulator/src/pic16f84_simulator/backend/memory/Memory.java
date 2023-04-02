// Eduard
package pic16f84_simulator.backend.memory;

public abstract class Memory {
    private int[][] memory;
    
    public Memory(int sizeOfMemory,int numberOfBits){
        this.memory = new int[sizeOfMemory][numberOfBits];
    }
    
    public int[] getMemory(int index) {
        return this.memory[index];
    }
    
    public void setMemory(int index,int[] memory) {
        this.memory[index] = memory;
    }
}
