package dev.manoj.EcomUserService.exception;

public class SessionNotValidated extends RuntimeException{
    public SessionNotValidated() {
    }

    public SessionNotValidated(String message) {
        super(message);
    }

    public SessionNotValidated(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionNotValidated(Throwable cause) {
        super(cause);
    }

    public SessionNotValidated(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
