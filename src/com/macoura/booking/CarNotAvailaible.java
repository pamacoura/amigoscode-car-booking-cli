package com.macoura.booking;

public class CarNotAvailaible extends RuntimeException {
    public CarNotAvailaible(String message) {
        super(message);
    }
}
