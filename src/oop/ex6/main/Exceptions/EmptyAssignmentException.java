package oop.ex6.main.Exceptions;

public class EmptyAssignmentException extends Exception {
    public EmptyAssignmentException() {
        super("Error: no value assigned variable");
    }
}
