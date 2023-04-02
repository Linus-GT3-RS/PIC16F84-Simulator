package pic16f84_simulator.backend;

public class BinaryArrayIsToSmall extends RuntimeException {
    
    public BinaryArrayIsToSmall(String msg){
        super(msg);
    }
    
}
