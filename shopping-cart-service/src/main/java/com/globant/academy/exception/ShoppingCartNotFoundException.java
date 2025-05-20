package com.globant.academy.exception;

public class ShoppingCartNotFoundException extends Throwable {
    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
