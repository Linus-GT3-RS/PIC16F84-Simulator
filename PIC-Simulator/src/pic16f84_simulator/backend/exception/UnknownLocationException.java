package pic16f84_simulator.backend.exception;

public class UnknownLocationException extends RuntimeException {
    
    public UnknownLocationException(String msg){
        super(msg);
    }
    
}
