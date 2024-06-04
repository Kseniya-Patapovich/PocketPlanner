package com.pocketplanner.exception;

public class InsufficientFundsException extends RuntimeException {
    String message;

    public InsufficientFundsException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Insufficient funds in the account: " + message;
    }
}
