package oop.ex6.main.Exceptions;

public class NoReturnValueException extends Exception {
    public NoReturnValueException() {
        super("Error: no return line for function!");
    }
}
