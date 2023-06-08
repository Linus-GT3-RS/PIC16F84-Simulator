package pic16f84_simulator.backend.memory;

public class Register extends Template_Memory {
    
    public Register(int numbOfBits){
        super(1, numbOfBits);
    }
    
    public int length = read().length;
    
    public int[] read() {
        return super.readCell(0);
    }
    
    public int readBit(int indx) {
        return super.readBit(0, indx);
    }
    
    public void write(int[] bitWord) {
      super.writeDataCell(0, bitWord);
    }
    
    public void writeBit(int indx, int bit) {
        super.writeSpecificBit(0, indx, bit);
    }

}
