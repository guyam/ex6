package oop.ex6.main.Regex;
import oop.ex6.main.Types.JavaType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepository {

    String inputString;

    String[] classNames = {"String", "double", "int", "boolean", "char"};
    public static final Pattern finalTypeRegex = Pattern.compile("^final\\s.*;$");
    public static final Pattern assignmentTypeRegex = Pattern.compile("");
    public static final  Pattern generalStructureRegex = Pattern.compile(" *(final)? *(int|boolean|double|char|String)" +
            "((.)+);$");
    public static Matcher generalStructureMatcher;
    public static final Pattern nameRegex = Pattern.compile(" *(([A-Za-z]+[A-Za-z_0-9]*)|(_[A-Za-z_0-9]+)|" +
            "(__[A-Za-z_0-9]*)) *");
    public static final Pattern nameRegexWithValue = Pattern.compile(" *(([A-Za-z]+[A-Za-z_0-9]*)|" +
            "(_[A-Za-z_0-9]+)|(__[A-Za-z_0-9]*)) *(=) *" +"(\\w+) *");




    //String static final

    RegexRepository(String input) {
        this.inputString = input;
        this.generalStructureMatcher = this.generalStructureRegex.matcher(inputString);
    }


    boolean checkGeneralValidity() {
        if (generalStructureMatcher.matches()){
            return true;
        }
        //  TODO -- NOAM - DO YOU THINK TO CHECK for 2 ";" or to int/float/bools...?
        return false;
    }


    boolean checkSingleBlock() {
        Matcher nameMatcher = nameRegex.matcher(generalStructureMatcher.group(3));
        Matcher nameMatcherWithValue = nameRegexWithValue.matcher(generalStructureMatcher.group(3));
        if (!inputString.contains("=")) { // if has no =
            if (nameMatcher.matches()){
                if (generalStructureMatcher.group(1)!=null){ // if the is 'final' but not =
                    return false;
                }
                JavaType globalLine = new JavaType(generalStructureMatcher.group(2));
                System.out.println(nameMatcher.group(1)); // todo NOAM  del - name only
                System.out.println("BOOM");  // todo del
                return true;
            }
            return false;
        }
        // next - if more than one == - than false
        if(inputString.split("=").length >2){
            return false;
        } //made it here - has one "="
        if(nameMatcherWithValue.matches()) {
            // TODO NOAM - call constructor with =
            System.out.println("BOOM-222");
            return true;
        }
        return false;
    }


    boolean checkMultiBlocks(){ //TODO  - WILL CONTINUE NOAM
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


    boolean checkSyntaxValidity () {
        if (!checkGeneralValidity()) {
            return false;
        }
        if (!inputString.contains(",")) { // if contains
            return checkSingleBlock();
        } // has mor than one block
        return checkMultiBlocks();

    }
    //return checkMultiBlocks(); // TODO MULTI BLOCKS

//
//


    public static void main(String[] args) {
        //String s1 = "int a1 = 7, a2, a3 = 6;";
        String s2 = "  final    int        a=45     ,    a_, y =8__,      a1a1=_1_1;";
        //String s3 = "sdfs= =sdffd";
        // System.out.println(s3.split("=").length);
        RegexRepository r2 = new RegexRepository(s2);
        //r1.checkValidity();
        //System.out.println(r2.checkGeneralValidity());
        //System.out.println(r2.checkSyntaxValidity());
        //int i = 0;
//        while (r2.generalStructureMatcher.find()) {
//            System.out.println(r2.generalStructureMatcher.group(i));
//            i++;
//        }
        System.out.println(r2.checkSyntaxValidity());
//        System.out.println(r2.generalStructureMatcher.group(1));
//        System.out.println(r2.generalStructureMatcher.group(2));
//        System.out.println(r2.generalStructureMatcher.group(3));
        //System.out.println(r2.checkGeneralValidity());
        //System.out.println(r2.checkMultiBlocks());
    }


}
