package oop.ex6.main.Regex;
import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.FinalAssignmentException;
import oop.ex6.main.Exceptions.VariableAlreadyExistsException;
import oop.ex6.main.Types.JavaType;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepository {

    String inputString;
    TreeMap<String, JavaType> variableSet;
    int scope;

    String[] classNames = {"String", "double", "int", "boolean", "char"};
    public static final Pattern finalTypeRegex = Pattern.compile("^final\\s.*;$");
    public static final Pattern assignmentTypeRegex = Pattern.compile("");
    public static final  Pattern generalStructureRegex = Pattern.compile("[ \\t\\r]*(final)?[ \\t\\r]*(int|boolean|double|char|String)" +
            "((.)+);$");
    public static Matcher generalStructureMatcher;
    public static final Pattern nameRegex = Pattern.compile("[ \\t\\r]*(([A-Za-z]+[A-Za-z_0-9]*)|(_[A-Za-z_0-9]+)|" +
            "(__[A-Za-z_0-9]*))[ \\t\\r]*");
    public static final Pattern nameRegexWithValue = Pattern.compile("[ \\t\\r]*(([A-Za-z]+[A-Za-z_0-9]*)|" +
            "(_[A-Za-z_0-9]+)|(__[A-Za-z_0-9]*))[ \\t\\r]*(=)[ \\t\\r]*" +"(\\w+)[ \\t\\r]*");
    public static final Pattern subnameRegexWithValue = Pattern.compile("([ \\t\\r]*(" +
            "((\\w)+)[ \\t\\r]*=[ \\t\\r]*((\\w)+)[ \\t\\r]*))");

    public RegexRepository(String input, TreeMap<String, JavaType> varSetInput, int scopeInput) {
        this.inputString = input;
        this.generalStructureMatcher = this.generalStructureRegex.matcher(inputString);
        this.variableSet = varSetInput;
        this.scope = scopeInput;
    }


    public void setString (String newString){
        this.inputString = newString;
        this.generalStructureMatcher = this.generalStructureRegex.matcher(newString);
    }


    boolean checkGeneralValidity() {
        if (generalStructureMatcher.matches()){
            return true;
        }
        //  TODO -- NOAM - DO YOU THINK TO CHECK for 2 ";" or to int/float/bools...?
        return false;
    }


    boolean checkSingleBlock(String lineName) throws EmptyFinalDeclarationException{//, VariableAlreadyExistsException {
        Matcher nameMatcher = nameRegex.matcher(lineName);
        Matcher nameMatcherWithValue = nameRegexWithValue.matcher(lineName);

        if (!lineName.contains("=")) { // if has no =
            if (nameMatcher.matches()){
////                // TODO DEBUG
//                System.out.println("WE GOT");
//                System.out.println(generalStructureMatcher.group(1));
////                // TODO DEBUG
                if (generalStructureMatcher.group(1)!=null){ // if the is 'final' but not =
                    //System.out.println("YES1");
                    System.out.println("EXCEPTION!!");
                    // TODO EXCEPTION!!!!!
                    return false;
                }
                if ((variableSet.containsKey(lineName.trim()))&&(variableSet.get(lineName.trim()).getScope
                        ()==scope)){
                    //throw new VariableAlreadyExistsException();
                    //System.out.println("EXCEPTION"); // todo del;
                }
                variableSet.put(lineName.trim(), new JavaType(generalStructureMatcher.group(2), scope));
                System.out.println("BOOM");  // todo del
                return true;
            }
            return false;
        }
        // next - if more than one == - than false
        if(lineName.split("=").length >2){
            System.out.println("EXCEPTION!!");
            // TODO EXCEPTION!!!!!
            return false;
        } //made it here - has one "="
        //Matcher subStringWithValue =  subnameRegexWithValue.matcher(lineName);
        //System.out.println(lineName); // todo chenge
        String[] varAndVal = lineName.split("=");
        boolean isFinal = generalStructureMatcher.group(1)!=null;
        String var = varAndVal[0].trim();
        String val = varAndVal[1].trim();
        String typeName = generalStructureMatcher.group(2);
        if(nameMatcherWithValue.matches()) {
            if ((variableSet.containsKey(var))&&(variableSet.get(var).getScope()==scope)){
                //throw new VariableAlreadyExistsException();
            }
            variableSet.put(var, new JavaType(typeName,val,isFinal, scope));
            System.out.println("BOOM-222");
            return true;
        }
        return false;
    }


    boolean checkMultiBlocks() throws EmptyFinalDeclarationException{ //TODO  - WILL CONTINUE NOAM
        //Matcher nameMatcher = nameRegex.matcher(generalStructureMatcher.group(3));
        String lineWithComma = generalStructureMatcher.group(3);
        String[] dividedStringArray = lineWithComma.split(","); // TODO MAKE SURE ASSUMPTION COMMA IS CORRECT
        //System.out.println(lineWithComma);
        for (String subString: dividedStringArray){ //TODO DEL
            //System.out.println(subString);
            if (!subStringNameChecker(subString)){
                return false;
            } // made it here - all name ok
        }
        for (String subString: dividedStringArray) { //TODO DEL
                checkSingleBlock(subString);

        }
            //System.out.println(dividedStringArray);  //TODO DEL
        return true;
    }


    boolean subStringNameChecker(String subString){
        Matcher subStringnameMatcher = nameRegex.matcher(subString);
        Matcher subStringnameMatcherWithValue = nameRegexWithValue.matcher(subString);
        if (!subString.contains("=")){ // with no '='
            //System.out.println("no ="); // TODO DEL
            return subStringnameMatcher.matches();
        }
        //System.out.println("YESSS ="); // TODO DEL
        return subStringnameMatcherWithValue.matches();
    }


    public boolean checkSyntaxValidity() throws EmptyFinalDeclarationException {
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
//    public static void main(String[] args) {
////        //String s1 = "int a1 = 7, a2, a3 = 6;";
//        String s2 = "  final    int        a=45    ;";
////        //String s3 = "sdfs= =sdffd";
////        // System.out.println(s3.split("=").length);
//        RegexRepository r2 = new RegexRepository(s2, null);
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
