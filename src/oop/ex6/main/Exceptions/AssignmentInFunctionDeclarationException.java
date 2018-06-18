package oop.ex6.main.Exceptions;

public class AssignmentInFunctionDeclarationException extends ValidatorException {
    public AssignmentInFunctionDeclarationException() {
        super("Error: Tried assigning a variable  ( used a \"=\" sign)  in a  function declaration line");
    }
}
