package main.models.exceptions;

/**
 * Custom Exception thrown when the amount is not valid
 * @author Ange Buhendwa
 */
public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String message){
        super(message);
    }
}
