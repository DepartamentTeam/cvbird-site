package ai.cvbird.cvbirdsite.exception;

public class NotEnoughFundsException extends Exception {
    public NotEnoughFundsException(String errorMessage) {
        super(errorMessage);
    }
}
