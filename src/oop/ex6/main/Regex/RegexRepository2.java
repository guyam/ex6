package oop.ex6.main.Regex;

import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;
import oop.ex6.main.Tuple;
import oop.ex6.main.Types.*;

import java.util.TreeMap;

public class RegexRepository2 {

    private String inputString;
    public TreeMap<String, JavaType> variableSet;
    public TreeMap<String, FunctionType> functionSet;
    public int scope;


    final String functionTitleRegex = "[ \\t\\r]*void[ \\t\\r]+([a-zA-Z]+\\w?)[ \\t\\r]*\\((.*)\\)[ \\t\\r]*\\{";
    final String functionCallRegex = "[ \\t\\r]*([a-zA-Z]+\\w?)[ \\t\\r]*\\(.*[ \\t\\r]*;$";

    public RegexRepository2(String input, TreeMap<String, JavaType> varSetInput, TreeMap<String,FunctionType> funcSetInput, int scopeInput) {
        this.inputString = input;
        //TODO - guy, the correct regex matcher
        this.variableSet = varSetInput;
        this.functionSet = funcSetInput;
        this.scope = scopeInput;
    }

    public void update(String input, int scopeInput) {
        scope = scopeInput;
        inputString = input;
    }

    /**
     * basicly a switchboard, checks to see what sort of line we got with the regex filter, and then handles each
     * case by directing to a different function.
     */
    public void lineHandler() {
        ///todo - regex
    }

    /**
     * this function handles a single declaration of a java type (String - int)
     */
    private void basicDecLine(String name, String type) throws VariableAlreadyExistsException {
        //check if there is already a variable with the same name in the code
        if (variableSet.containsKey(name))
            throw new VariableAlreadyExistsException();
        // let's add it to the variable set.
        variableSet.put(name,new JavaType(type, scope));
    }

    /**
     * this function handles a single declaration and initialization of a java type (String - int)
     */
    private void basicDecAndInitLine(String name, String type, String data, boolean isFinal) throws VariableAlreadyExistsException, EmptyFinalDeclarationException {
        //check if there is already a variable with the same name in the code
        if (variableSet.containsKey(name))
            throw new VariableAlreadyExistsException();
        variableSet.put(name, new JavaType(type, data, isFinal, scope));
    }


    private void basicassignmentLine() {


    }

}
