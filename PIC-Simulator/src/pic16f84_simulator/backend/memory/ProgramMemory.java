package pic16f84_simulator.backend.memory;
import pic16f84_simulator.backend.Converter;

public class ProgramMemory extends Memory {
    private int[][] memory;
    
    ProgramMemory(int sizeOfMemory, int numberOfBits){
        super(sizeOfMemory,numberOfBits);
    }
    
    public void Store(String data) {
        Converter.binaryToHex(new int[] {0,1,0,1,0,1});
    }
}
