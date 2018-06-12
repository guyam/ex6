package oop.ex6.main.Exceptions;

public class FinalAssignmentException extends Exception {
    public FinalAssignmentException() {
        super("Error: tried to assign a value to a \"final\" variable");
    }
}
