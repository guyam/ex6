package oop.ex6.main.Regex;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import oop.ex6.main.Exceptions.*;
import oop.ex6.main.Types.FunctionType;
import oop.ex6.main.Types.JavaType;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepositorytrial {

    String inputString;
    LinkedHashMap<String, JavaType> variableSet;
    TreeMap<String, FunctionType> methodSet;
    Stack<Integer> parenthasisCounterStack;
    boolean isMethod;
    int scope;

    String[] classNames = {"String", "double", "int", "boolean", "char"};
    //public static final Pattern finalTypeRegex = Pattern.compile("^final\\s.*;$");
    //public static final Pattern assignmentTypeRegex = Pattern.compile("");
    public static final Pattern generalStructureRegex = Pattern.compile("[ \\t\\r]*(final)?[ \\t\\r]*(int|boolean|double|char|String)" +
            "((.)+);$");
    public static Matcher generalStructureMatcher;
    public static Matcher methodGeneralNameMatcher;

    public static final Pattern nameRegex = Pattern.compile("[ \\t\\r]*(([A-Za-z]+[A-Za-z_0-9]*)|(_[A-Za-z_0-9]+)|" +
            "(__[A-Za-z_0-9]*))[ \\t\\r]*");
    public static final Pattern nameRegexWithValue = Pattern.compile("[ \\t\\r]*(([A-Za-z]+[A-Za-z_0-9]*)|" +
            "(_[A-Za-z_0-9]+)|(__[A-Za-z_0-9]*))[ \\t\\r]*(=)[ \\t\\r]*" + "(.+)[ \\t\\r]*");
    public static final Pattern subnameRegexWithValue = Pattern.compile("([ \\t\\r]*(" +
            "((\\w)+)[ \\t\\r]*=[ \\t\\r]*((\\w)+)[ \\t\\r]*))");

    //public static final Pattern methodGeneralName = Pattern.compile("[ \\t\\r]*(void)[\\t\\r]*" +
    //      "([A-Za-z]+[A-Za-z_0-9]*)[ \\t\\r]*[(][ \\t\\r]*(.)*[\\t\\r]*[)][\\t\\r]*[{][\\t\\r]*");
    public static final Pattern methodGeneralName = Pattern.compile("[ \\t\\r]*(void)[ \\t\\r]*" +
            "([A-Za-z]+[A-Za-z_0-9]*)[ \\t\\r]*(\\()[ \\t\\r]*((.)*)[ \\t\\r]*(\\))[ \\t\\r]*(\\{)[ " +
            "\\t\\r]*");
    public static final Pattern endsWithClosedCurlyBrackets = Pattern.compile(".*}[ \\t\\r]*$");
    public static final Pattern endsWithOpenCurlyBrackets = Pattern.compile(".*\\{[ \\t\\r]*$");
    public static final Pattern returnLine = Pattern.compile("[ \\t\\r]*(return)[ \\t\\r]*;[ \\t\\r]*$");

    /**
     * class constructor - used to initiliaze the regexRepository
     *
     * @param input           - the input line
     * @param varSetInput     the LinkedHashSet which contains all of of the variables declared in this scope
     * @param methodSetInput  the ordered dictionary which contains all of the methods declared in this scope
     * @param counter         a stack that counts the open and closed parenthesis, so we can keep track of the scope we are in
     * @param isParsingMethod a boolean value that holds whether we are looking at a a method declaration line, or a
     *                        simple variable declaration line - this matters because in a function declaration line
     * @param scopeInput      the scope number
     */
    public RegexRepositorytrial(String input, LinkedHashMap<String, JavaType> varSetInput, TreeMap<String, FunctionType>
            methodSetInput, Stack<Integer> counter, boolean isParsingMethod, int scopeInput) {
        this.inputString = input;
        this.generalStructureMatcher = this.generalStructureRegex.matcher(inputString);
        this.methodGeneralNameMatcher = this.methodGeneralName.matcher(inputString);
        this.variableSet = varSetInput;
        this.methodSet = methodSetInput;
        this.scope = scopeInput;
        this.parenthasisCounterStack = counter;
        this.isMethod = isParsingMethod;
    }


    public void setString(String newString) {
        this.inputString = newString;
        this.generalStructureMatcher = this.generalStructureRegex.matcher(newString);
        this.methodGeneralNameMatcher = this.methodGeneralName.matcher(newString);

    }





    public void setParenthasisCounterStack(Stack<Integer> newParenthesisCounter){
        this.parenthasisCounterStack = newParenthesisCounter;
    }

    public boolean checkGeneralMethodName() {
        return methodGeneralNameMatcher.matches(); // true if name matches
    }


    boolean checkGeneralValidity() { // for variables only!
        return generalStructureMatcher.matches();
    }
//                    return true;

//        //  TODO -- NOAM - DO YOU THINK TO CHECK for 2 ";" or to int/float/bools...?
//        return false;
//    }

// todo - add getter setter
    // todo - add dowith comas


    public String getMehodName(){
        if (!checkGeneralMethodName()){
            return "BAD"; //TODO CHANGE !!!!!!!!!!
        }
        String methodName = methodGeneralNameMatcher.group(2);
        return methodName;
    }


    public boolean checkMethodSyntax() throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException,EmptyAssignmentException {
        if (!checkGeneralMethodName()) { // if method name not good
            return false;
        } // if made it here - general signature structure good
        String methodName = methodGeneralNameMatcher.group(2);
        String parameterList = methodGeneralNameMatcher.group(4);
        //first, we'll check if the method doesn't have any parameters. in this case, we will just add the function's
        //name to the set of functions
        boolean parameterlessMethod = parameterList.matches("[ \\t\\r]*");
        if (parameterlessMethod) {
            this.methodSet.put(methodName, new FunctionType(new LinkedHashMap<>()));
            increaseScope();
            return true; // todo change
        } // TODO !!!! - UPDATED STACK OF "{}"
        //now, we'll check if the method only has one parameter
        LinkedHashMap<String, JavaType> localVarSet = new LinkedHashMap<>();
        if (!parameterList.contains(",")) { // no comma
            String parameters = parameterList + ";";
            //System.out.println(parameters); // todo del
            RegexRepositorytrial methodParameterChecker = new RegexRepositorytrial(parameters, localVarSet, null,
                    null, true, 1);
            //System.out.println(methodParameterChecker.checkSyntaxValidity());
            methodParameterChecker.checkSyntaxValidity();
            this.methodSet.put(methodName, new FunctionType(localVarSet));
            increaseScope();
            return true; // TODO CHANGE??
        } // made it here- contains commas
        LinkedHashMap<String, JavaType> localVars = new LinkedHashMap<>();
        increaseScope();
        RegexRepositorytrial methodParameterChecker = new RegexRepositorytrial(parameterList, localVars, null,
                this.parenthasisCounterStack, true, scope);
        String[] comalessParams = parameterList.split(",");
        for (String parSubString : comalessParams) {
            String param = parSubString + ";";
            methodParameterChecker.setString(param);
            if (!methodParameterChecker.checkSyntaxValidity())
                return false;
        }
        this.methodSet.put(methodName, new FunctionType(localVars));

//        System.out.println(parameterList);
//        System.out.println("A");
//        System.out.println(comalessParams[0]);
//        System.out.println(comalessParams[1]);
        return true;
    }

    private void increaseScope() {
        parenthasisCounterStack.push(0);
        scope++;
    }


    public boolean isMethod() {
        return isMethod;
    }

    public void setMethod(boolean method) {
        isMethod = method;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    boolean checkSingleBlock(String lineName) throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException, SyntaxException, EmptyAssignmentException {
        Matcher nameMatcher = nameRegex.matcher(lineName);
        Matcher nameMatcherWithValue = nameRegexWithValue.matcher(lineName);

        //case 1: checks if we are assigning a value to a parameter (includes a '=' in a function declaration line)
        if (lineName.contains("=") && isMethod) {
            System.out.println("tried assigning a value in a function declaration line");
            return false;
        }

        /*
        next, we'll check all of the cases where we don't have a '='. this is a bit complex, as it can be any one of the
        following cases:
        1. This is a simple assignment from the global scale. in this case, we need to check that it doesn't have a
        final prefix, as not assigning a value to a variable with a "final" declaration is illegal. Otherwise it's fine.
        2. This is a a parameter in the function declaration line, where anything goes.

         */
        if (!lineName.contains("=")) {
            //doesn't contain "="
            if (nameMatcher.matches()) {
                //parse the text according to the regex
                if (generalStructureMatcher.group(1) != null) {
                    // the word "final" exists.
                    if (!isMethod) {
                        // this is a bad declaration using a final prefix in the global scope.
                        throw new EmptyFinalDeclarationException();
                    } else {
                        // the word final exists, but we are in a function declaration line
                        //let's checks if we didn't already declare this variable in this scope
                        String trimmedName = lineName.trim(); // the actual variable name
                        if ((variableSet.containsKey(trimmedName)) && (variableSet.get(trimmedName).getScope() == scope)) {
                            //the variable already exists in this scope. we'll throw an error
                            throw new VariableAlreadyExistsException();
                        } else {
                            //we're good - meaning this is an ok javatype. we'll call the constructor that "ignores" final declaration,
                            // and add it to the variables list


                            variableSet.put(trimmedName, new JavaType(generalStructureMatcher.group(2),
                                    generalStructureMatcher.group(1) != null, scope));
                            return true;
                        }
                    }
                }
                // //todo - freddy, if we got here, the word final doesn't exists? so should it be simply "int a"?
                //handles the case where it is like "int a". checks if a duplicate exists, and if not, creates a new
                //empty variable
                if ((variableSet.containsKey(lineName.trim())) && (variableSet.get(lineName.trim()).getScope() == scope))
                    throw new VariableAlreadyExistsException();
                variableSet.put(lineName.trim(), new JavaType(generalStructureMatcher.group(2), scope));
                return true;
            }
            //todo - freddy, don't get what this piece of code it supposed to do? logically, it means the matcher didn't match, so we should throw an excpetion?
                /*
                System.out.println("EXCEPTION!!");
                */
            return false;
        }


        // next - if more than one == - than false
        else if (lineName.split("=").length > 2) {
            throw new MoreThanOneEqualsException();
        } else {
            //made it here - has exactly one "="
            String[] varAndVal = lineName.split("=");
            //now, we'll check is we are declaring a parameter in a function decalaration line. this is illegal,
            //as parameters in the the function declaration cannot have a "=".
            if (isMethod)
                throw new AssignmentInFunctionDeclarationException();

            //we reached here. that means we have one "=" sign, and we are in a global declaration of a javatype.
            boolean isFinal = generalStructureMatcher.group(1) != null;
            String var = varAndVal[0].trim();
            String val;
            try {
                val = varAndVal[1].trim();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EmptyAssignmentException();
            }
            String typeName = generalStructureMatcher.group(2);
            if (nameMatcherWithValue.matches()) {
                if ((variableSet.containsKey(var)) && (variableSet.get(var).getScope() == scope)) {
                    throw new VariableAlreadyExistsException();
                }
                variableSet.put(var, new JavaType(typeName, val, isFinal, scope));
                //System.out.println("plain old dec and init");
                return true;
            }
            throw new SyntaxException("line syntax is incorrect");
        }
    }


    public boolean innerScopeLine(String previousLine) {

        Matcher matcherClosed = endsWithClosedCurlyBrackets.matcher(inputString);
        Matcher matcherOpen = endsWithOpenCurlyBrackets.matcher(inputString);
        Matcher matchesReturnLine = returnLine.matcher(previousLine);

        if (matcherOpen.matches()) {
            increaseScope();
            return true;
        }
        if (matcherClosed.matches()) {
            switch (parenthasisCounterStack.size()) {
                case 0:
                    return false;
                case 1: {
                    if (matchesReturnLine.matches()) {
                        decreaseScope();
                        return true;
                    }
                    return false;
                }
                default:
                    decreaseScope();
                    return true;
            }
        }
        return true;
    }

    private void decreaseScope() {
        parenthasisCounterStack.pop();
        scope--;
    }

    boolean checkMultiBlocks() throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException,EmptyAssignmentException { //TODO  - WILL CONTINUE NOAM
        //Matcher nameMatcher = nameRegex.matcher(generalStructureMatcher.group(3));
        String lineWithComma = generalStructureMatcher.group(3);
        String[] dividedStringArray = lineWithComma.split(","); // TODO MAKE SURE ASSUMPTION COMMA IS CORRECT
        //System.out.println(lineWithComma);
        for (String subString : dividedStringArray) { //TODO DEL
            //System.out.println(subString);
            if (!subStringNameChecker(subString)) {
                //System.out.println("EXCEPTION!!");
                return false;
            } // made it here - all name ok
        }
        for (String subString : dividedStringArray) { //TODO DEL
            checkSingleBlock(subString);
        }
        return true;
    }


    boolean subStringNameChecker(String subString) {
        Matcher subStringnameMatcher = nameRegex.matcher(subString);
        Matcher subStringnameMatcherWithValue = nameRegexWithValue.matcher(subString);
        if (!subString.contains("=")) { // with no '='
            //System.out.println("no ="); // TODO DEL
            return subStringnameMatcher.matches();
        }
        //System.out.println("YESSS ="); // TODO DEL
        return subStringnameMatcherWithValue.matches();
    }


    public boolean checkSyntaxValidity() throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException,EmptyAssignmentException {
//        try{
        if (!checkGeneralValidity()) {
            return false;
        }
        if (!inputString.contains(",")) { // if contains
            String fullComalessLine = generalStructureMatcher.group(3);
            return checkSingleBlock(fullComalessLine);
        } // has mor than one block
        return checkMultiBlocks();
//    }
//    catch(VariableAlreadyExistsException e1 | EmptyFinalDeclarationException e2){
//            return false;

    }
//    }

    //return checkMultiBlocks(); // TODO MULTI BLOCKS

//
//

    //
    public static void main(String[] args) throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException,EmptyAssignmentException {
        TreeMap<String, FunctionType> methodMap1 = new TreeMap<>();
        LinkedHashMap<String, JavaType> varSet1 = new LinkedHashMap<>();
        Stack<Integer> testStack = new Stack<>();
        String s1 = "   void getNoam1  (final int a1, final char b, char c)      {";
        String[] array1 = s1.split(",");
        printAll(array1);
        //System.out.println(array1[1:array1.length]);
        RegexRepositorytrial r2 = new RegexRepositorytrial(s1, varSet1, methodMap1, testStack, true, 0);
        //System.out.println(r2.checkMethodSyntax());
    }

    public static void printAll(String[] array) {
        for (String obj : array)
            System.out.println(obj);
    }
//        String s2 = "  final    int        a=45    ;";
////        //String s3 = "sdfs= =sdffd";
////        // System.out.println(s3.split("=").length);
////        //r1.checkValidity();
////        //System.out.println(r2.checkGeneralValidity());
////        //System.out.println(r2.checkSyntaxValidity());
////        //int i = 0;
//////        while (r2.generalStructureMatcher.find()) {
//////            System.out.println(r2.generalStructureMatcher.group(i));
//////            i++;
//////        }
//        System.out.println(r2.checkSyntaxValidity());
//        System.out.println(r2.generalStructureMatcher.group(1));
//        System.out.println(r2.generalStructureMatcher.group(2));
//        System.out.println(r2.generalStructureMatcher.group(3));
//        //System.out.println(r2.checkGeneralValidity());
//        //System.out.println(r2.checkMultiBlocks());
//    }
    // hi guy


}


