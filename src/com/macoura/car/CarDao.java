package com.macoura.car;

import com.macoura.booking.BookingStatus;
import com.macoura.booking.CarBooking;
import com.macoura.booking.CarBookingDao;
import com.macoura.user.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarDao {
    private static Car[] cars;
    private CarBookingDao carBookingDao;
    static {
        cars=new Car[]{
                new Car("SN-DK-01", new BigDecimal(30000), Brand.TOYOTA, false),
                new Car("SN-TH-01", new BigDecimal(40000), Brand.AUDI, false),
                new Car("SN-DK-02", new BigDecimal(45000), Brand.MERCEDES, false),
                new Car("SN-DK-03", new BigDecimal(50000), Brand.TESLA, true),
        };
    }

    public CarDao() {
        carBookingDao=new CarBookingDao();
    }

    public static Car[] getCars() {
        return cars;
    }
    public Optional<Car> getCarById(UUID id){
        Optional<Car> u=Optional.ofNullable(null);
        for (Car car:cars){
            if(car.getId().equals(id)){
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }
    public Car[] getAvailableCars(CarBooking[] carBookings,boolean isElectric){
        Car[] availableCars=new Car[cars.length];
        CarBooking[] cbs=carBookingDao.getCurrentCarBooking();
        int k=0;
        for (Car car:cars){
            boolean isBooked = false;
            if(car==null) {
                continue;
            }
            if(car!=null && !car.isElectric()){
                continue;
            }
            for (CarBooking cb : cbs) {
                if(cb==null){
                    continue;
                }
                if (cb != null ) {
                    if (car.getId().equals(cb.getCar().getId())) {
                        isBooked=true;
                        break;
                    }
                }
            }
            if (!isBooked) {
                availableCars[k++] = car;
            }
        }
        return Arrays.copyOf(availableCars,k);
    }
    public Car[] getAvailableCars(CarBooking[] carBookings){
        Car[] availableCars=new Car[cars.length];
        CarBooking[] cbs=carBookingDao.getCurrentCarBooking();
        int k=0;
        for (Car car:cars){
            boolean isBooked = false;
            if(car==null) {
                continue;
            }
            for (CarBooking cb : cbs) {
                if(cb==null){
                    continue;
                }
                if (cb != null ) {
                    if (car.getId().equals(cb.getCar().getId())) {
                        isBooked=true;
                        break;
                    }
                }
            }
            if (!isBooked) {
                availableCars[k++] = car;
            }
        }
        return Arrays.copyOf(availableCars,k);
    }
}
