package oop.ex6.main.Regex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepository {

    String inputString;

    String[] classNames = {"String", "double", "int", "boolean", "char"};
    public static final Pattern finalTypeRegex = Pattern.compile("^final\\s.*;$");
    public static final Pattern assignmentTypeRegex = Pattern.compile("");
    public static final  Pattern generalStructureRegex = Pattern.compile("(int|boolean|double|char|String)" +
            "((.)+);$");
    public static Matcher generalStructureMatcher;
    public static final Pattern nameRegex = Pattern.compile(" *(([A-Za-z]+\\w*)|(_+\\w*)) *");
    public static final Pattern nameRegexWithValue = Pattern.compile(" *(([A-Za-z]+\\w*)|(_+\\w*)) *(=) *" +
            "(\\w+) *");




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
        Matcher nameMatcher = nameRegex.matcher(generalStructureMatcher.group(2));
        Matcher nameMatcherWithValue = nameRegexWithValue.matcher(generalStructureMatcher.group(2));
        if (!inputString.contains("=")) { // if has no =
            if (nameMatcher.matches()){
                // TODO NOAM - call constructor without =
                System.out.println("BOOM");
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
        return true;
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
        String s2 = "int                         1_1_=1;";
        //String s3 = "sdfs= =sdffd";
        // System.out.println(s3.split("=").length);
        RegexRepository r2 = new RegexRepository(s2);
        //r1.checkValidity();
        //System.out.println(r1.checkGeneralValidity());
        System.out.println(r2.checkGeneralValidity());
        int i = 0;
//        while (r2.generalStructureMatcher.find()) {
//            System.out.println(r2.generalStructureMatcher.group(i));
//            i++;
//        }
        System.out.println(r2.generalStructureMatcher.group(2));
        System.out.println(r2.checkSingleBlock());
    }


}
