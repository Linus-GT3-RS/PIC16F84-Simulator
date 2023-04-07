package pic16f84_simulator.backend.memory;

public abstract class Template_Register {
    
    private int[] reg;
    
    Template_Register(int size) {
        this.reg = new int[size];
    }
    
    
    public int[] readReg() {
        return this.reg;
    }
    
    public int readBit(int index) {
        return this.reg[index];
    }        
    
    
    public void writeReg(int[] data) {
        this.reg = data;
    }
    
    public void writeBit(int index, int data) {
        this.reg[index] = data;
    }

}
