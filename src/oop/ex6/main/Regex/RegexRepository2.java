package oop.ex6.main.Regex;

import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;

import oop.ex6.main.Types.*;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepository2 {

    private String inputString;
    public ArrayList<LinkedHashMap<String, JavaType>> variableSet;
    public TreeMap<String, FunctionType> functionSet;
    public int scope;
    public static Matcher generalConditionMatcher;



    public static final Pattern generalCondition = Pattern.compile("[ \\t\\r]*(if|while)[ \\t\\r]*" +
            "(\\()[ \\t\\r]*((.)*)[ \\t\\r]*(\\))[ \\t\\r]*(\\{)[ \\t\\r]*");
    public static final Pattern booleanReservedWord = Pattern.compile("[ \\t\\r]*(true|false)[ \\t\\r]*");
    public static final Pattern booleanVar = Pattern.compile("[ \\t\\r]*((.)*)[ \\t\\r]*[ " +
            "\\t\\r]*");

    public RegexRepository2(String input, ArrayList<LinkedHashMap<String, JavaType>> varSetInput, TreeMap<String,FunctionType> funcSetInput, int scopeInput) {
        this.inputString = input;
        //TODO - guy, the correct regex matcher
        this.variableSet = varSetInput;
        this.functionSet = funcSetInput;
        this.scope = scopeInput;
        this.generalConditionMatcher = this.generalCondition.matcher(inputString);
    }

    public void update(String input, int scopeInput, ArrayList<LinkedHashMap<String, JavaType>> newVarDict) {
        scope = scopeInput;
        inputString = input;
        this.generalConditionMatcher = this.generalCondition.matcher(inputString);
        this.variableSet = newVarDict;
    }


    public boolean singleBoolBlockChecker(String singleBlock){
        Matcher singleReservedWordMatcher = booleanReservedWord.matcher(singleBlock);
        Matcher singleBoolValMatcher = booleanVar.matcher(singleBlock);
        if (singleReservedWordMatcher.matches()){
            System.out.println("TRUE FALSE WORD"); // TODO DEL
            return true;
        }
        if (!singleBoolValMatcher.matches()){
            return false;
        }
        String boolVar = singleBlock.trim();
        int scopeOfRelevantVar= wasBoolDeclared(boolVar);
        if (scopeOfRelevantVar!= -1){ // it is a variable
            String boolVarType = variableSet.get(scopeOfRelevantVar).get(boolVar).getType();
            if (JavaType.contains(JavaType.compatibleTypes.get("boolean"), boolVarType)){
                System.out.println("BOOLEAN GLOBAL VARIABLE!"); // TODO DEL
                return true;
            }
            System.out.println("EXCEPTION NOT BOOLEAN VARIABLE"); // TODO ADD EXCEPTION
            return false;
        }
        System.out.println(boolVar);
        //System.out.println("NOT IN DICT");
        if (JavaType.isDouble(boolVar)||JavaType.isInt(boolVar)){
            System.out.println("THIS IS AN INT OR A DOUBLE");
            return true;
        }
        System.out.println("EXCEPTION! REACH END! NOT VALID CONDITION"); // TODO ADD EXCEPTION
        return false;
    }


    private int wasBoolDeclared(String varName){
        for (int i = scope; i>=0; i--){
            if (variableSet.get(i).containsKey(varName)){
                return i;
            }
        }
        return -1;
    }


    public boolean checkBooleanSyntax(){
        if (!generalValidityChecker()){ // check general syntax
            //System.out.println("BAD BOOLEAN EXCEPTION"); //
            return false;
        }
        String boolCondInput = this.generalConditionMatcher.group(3);
        //System.out.println(boolCondInput);
        if (!boolCondInput.contains("||")&&!boolCondInput.contains("&&")) {
            System.out.println("ONLY SINGLE BLOCK TO CHECK"); // TODO DEL
            return singleBoolBlockChecker(boolCondInput);
        }
        System.out.println("MULTI BLOCKS");// TODO DEL
        ArrayList<String> condBlockArray = combinedConditionBlocks(boolCondInput);
        for (String conditionBlock : condBlockArray){
            if (singleBoolBlockChecker(conditionBlock)){
                continue;
            }
            System.out.println("EXCEPTION! PROBLEM WITH AT LEAST ONE BLOCK");
            return false; //TODO ADD EXCEPTION
        }

        return true; // TODO CHANGE!!!!!
    }


    private ArrayList<String> combinedConditionBlocks(String fullString){
        String[] orOpStrings = fullString.split("\\|\\|");
        ArrayList<String> allConditions = new ArrayList<>();
        for (String str: orOpStrings){
            if (!str.contains("&&")){
                allConditions.add(str);
                continue;
            }
            String[] andConds = str.split("(&&)");
            for (String localString: andConds){
                allConditions.add(localString);
            }
        }
//        System.out.println("THE SIZE IS");// todo DEL
//        System.out.println(allConditions.size()); // todo DEL
//        for (String l: allConditions){ // todo DEL
//            System.out.println(l); // todo DEL
//        } // todo DEL
        return  allConditions;
    }


    /**
     * basicly a switchboard, checks to see what sort of line we got with the regex filter, and then handles each
     * case by directing to a different function.
     */
    public boolean generalValidityChecker() {
        return generalConditionMatcher.matches();
    }

    /**
     * this function handles a single declaration of a java type (String - int)
     */
    private void basicDecLine(String name, String type) throws VariableAlreadyExistsException {
        //check if there is already a variable with the same name in the code
        if (variableSet.get(scope).containsKey(name))
            throw new VariableAlreadyExistsException();
        // let's add it to the variable set.
        variableSet.get(scope).put(name,new JavaType(type, scope));
    }

    /**
     * this function handles a single declaration and initialization of a java type (String - int)
     */
    private void basicDecAndInitLine(String name, String type, String data, boolean isFinal) throws VariableAlreadyExistsException, EmptyFinalDeclarationException {
        //check if there is already a variable with the same name in the code
        if (variableSet.get(scope).containsKey(name))
            throw new VariableAlreadyExistsException();
        variableSet.get(scope).put(name, new JavaType(type, data, isFinal, scope));
    }


    private void basicassignmentLine() {


    }


    public static void main(String args[]){
        JavaType var1 = new JavaType("boolean", 0);
        String s1 = "   while (      a12||  false && true && -12.98  )  {  " ;
        ArrayList<LinkedHashMap<String, JavaType>> variableDictTest = new ArrayList<>();
        variableDictTest.add(new LinkedHashMap<>());
        variableDictTest.get(0).put("a12", var1);
        RegexRepository2 r2 = new RegexRepository2(s1, variableDictTest, null, 0);
        System.out.println(r2.checkBooleanSyntax());
        //System.out.println(r2.generalConditionMatcher.group(1));
        //System.out.println(r2.generalConditionMatcher.group(2));
       // System.out.println(r2.generalConditionMatcher.group(3));

    }

}
