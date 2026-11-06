package com.macoura.booking;

public class CarNotAvailable extends RuntimeException {
    public CarNotAvailable(String message) {
        super(message);
    }
}
