package main.models.exceptions;

public class  OverdraftExceededException extends RuntimeException {
    public OverdraftExceededException(){
        super("Cannot perform operation, ovedraft exceeded");
    }
}
