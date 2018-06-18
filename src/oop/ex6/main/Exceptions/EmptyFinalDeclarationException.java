package oop.ex6.main.Exceptions;

public class EmptyFinalDeclarationException extends ValidatorException  {
    public EmptyFinalDeclarationException() {
        super("Error: no value assigned to a \"final\" variable");
    }
}
