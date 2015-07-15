package pl.java.scalatech.spring_camel.exception;


public class Exceptions {
    public static RuntimeException transactionIdRequired() {
        return new RuntimeException("Transaction id is required.");
    }

    public static RuntimeException transactionNotFound() {
        return new RuntimeException("Transaction not found.");
    }

    public static RuntimeException itemNotFound() {
        return new RuntimeException("item not found.");
    }

    public static RuntimeException userNotFound() {
        return new RuntimeException("User not found.");
    }

    public static RuntimeException userIdRequired() {
        return new RuntimeException("User id required.");
    }

}