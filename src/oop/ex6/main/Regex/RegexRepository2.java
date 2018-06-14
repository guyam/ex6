package oop.ex6.main.Regex;

import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.FinalAssignmentException;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;
import oop.ex6.main.Exceptions.VariableNotDeclaredException;
import oop.ex6.main.Tuple;
import oop.ex6.main.Types.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.TreeMap;

public class RegexRepository2 {

    private String inputString;
    public Stack<Integer> parathensisCounter;
    public ArrayList<LinkedHashMap<String, JavaType>> variableSet;
    public TreeMap<String, FunctionType> functionSet;
    public int scope;


    final String functionTitleRegex = "[ \\t\\r]*void[ \\t\\r]+([a-zA-Z]+\\w?)[ \\t\\r]*\\((.*)\\)[ \\t\\r]*\\{";
    final String functionCallRegex = "[ \\t\\r]*([a-zA-Z]+\\w?)[ \\t\\r]*\\(.*[ \\t\\r]*;$";

    public RegexRepository2(String input, ArrayList<LinkedHashMap<String,JavaType>> varSetInput,
                            TreeMap<String, FunctionType> funcSetInput, int scopeInput, Stack<Integer> pCounter) {
        this.inputString = input;
        //TODO - guy, the correct regex matcher
        this.variableSet = varSetInput;
        this.functionSet = funcSetInput;
        this.scope = scopeInput;
        this.parathensisCounter = pCounter;
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
        if (variableSet.get(variableSet.size()).containsKey(name))
            throw new VariableAlreadyExistsException();
        // let's add it to the variable set.
        variableSet.get(variableSet.size()).put(name, new JavaType(type, scope));
    }

    /**
     * this function handles a single declaration and initialization of a java type (String - int)
     */
    private void basicDecAndInitLine(String name, String type, String data, boolean isFinal) throws VariableAlreadyExistsException, EmptyFinalDeclarationException {
        //check if there is already a variable with the same name in the code
        if (variableSet.get(variableSet.size()).containsKey(name))
            throw new VariableAlreadyExistsException();
        variableSet.get(variableSet.size()).put(name, new JavaType(type, data, isFinal, scope));
    }

    /**
     * this function handles an assignment to a variable
     */
    private void basicassignmentLine(String varName, String newValue) throws FinalAssignmentException, VariableNotDeclaredException {
        //first, we'll check all of the scopes, from the most recent to the first, and check if the variable already exists
        for (int i = variableSet.size() - 1; i >= 0; i--) {
            //now that we are in the scope's variable dictionary, we'll check if a variable with the same name exists in this scope
            if (variableSet.get(i).containsKey(varName)) {
                // try to assign the value. will not assign if it's final and throws an exception
                variableSet.get(i).get(varName).update(newValue);
                //if we reached here, the assignment was succesful. we can exit the function
                return;
            }

        }
        //there is no variable with this name, we will therfor throw an exception
        throw new VariableNotDeclaredException();
    }

    public int getScope() {
        return scope;
    }

    private void functionDecType(ArrayList<JavaType> functionParams, ArrayList<String>paramNames){
        //increase the stack that counts the parenthasis and the scope we are going into
        parathensisCounter.push(0);
        scope++;
        //add the function parameters to the Set of javaTypes, we just opened up a new scope, so we don't need to worry
        // about clashes
        variableSet.add(new LinkedHashMap<>());
        for(int i=0;i<functionParams.size();i++){
            variableSet.get(variableSet.size()-1).put(paramNames.get(i),functionParams.get(i));
        }
    }


}
