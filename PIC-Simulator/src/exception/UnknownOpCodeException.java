package exception;

public class UnknownOpCodeException extends RuntimeException {
    
    public UnknownOpCodeException(String msg){
        super(msg);
    }
    
}
