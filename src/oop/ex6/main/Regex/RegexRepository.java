package oop.ex6.main.Regex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepository {

    String inputString;

    String[] classNames = {"String", "double", "int", "boolean", "char"};
    public static final Pattern finalTypeRegex = Pattern.compile("^final\\s.*;$");
    public static final Pattern assignmentTypeRegex = Pattern.compile("");

    //String static final

    RegexRepository(String input) {
        this.inputString = input;
    }


    boolean checkGeneralValidity() {
        if (inputString.matches("(int|boolean|double|char|String) .+;$")) {
            return true;
        }
        //  TODO -- NOAM - DO YOU THINK TO CHECK for 2 ";" or to int/float/bools...?
        return false;
    }


    boolean checkSingleBlock() {
        if (!inputString.contains("=")) { // if has no =
            if (inputString.matches("^(int|boolean|double|char|String) (([A-Za-z]+\\w*)|(_+\\w)) \\w+;")) {
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
            if(inputString.matches("^(int|boolean|double|char|String) (([A-Za-z]+\\w*)|(_+\\w)) = .+;")) {
            // TODO NOAM - call constructor with =
            return true;
        }
        return false;
    }


    boolean checkMultiBlocks(){
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
//            return true;
//        } // if made it here it has at least one comma
//        String[] commaDivider = inputString.split(",");
//        for (String str: commaDivider){
//            System.out.println(str);
//        }
//
//
//        System.out.println("the input is: "); // todo del
//        System.out.println(inputString);
//        System.out.println(" ");
//        System.out.println("after division: "); // todo del
//        //if (inputString.contains("=")){
////           String[] eqaulsDivider = inputString.split(",");
////           for (String str: eqaulsDivider){
////               System.out.println(str);
////           }
//           //System.out.println(eqaulsDivider) ; // todo del
////        }
//        return true;
//    }

    public static void main(String[] args) {
        //String s1 = "int a1 = 7, a2, a3 = 6;";
        String s2 = "int a1__ = dfghdfgj;";
        String s3 = "sdfsd= =sdffd";
        System.out.println(s3.split("=").length);
        RegexRepository r2 = new RegexRepository(s2);
        //r1.checkValidity();
        //System.out.println(r1.checkGeneralValidity());
        System.out.println(r2.checkSingleBlock());
    }


}
