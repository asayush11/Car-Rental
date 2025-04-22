package src;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private final String bookingID;
    private final Car car;
    private final User user;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double totalPrice;
    private BookingStatus bookingStatus;

    public String getBookingID() {
        return bookingID;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Car getCar() {
        return car;
    }

    public Booking(String bookingID, User user, Car car, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.bookingID = bookingID;
        this.user = user;
        this.car = car;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.totalPrice = calculateTotalPrice();
        this.bookingStatus = BookingStatus.PENDING;
    }

    private double calculateTotalPrice(){
        long hoursRented = ChronoUnit.HOURS.between(startDateTime, endDateTime);
        System.out.println("Total hours rented is: " + hoursRented);
        return car.getPricePerHour() * hoursRented;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.totalPrice = calculateTotalPrice();
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        this.totalPrice = calculateTotalPrice();
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
