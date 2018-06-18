package oop.ex6.main.Exceptions;

public class VariableAlreadyExistsException extends ValidatorException  {
    public VariableAlreadyExistsException() {
        super("Error: tried to initialize a basic variable that has already been initialized in this scope");
    }
}
