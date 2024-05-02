package projectspringboot.library.exception;

public class StoreException extends RuntimeException{
    private final static long serialVersionUID = 1L;

    public StoreException(String message){
        super(message);
    }

    public StoreException(String message, Throwable exception){
        super(message, exception);
    }
}
