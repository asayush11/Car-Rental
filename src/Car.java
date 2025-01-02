package src;

public class Car {
    private final String numberPlate;
    private final String model;
    private final double pricePerHour;
    private final int numberOfSeats;

    public String getModel() {
        return model;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public Car(String numberPlate, String model, double pricePerHour, int numberOfSeats){
        this.numberPlate = numberPlate;
        this.model = model;
        this.pricePerHour = pricePerHour;
        this.numberOfSeats = numberOfSeats;
    }

}
