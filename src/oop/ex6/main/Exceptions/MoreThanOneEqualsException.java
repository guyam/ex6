package oop.ex6.main.Exceptions;

public class MoreThanOneEqualsException extends Exception {
    public MoreThanOneEqualsException() {
        super("Error: More than one \"=\" sign detected");
    }
}
