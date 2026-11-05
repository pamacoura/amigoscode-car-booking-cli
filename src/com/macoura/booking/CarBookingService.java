package com.macoura.booking;

import com.macoura.car.Car;
import com.macoura.car.CarNotFoundException;
import com.macoura.car.CarService;
import com.macoura.user.User;
import com.macoura.user.UserNotFoundException;
import com.macoura.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {
    private CarBookingDao carBookingDao;
    private UserService userService;
    private CarService carService;

    public CarBookingService() {
        this.carBookingDao=new CarBookingDao();
        this.userService=new UserService();
        this.carService=new CarService();
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        // 1. Look up the user by userId
        // - if not found, throw an exception or print an error
        User user= userService.getUserById(userId).orElseThrow(()->new UserNotFoundException("User not found with ID:"+userId));
        // 2. Look up the car by carId
        // - if not found, throw an exception or print an error
        Car car= carService.getCarById(carId).orElseThrow(()->new CarNotFoundException("Car not found with ID:"+carId));
        // 3. Validate the dates:
        // - startDate must not be in the past
        // - endDate must be after startDate
        // - if invalid, throw IllegalArgumentException or print an error
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException(
                    "End date (" + endDate + ") must be after start date (" + startDate + ")");
        }
        // 4. Get all current bookings
        CarBooking[] currentCarBooking=carBookingDao.getCurrentCarBooking();
        // 5. Check whether an active booking already holds this car
        // - if it does, reject: the car is not available
        if(currentCarBooking.length>0){
            for(CarBooking cb:currentCarBooking){
                if(cb.getCar().getId().equals(car.getId())){
                    throw new CarNotAvailaible("the car is not available");
                }
            }
        }
        // 6. Count the days with ChronoUnit.DAYS.between(startDate, endDate)
        long nbDays= ChronoUnit.DAYS.between(startDate,endDate);
        // 7. Calculate the price: car.getRentalPricePerDay() x numberOfDays
        BigDecimal price=car.getRentalPricePerDay().multiply(BigDecimal.valueOf(nbDays));
        // 8. Build a CarBooking with a UUID, user, car, dates, price,
        // BookingStatus.ACTIVE and bookedAt = LocalDateTime.now()
        CarBooking carBooking =new CarBooking(user,car,startDate,endDate,price,BookingStatus.ACTIVE, LocalDateTime.now());
        // 9. Save the booking through the DAO
        carBookingDao.saveBooking(carBooking);
        // 10. Return the saved booking
        return carBooking;
    }
    public CarBooking[] getCurrentCarBooking(){
        return carBookingDao.getCurrentCarBooking();
    }
    public void deleteCarBooking(UUID id){
         carBookingDao.deleteCarBooking(id);
    }
    public CarBooking[] getCarBookedBySpecificUser(UUID userId){
        return carBookingDao.getCarBookedBySpecificUser(userId);
    }
}
