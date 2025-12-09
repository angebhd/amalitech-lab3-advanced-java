package main.models.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNo) {
        super("Account number: "+ accountNo + " not found");
    }
}
