package com.globant.academy.exception;

public class CartItemNotFoundException extends Throwable {
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
