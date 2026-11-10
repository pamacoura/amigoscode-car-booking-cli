package com.macoura.car;

import com.macoura.booking.CarBooking;
import com.macoura.user.User;
import com.macoura.user.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

public class CarService {
    private CarDao carDao;

    public CarService() {
        this.carDao = new CarDao();
    }

    public Car[] getAvailableCars(CarBooking[] carBookings,boolean isElectric){
        return carDao.getAvailableCars(carBookings,isElectric);
    }
    public Car[] getAvailableCars(CarBooking[] carBookings){
        return carDao.getAvailableCars(carBookings);
    }
public Optional<Car> getCarById(UUID id){
    return carDao.getCarById(id);
}
    public Car[] getCars() {
        return carDao.getCars();
    }
}
