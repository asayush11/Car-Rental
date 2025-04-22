package src;

import java.time.LocalDateTime;
import java.util.List;

public class main {
    private static String bookingID;

    public static void main(String[] args){
        BookingSystem bookingSystem = BookingSystem.getSoInstance();


        // Adding cars
        Car car1 = new Car("UP65123", "Hyundai", 700.00, 6);
        bookingSystem.addCar(car1);
        Car car2 = new Car("UP65124", "Tata", 600.00, 4);
        bookingSystem.addCar(car2);
        Car car3 = new Car("UP65125", "Nexa", 800.00, 5);
        bookingSystem.addCar(car3);

        // Adding users
        User user1 = new User("Ayush", "12345", "asdfg");
        User user2 = new User("Sharma", "12346", "asert");

        bookCar(bookingSystem, user1, 5, new CardPayment());
        bookCar(bookingSystem, user2, 9, new ChequePayment());
        bookCar(bookingSystem, user1, 4, new CardPayment());
        bookCar(bookingSystem, user2, 6, new ChequePayment());
        bookingSystem.cancelBooking(bookingID);
    }

    private static void bookCar(BookingSystem bookingSystem, User user1, int numberOfPeople, ProcessPayment paymentMethod) {
        // booking a car
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(5);

        List<Car> availableCars = bookingSystem.searchCars(numberOfPeople, startDateTime, endDateTime);
        if(!availableCars.isEmpty()){
            for(Car car : availableCars){
                Booking booking = bookingSystem.bookCar(user1, car, startDateTime, endDateTime, paymentMethod);
                if(booking != null) {
                    bookingSystem.makePayment(booking.getTotalPrice(), new CardPayment());
                    bookingID = booking.getBookingID();
                    break;
                }
            }
        }
    }
}
