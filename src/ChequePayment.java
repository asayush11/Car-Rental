package src;

public class ChequePayment implements ProcessPayment{
    @Override
    public void processPayment(double amount){
        System.out.println("The amount to be paid via cheque is: " + amount );
    }
}
