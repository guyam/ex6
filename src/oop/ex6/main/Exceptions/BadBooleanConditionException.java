package oop.ex6.main.Exceptions;

public class BadBooleanConditionException extends ValidatorException  {
    public BadBooleanConditionException() {
        super("Error: not a boolean condition in if/while block");
    }
}
