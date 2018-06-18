package oop.ex6.main.Regex;

import oop.ex6.main.Exceptions.BadBooleanConditionException;
import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;

import oop.ex6.main.Types.*;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    public static final Pattern methodCallRegex = Pattern.compile("[ \\t\\r]*([A-Za-z]+[A-Za-z_0-9]*)[ \\t\\r]*(\\()[ \\t\\r]*((.)*)[ \\t\\r]*(\\))[ \\t\\r]*;[ \\t\\r]*");

    public RegexRepository2(String input, ArrayList<LinkedHashMap<String, JavaType>> varSetInput, TreeMap<String, FunctionType> funcSetInput, int scopeInput) {
        this.inputString = input;
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


    public boolean singleBoolBlockChecker(String singleBlock) {
        Matcher singleReservedWordMatcher = booleanReservedWord.matcher(singleBlock);
        Matcher singleBoolValMatcher = booleanVar.matcher(singleBlock);
        if (singleReservedWordMatcher.matches()) {
            System.out.println("TRUE FALSE WORD"); // TODO DEL
            return true;
        }
        if (!singleBoolValMatcher.matches()) {
            return false;
        }
        String boolVar = singleBlock.trim();
        int scopeOfRelevantVar = wasBoolDeclared(boolVar);
        if (scopeOfRelevantVar != -1) { // it is a variable
            String boolVarType = variableSet.get(scopeOfRelevantVar).get(boolVar).getType();
            if (JavaType.contains(JavaType.compatibleTypes.get("boolean"), boolVarType)) {
                //System.out.println("BOOLEAN GLOBAL VARIABLE!"); // TODO DEL
                return true;
            }
            return false;
        }
        System.out.println(boolVar);
        //System.out.println("NOT IN DICT");
        if (JavaType.isDouble(boolVar) || JavaType.isInt(boolVar)) {
           // System.out.println("THIS IS AN INT OR A DOUBLE");
            return true;
        }
        //System.out.println("EXCEPTION! REACH END! NOT VALID CONDITION");
        return false;
    }


    private int wasBoolDeclared(String varName) {
        for (int i = scope; i >= 0; i--) {
            if (variableSet.get(i).containsKey(varName)) {
                return i;
            }
        }
        return -1;
    }


    public boolean checkBooleanSyntax() {
        if (!generalValidityChecker()) { // check general syntax
            //System.out.println("BAD BOOLEAN EXCEPTION"); //
            return false;
        }
        String boolCondInput = this.generalConditionMatcher.group(3);
        //System.out.println(boolCondInput);
        if (!boolCondInput.contains("||") && !boolCondInput.contains("&&")) {
            //System.out.println("ONLY SINGLE BLOCK TO CHECK"); // TODO DEL
            return singleBoolBlockChecker(boolCondInput);
        }
        //System.out.println("MULTI BLOCKS");// TODO DEL
        ArrayList<String> condBlockArray = combinedConditionBlocks(boolCondInput);
        for (String conditionBlock : condBlockArray) {
            if (singleBoolBlockChecker(conditionBlock)) {
                continue;
            }
            return false;
        }

        return true;
    }


    private ArrayList<String> combinedConditionBlocks(String fullString) {
        String[] orOpStrings = fullString.split("\\|\\|");
        ArrayList<String> allConditions = new ArrayList<>();
        for (String str : orOpStrings) {
            if (!str.contains("&&")) {
                allConditions.add(str);
                continue;
            }
            String[] andConds = str.split("(&&)");
            for (String localString : andConds) {
                allConditions.add(localString);
            }
        }
//        System.out.println("THE SIZE IS");// todo DEL
//        System.out.println(allConditions.size()); // todo DEL
//        for (String l: allConditions){ // todo DEL
//            System.out.println(l); // todo DEL
//        } // todo DEL
        return allConditions;
    }


    /**
     * basicly a switchboard, checks to see what sort of line we got with the regex filter, and then handles each
     * case by directing to a different function.
     */
    public boolean generalValidityChecker() {
        return generalConditionMatcher.matches();
    }

    public boolean getParamsandCheck(String funcName, List<String> funcParamNames) {
        if (!functionSet.containsKey(funcName))
            return false;
        ArrayList<String> paramTypes = getTypes(funcParamNames);
        if (paramTypes == null)
            return false;
        return functionSet.get(funcName).sameSignature(paramTypes);
    }

    public ArrayList<String> getCalledMeth() {
        Matcher methodCallMatcher = methodCallRegex.matcher(inputString);
        ArrayList<String> calledMethodParams = new ArrayList<>();
        methodCallMatcher.matches();
        String methName = methodCallMatcher.group(1);
        calledMethodParams.add(methName);
        String params = methodCallMatcher.group(3);
        System.out.println(params);
        if (!params.contains(",")) {
            String spacelessParam = params.trim();
            if (spacelessParam.matches("[ \\t\\r]*")) {
                calledMethodParams.add(null);
                return calledMethodParams;
            }
            calledMethodParams.add(spacelessParam);
            return calledMethodParams;
        }
        String[] splitParams = params.split(",");
        for (String singleParam : splitParams) {
            String spacelessParam = singleParam.trim();
            calledMethodParams.add(spacelessParam);
            //singleParam.trim().getChars(0);
        }
        return calledMethodParams;
    }

    public boolean checkMethodCallSyntax() {
        Matcher methodCallMatcher = methodCallRegex.matcher(inputString);
        return methodCallMatcher.matches();
    }

    private ArrayList<String> getTypes(List<String> funcLiterals) {
        ArrayList<String> types = new ArrayList<>();
        if (funcLiterals.get(0) == null)
            return types;
        for (String funcParameter : funcLiterals) {
            String type = JavaType.returnType(funcParameter);
            if (type != null) {
                types.add(type);
            } else {
                type = findTypeofParam(funcParameter);
                if (type == null)
                    return null;
            }
        }
        return types;
    }

    private String findTypeofParam(String name) {
        for (int i = variableSet.size() - 1; i >= 0; i++) {
            if (variableSet.get(i).containsKey(name))
                return variableSet.get(i).get(name).getType();
        }
        return null;
    }


    public static void main(String args[]) {

    }

}
