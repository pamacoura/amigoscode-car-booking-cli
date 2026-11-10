package com.macoura;

import com.macoura.booking.CarBooking;
import com.macoura.booking.CarBookingNotFoundException;
import com.macoura.booking.CarBookingService;
import com.macoura.booking.CarNotAvailable;
import com.macoura.car.*;
import com.macoura.user.User;
import com.macoura.user.UserNotFoundException;
import com.macoura.user.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static UserService userService=new UserService();
    private static Scanner scanner=new Scanner(System.in);
    private static CarBookingService carBookingService=new CarBookingService();
    private static CarService carService=new CarService();
    public static void main(String[] args){
            menu();
    }
    public static void bookCar() {
        System.out.println("List of users");
        for (User u : userService.getAllUsers()) {
            System.out.println(u.toString());
        }

        UUID uid=null;
       while(uid==null) {
           System.out.println("Enter user ID : ");
           String userID = scanner.nextLine();
           try {
               uid = UUID.fromString(userID);
           } catch (IllegalArgumentException e) {
               System.out.println("Invalid UUID string.");
           }
       }
        System.out.println("List of cars");
        for (Car c :carService.getCars()) {
            System.out.println(c.toString());
        }
        UUID cid=null;
        while (cid==null){
            System.out.println("Enter car ID :");
            String carID = scanner.nextLine();
            try {
                cid= UUID.fromString(carID);
            }catch (IllegalArgumentException e){
                System.out.println("Invalid UUID string.");
            }
        }

        LocalDate startD=null;
        do {
            System.out.println("Enter start date in YYYY/MM/DD format :");
            String startDate = scanner.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            try {
                 startD = LocalDate.parse(startDate, dtf);
            }catch (DateTimeParseException e){
                System.out.println("Invalid date, expected format yyyy/MM/dd.");
            }

        } while (startD==null);

        LocalDate endD=null;
        do {
            System.out.println("Enter end date in YYYY/MM/DD format :");
            String startDate = scanner.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            try {
                endD = LocalDate.parse(startDate, dtf);
            }catch (DateTimeParseException e){
                System.out.println("Invalid date, expected format yyyy/MM/dd.");
            }

        } while (endD==null);
        //
       try {
           CarBooking cb=carBookingService.bookCar(uid,cid,startD,endD);
           System.out.println("Car booking saved successfully!!!");
           System.out.println(cb.toString());
       }catch (UserNotFoundException u){
           System.out.println(u.getMessage());
       }catch (CarNotFoundException c){
           System.out.println(c.getMessage());
       }catch (CarNotAvailable c){
           System.out.println(c.getMessage());
       }catch (IllegalArgumentException e){
           System.out.println(e.getMessage());
       }
        System.out.println("");System.out.println("");
    }
    public static void deleteBooking(){
        System.out.println("2 - Delete booking");
        if(carBookingService.getCurrentCarBooking().length==0){
            System.out.println("No bookings found.");
        }else {
            System.out.println("All Bookings");
            viewBookingList();
            UUID bid=null;
            while (bid==null){
                System.out.println("Enter booking ID :");
                String bID = scanner.nextLine();
                try {
                    bid= UUID.fromString(bID);
                }catch (IllegalArgumentException e){
                    System.out.println("Invalid UUID string.");
                }
            }
            try {
                carBookingService.deleteCarBooking(bid);

            }catch (CarBookingNotFoundException cb){
                System.out.println(cb.getMessage());
            }
        }
        System.out.println("");
        System.out.println("");

    }
    public static void viewAllUserBookedCars()  {
        System.out.println("3 - view All User Booked Cars");
        System.out.println("List of users");
        for (User u : userService.getAllUsers()) {
            System.out.println(u.toString());
        }

        UUID uid=null;
        while(uid==null) {
            System.out.println("Enter user ID : ");
            String userID = scanner.nextLine();
            try {
                uid = UUID.fromString(userID);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string.");
            }
        }

        try {
            CarBooking[] cbs=carBookingService.getCarBookedBySpecificUser(uid);
            if (cbs.length==0){
                System.out.println("No cars booked found for this user.");
            }else{
                for(CarBooking cb:cbs){
                    System.out.println("Car : "+cb.getCar().getBrand()+", Start and End date: "+cb.getStartDate()+" to "+cb.getEndDate()+", Total rental price: "+cb.getPrice()+" and Booking status: "+cb.getStatus());
                }
            }
        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
        }
        System.out.println("");
        System.out.println("");
    }
    public static void viewAllBookings(){
        System.out.println("4 - All Bookings");
        if(carBookingService.getAllBooking().length==0){
            System.out.println("No bookings found.");
        }else {
            for(CarBooking cb:carBookingService.getAllBooking()){
                System.out.println(cb.toString());
            }
        }

        System.out.println("");
        System.out.println("");
    }
    public static void viewBookingList(){
        if(carBookingService.getCurrentCarBooking().length==0){
            System.out.println("No bookings found.");
        }else {
            for(CarBooking cb:carBookingService.getCurrentCarBooking()){
                System.out.println(cb.toString());
            }
        }
    }
    public static void viewAvailableCars(){
        System.out.println("5 - view Available Cars");

        if(carService.getAvailableCars(carBookingService.getCurrentCarBooking()).length==0){
            System.out.println("No available cars.");
        }else {
            for (Car c:carService.getAvailableCars(carBookingService.getCurrentCarBooking())){
                System.out.println(c.toString());
            }
        }
        System.out.println("");
        System.out.println("");
    }
    public static void viewAvailableElectricCars(){
        System.out.println("6 - view Available Electric Cars");
        if(carService.getAvailableCars(carBookingService.getCurrentCarBooking(),true).length==0){
            System.out.println("No available electric cars.");
        }else {
            for (Car c:carService.getAvailableCars(carBookingService.getCurrentCarBooking(),true)){
                System.out.println(c.toString());
            }
        }

        System.out.println("");
        System.out.println("");
    }
    public static void viewAllUsers(){
        System.out.println("7 - view All Users");
        User[] us=userService.getAllUsers();
        if(us.length==0){
            System.out.println("No users!");
        }else{
            for (User u:us){
                System.out.println(u.toString());
            }
        }
        System.out.println("");
        System.out.println("");
    }
    public static void menu(){
        boolean running=true;
        while(running) {
                System.out.println("Car Booking CLI");
                System.out.println("---------------------------------");
                System.out.println("1 - Book Car");
                System.out.println("2 - Delete Booking");
                System.out.println("3 - View All User Booked Cars");
                System.out.println("4 - View All Bookings");
                System.out.println("5 - View Available Cars");
                System.out.println("6 - View Available Electric Cars");
                System.out.println("7 - View All Users");
                System.out.println("8 - Exit");
                System.out.print("Enter your choice: ");
            if(scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> bookCar();
                    case 2 -> deleteBooking();
                    case 3 -> viewAllUserBookedCars();
                    case 4 -> viewAllBookings();
                    case 5 -> viewAvailableCars();
                    case 6 -> viewAvailableElectricCars();
                    case 7 -> viewAllUsers();
                    case 8 -> {
                        running = false;
                        System.out.println("See you next time !!!!");
                    }
                    default -> System.out.println("Please select a number between 1 and 8.");
                }
            } else{
                System.out.println("Invalid input!");
                scanner.nextLine();
            }
        }
    }
}
