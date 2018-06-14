package oop.ex6.main.Regex;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;
import oop.ex6.main.Types.*;
import java.util.TreeMap;

public class RegexRepository2 {

    private String inputString;
    public TreeMap<String, JavaType> variableSet;
    public TreeMap<String, FunctionType> functionSet;
    public int scope;


    final String functionTitleRegex = "[ \\t\\r]*void[ \\t\\r]+([a-zA-Z]+\\w?)[ \\t\\r]*\\((.*)\\)[ \\t\\r]*\\{";
    final String functionCallRegex = "[ \\t\\r]*([a-zA-Z]+\\w?)[ \\t\\r]*\\(.*[ \\t\\r]*;$";

    public RegexRepository2(String input, TreeMap<String, JavaType> varSetInput,TreeMap<String, FunctionType> funcSetInput, int scopeInput) {
        this.inputString = input;
        //TODO - guy, the correct regex matcher
        this.variableSet = varSetInput;
        this.functionSet = funcSetInput;
        this.scope = scopeInput;
    }

    public void update(String input, int scopeInput){
        scope=scopeInput;
        inputString=input;
    }

    /**
     * basicly a switchboard, checks to see what sort of line we got with the regex filter, and then handles each
     * case by directing to a different function.
     */
    public void lineHandler(){
        ///todo - regex
    }

    /**
     * this function handles a single declaration of a java type (String - int)
     */
    private void basicDecLine(String name) throws VariableAlreadyExistsException {
        JavaType

    }

    /**
     * this function handles a single declaration and initialization of a java type (String - int)
     */
    private void basicDecAndInitLine() {

    }

    private void basicassignmentLine() {

    }

}
