package pic16f84_simulator.backend.memory;

public abstract class Memory {
    private int[][] memory;
    
    Memory(int sizeOfMemory,int numberOfBits){
        this.memory = new int[sizeOfMemory][numberOfBits];
    }
}
