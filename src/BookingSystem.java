package src;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookingSystem {
    private static volatile BookingSystem soInstance;
    private final Map<String, Booking> bookings;
    private final List<Car> cars;

    private BookingSystem() {
        this.bookings = new ConcurrentHashMap<>();
        this.cars = new ArrayList<>();
    }

    public static synchronized BookingSystem getSoInstance() {
        if(soInstance == null){
            synchronized (BookingSystem.class) {
                soInstance = new BookingSystem();
            }
        }
        return soInstance;
    }

    public synchronized void addCar(Car car){
        cars.add(car);
        System.out.println("Added Car: " + car.getNumberPlate());
    }

    public synchronized void removeCar(String numberPlate){
        if(cars.removeIf((car -> car.getNumberPlate().equals(numberPlate)))){
            System.out.println("Car with number plate: " + numberPlate + " removed");
        }
        else System.out.println("Car with number plate: " + numberPlate + " not found");
    }

    public synchronized Booking bookCar(User user, Car car, LocalDateTime startDateTime, LocalDateTime endDateTime, ProcessPayment method){
        if (isCarAvailable(car.getNumberPlate(), startDateTime, endDateTime)) {
            String bookingId = generateBookingId();
            Booking booking = new Booking(bookingId, user, car, startDateTime, endDateTime);
            bookings.put(bookingId, booking);
            makePayment(booking.getTotalPrice(), method);
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            System.out.println("Car number: " + car.getNumberPlate() + " booked from " + startDateTime + " to " + endDateTime);
            return booking;
        }
        else System.out.println("Sorry, Car not available");
        return null;
    }

    public synchronized void cancelBooking(String bookingID){
        bookings.get(bookingID).setBookingStatus(BookingStatus.CANCELLED);
        System.out.println("Booking with ID: " + bookingID + " cancelled");
    }

    private boolean isCarAvailable(String numberPlate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (bookings.values().stream().noneMatch(booking -> booking.getCar().getNumberPlate().equals(numberPlate) &&
                (booking.getStartDateTime().isBefore(endDateTime) && booking.getEndDateTime().isAfter(startDateTime)
                && booking.getBookingStatus() != BookingStatus.CANCELLED)));
    }

    private String generateBookingId() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Car> searchCars(int numberOfPeople, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Car> availableCars = cars.stream()
                .filter(car -> car.getNumberOfSeats() >= numberOfPeople)
                .filter(car -> isCarAvailable(car.getNumberPlate(), startDateTime, endDateTime))
                .collect(Collectors.toList());

       if(availableCars.isEmpty()) System.out.println("Sorry, Car not available");
       availableCars.sort((car1, car2) -> {
           if (car1.getNumberOfSeats() != car2.getNumberOfSeats()) {
               return Integer.compare(car2.getNumberOfSeats(), car1.getNumberOfSeats());
           } else {
               return Double.compare(car1.getPricePerHour(), car2.getPricePerHour());
           }
       });
       return availableCars;
    }

    public void makePayment(double amount, ProcessPayment method){
        method.processPayment(amount);
        System.out.println("Payment done");
    }
}
