package exception;

public class InsufficientFundsException extends Exception{

	private static final long serialVersionUID = 7558019994468955312L;
	
	private String message = null;
	 
    public InsufficientFundsException() {
        super();
    }
 
    public InsufficientFundsException(String message) {
        super(message);
        this.message = message;
    }
 
    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
