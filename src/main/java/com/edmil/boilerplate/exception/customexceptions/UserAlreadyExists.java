package com.edmil.boilerplate.exception.customexceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(){super("User already exists.");}
}
