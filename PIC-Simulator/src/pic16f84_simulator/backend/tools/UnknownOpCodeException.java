package pic16f84_simulator.backend.tools;

public class UnknownOpCodeException extends RuntimeException {
    
    public UnknownOpCodeException(String msg){
        super(msg);
    }
    
}
