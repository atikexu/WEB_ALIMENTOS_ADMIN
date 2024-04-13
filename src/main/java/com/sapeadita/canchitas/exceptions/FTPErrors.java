package com.sapeadita.canchitas.exceptions;

public class FTPErrors extends Exception {

    private ErrorMessage errorMessage;

    public FTPErrors(ErrorMessage errorMessage) {
        super(errorMessage.getErrormessage());
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
