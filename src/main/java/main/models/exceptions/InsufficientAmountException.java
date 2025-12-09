package main.models.exceptions;

public class InsufficientAmountException extends RuntimeException {
    /**
     * @param amount the client wants to withdraw
     */
    public InsufficientAmountException(double amount, double balance){
        super("Cannot withdraw: $"+ amount+ ", insufficient balance\n Your balance is: $" + balance + "\nAnd you can't go under the minimum balance");
    }
}
