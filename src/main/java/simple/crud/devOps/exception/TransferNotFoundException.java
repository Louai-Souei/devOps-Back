package simple.crud.devOps.exception;

public class TransferNotFoundException extends RuntimeException {
    public TransferNotFoundException(String message) {
        super(message);
    }
}