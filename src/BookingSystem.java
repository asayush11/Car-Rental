package src;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookingSystem {
    private static volatile BookingSystem soInstance;
    private final Map<String, Booking> bookings;
    private final List<Car> cars;
    private ProcessPayment processPayment;

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

    public synchronized void removeCar(Car car){
        cars.remove(car);
        System.out.println("Removed Car: " + car.getNumberPlate());
    }

    public synchronized Booking bookCar(User user, Car car, LocalDateTime startDateTime, LocalDateTime endDateTime){
        if (isCarAvailable(car, startDateTime, endDateTime)) {
            String bookingId = generateBookingId();
            Booking booking = new Booking(bookingId, user, car, startDateTime, endDateTime);
            bookings.put(bookingId, booking);
            System.out.println("Car number: " + car.getNumberPlate() + " booked from " + startDateTime + " to " + endDateTime);
            return booking;
        }
        else System.out.println("Sorry, Car not available");
        return null;
    }

    public synchronized void cancelBooking(String bookingID){
        bookings.remove(bookingID);
        System.out.println("Booking with ID: " + bookingID + " cancelled");
    }

    private boolean isCarAvailable(Car car, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        for (Booking booking : bookings.values()) {
            if (booking.getCar().equals(car)) {
                if (startDateTime.isBefore(booking.getEndDateTime()) && endDateTime.isAfter(booking.getStartDateTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    private String generateBookingId() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Car> searchCars(int numberOfPeople, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getNumberOfSeats() >= numberOfPeople) {
                if (isCarAvailable(car, startDateTime, endDateTime)) {
                    availableCars.add(car);
                }
            }
        }
        if(availableCars.isEmpty()) System.out.println("Sorry, Car not available");
        availableCars.sort(Comparator.comparing(Car::getNumberOfSeats).thenComparing(Car::getPricePerHour));
        return availableCars;
    }

    public void makePayment(double amount, ProcessPayment method){
        method.processPayment(amount);
        System.out.println("Payment done");
        return;
    }
}
