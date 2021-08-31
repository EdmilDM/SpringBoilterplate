package com.edmil.boilerplate.exception.customexceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String object, Long id) {
        super("Could not find " + object + " with id " + id);
    }
}
