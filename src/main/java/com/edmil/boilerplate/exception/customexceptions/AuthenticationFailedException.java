package com.edmil.boilerplate.exception.customexceptions;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(){ super("Invalid credentials."); }
}
