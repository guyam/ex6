package oop.ex6.main;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import oop.ex6.main.Exceptions.*;
import oop.ex6.main.Regex.RegexRepositorytrial;
import oop.ex6.main.Types.FunctionType;
import oop.ex6.main.Types.JavaType;

import java.io.*;
import java.util.regex.*;
import java.util.*;


public class Sjavac {


    public Set<Integer> visitedLineSet;
    public ArrayList<LinkedHashMap<String, JavaType>> variableDict;
    public TreeMap<String, FunctionType> methodDict;
    public Stack<Integer> parenthesisCounter;
    public static int FIRST_SCOPE = 0;
    public static int SECOND_SCOPE = 1;


    Sjavac() {
        this.visitedLineSet = new HashSet<>();
        this.variableDict = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            variableDict.add(new LinkedHashMap<>());
        }
        this.methodDict = new TreeMap<>();
        parenthesisCounter = new Stack<>();
    }

    //todo - second runner: skip lines we already visited, and after each iteration, update the scope to match the
    //todo Repository's scope (using getscope)

    private boolean firstRunner(String filePath, int scope) {

        File file = new File(filePath);
        FileReader fileReader = null;
        String prevLine = "";
        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int lineCounter = 0;
            RegexRepositorytrial variableHandler = new RegexRepositorytrial("", this.variableDict.get(FIRST_SCOPE), methodDict,
                    parenthesisCounter, true, scope);
            while ((line = bufferedReader.readLine()) != null) {
                //we have reached a not empty line
                //let's check if it's a comment line.
                lineCounter++;
                Pattern commentLinePattern = Pattern.compile("//");
                Matcher commentLine = commentLinePattern.matcher((line));

                if (line.matches("[\\t\\r]*") || commentLine.lookingAt()) {
                    //we have reached a cooment line! now we can unceremoniously skip
                    this.visitedLineSet.add(lineCounter);
                    continue;
                }

                //it is not a comment line. let's check it out!
                //first, we'll check if we are in the global scope
                if (parenthesisCounter.isEmpty()) {
                    //we are in the global scope! we'll now check and handle both legal cases in the global scope,
                    //variable handling and declaration,
                    if (!golbalScopeHandler(line, variableHandler))
                        return false;
                }
                //if we have reached here, that means that we are NOT in the global scope. so we will just keep track of
                //the number of brackets opened and closed
                else {
                    if (!innerScopeHandler(line,prevLine,variableHandler))
                        throw new NoReturnValueException();
                }
                prevLine = line;
                visitedLineSet.add(lineCounter);
            }
            //if Pattern.
            //System.out.println(line);
        } catch (Exception e) { //TODO CHANGE
            e.printStackTrace();
            return false;
        }
        return parenthesisCounter.isEmpty();
        //System.out.println(this.visitedLineSet);
    }

    private boolean golbalScopeHandler(String line, RegexRepositorytrial variableHandler) throws EmptyFinalDeclarationException, VariableAlreadyExistsException, MoreThanOneEqualsException, AssignmentInFunctionDeclarationException {
        variableHandler.setMethod(true);
        variableHandler.setString(line);
        if (variableHandler.checkMethodSyntax()) {
            return true;
        }
        variableHandler.setMethod(false);
        if (!variableHandler.checkSyntaxValidity())
            throw new SyntaxException("bad syntax in line" + line);
        return true;
    }

    private boolean innerScopeHandler(String currentLine,String prevLine, RegexRepositorytrial variableHandler) {
        variableHandler.setString(currentLine);
        return variableHandler.innerScopeLine(prevLine);
    }

    public static void main(String[] args) throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException {
        // TODO  - CHEK PARAMETERS???
        String pathName = args[0];
        Sjavac validator = new Sjavac();
        if (validator.firstRunner(pathName, 0)) {
            for (LinkedHashMap<String, JavaType> tree : validator.variableDict) {
                for (String treeKey : tree.keySet()) {
                    System.out.println(tree.get(treeKey));
                    System.out.println("also, its name is : " + treeKey);
                }
            }
            for (FunctionType function : validator.methodDict.values()) {
                System.out.println(function);
            }
        } else {
            System.out.println("no good");
        }
    }


}
