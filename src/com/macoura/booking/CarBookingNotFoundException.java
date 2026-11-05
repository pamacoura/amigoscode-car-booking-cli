package com.macoura.booking;

public class CarBookingNotFoundException extends RuntimeException {
    public CarBookingNotFoundException(String message) {
        super(message);
    }
}
