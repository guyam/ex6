package oop.ex6.main.Exceptions;

public class VariableNotDeclaredException extends ValidatorException {
    public VariableNotDeclaredException() {
        super("Error: a variable that was assigned to that was never declared");
    }
}
