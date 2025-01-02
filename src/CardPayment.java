package src;

public class CardPayment implements ProcessPayment{
    @Override
    public void processPayment(double amount){
        System.out.println("The amount to be paid via card is: " + amount );
        return;
    }
}
