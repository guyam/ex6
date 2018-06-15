package oop.ex6.main;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import oop.ex6.main.Exceptions.AssignmentInFunctionDeclarationException;
import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.MoreThanOneEqualsException;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;
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

    private boolean firstRunner(String filePath, int scope) throws EmptyFinalDeclarationException, VariableAlreadyExistsException,
            MoreThanOneEqualsException, AssignmentInFunctionDeclarationException {

        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int lineCounter = 0;
            //todo - change the signature of regex to accept the new LINKEDHASHMAP!!
            RegexRepositorytrial variableHandler = new RegexRepositorytrial("", this.variableDict.get(FIRST_SCOPE), methodDict,
                    parenthesisCounter, true, scope);
            while ((line = bufferedReader.readLine()) != null) {
                lineCounter++;
                Pattern commentLinePattern = Pattern.compile("//");
                //System.out.println(lineCounter); //TODO DEL
                Matcher commentLine = commentLinePattern.matcher((line));
                if (line.matches("[\\t\\r]*") || commentLine.lookingAt()) {
                    //System.out.println("STRING");  //TODO DEL
                    this.visitedLineSet.add(lineCounter);
                } else {
                    variableHandler.setMethod(true);
                    variableHandler.setString(line);
                    if (variableHandler.checkMethodSyntax())
                        continue;
                    variableHandler.setMethod(false);
                    if (!variableHandler.checkSyntaxValidity())
                        throw new SyntaxException("bad syntax in line" + line);

                    //psuedocode
                    //if(!variableHandler.checkSyntaxValidity()) // should change name to syntax of javatypes
                    //variableHandler.checkFuncDecSyntax()
                    //f

                }


                // if (line.matches("\\s*")){
            }
            //if Pattern.
            //System.out.println(line);
        } catch (Exception e) { //TODO CHANGE
            e.printStackTrace();
            return false;
        }
        return true;
        //System.out.println(this.visitedLineSet);
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
