package oop.ex6.main.Exceptions;

public class MoreThanOneEqualsException extends ValidatorException  {
    public MoreThanOneEqualsException() {
        super("Error: More than one \"=\" sign detected");
    }
}
