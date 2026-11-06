package com.macoura.booking;

import com.macoura.user.User;
import com.macoura.user.UserNotFoundException;
import com.macoura.user.UserService;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarBookingDao {
    private static CarBooking[] carBookings=new CarBooking[10];
    private static int bookingCount=0;
    private UserService userService= new UserService();

    public void saveBooking(CarBooking carBooking){
        carBookings[bookingCount]=carBooking;
        bookingCount++;
    }

    public CarBooking[] getCurrentCarBooking() {

        int activeBooking=0;
        if(carBookings.length>0) {

            for (CarBooking cb : carBookings) {
                if(cb!=null) {
                    if (cb.status.equals(BookingStatus.ACTIVE))
                        activeBooking++;
                }
            }
            CarBooking[] currentCarBooking = new CarBooking[activeBooking];
            int index = 0;
            for (CarBooking cb : carBookings) {
                if(cb!=null) {
                    if (cb.status.equals(BookingStatus.ACTIVE)) {
                        currentCarBooking[index] = cb;
                        index++;
                    }
                }
            }
            return currentCarBooking;
        }else {
            return new CarBooking[0];
        }

    }
    public void deleteCarBooking(UUID carBookingId){
        //CarBooking[] currentCarBooking;
        int index=getCarBookingIndex(carBookingId);
        if (index<0){
            throw new CarBookingNotFoundException("Car booking not found.");
        }else {
            //int j=0;
            //currentCarBooking=new CarBooking[carBookings.length-1];
//            for (int i = 0; i < carBookings.length; i++) {
//                if(carBookings[i]!=null) {
//                    if (!carBookings[i].getId().equals(carBookingId)) {
//                        currentCarBooking[j++] = carBookings[i];
//                    }
//                }
//            }
            //carBookings=currentCarBooking;
            carBookings[index].setStatus(BookingStatus.CANCELLED);
            System.out.println("Booking deleted successfully.");
        }
    }
    public int getCarBookingIndex(UUID id){
        int index=-1;

        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] != null){
                if (carBookings[i].getId().equals(id)) {
                    index = i;
                }
            }
        }
        return index;
    }

    public CarBooking[] getCarBookedBySpecificUser(UUID userId){
        User user= userService.getUserById(userId).orElseThrow(()->new UserNotFoundException("User not found with ID:"+userId));
        int count=0;
        CarBooking[] userCarBooking=new CarBooking[carBookings.length];
        for(CarBooking cb:carBookings){
            if(cb!=null){
                if(cb.getUser().getId().equals(userId)){
                    userCarBooking[count++]=cb;
                }
            }
        }
        return Arrays.copyOf(userCarBooking,count);
    }
    public CarBooking[] getAllBooking(){
        CarBooking[] cbs=new CarBooking[carBookings.length];
        int i=0;
        for(CarBooking cb:carBookings){
            if(cb!=null)
                cbs[i++]=cb;

        }
        return Arrays.copyOf(cbs,i);
    }
}
