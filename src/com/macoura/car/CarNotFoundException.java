package com.macoura.car;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String message) {
        super(message);
    }
}
