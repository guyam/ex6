package oop.ex6.main;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import java.io.*;
import java.util.regex.*;
import java.util.*;





public class Sjavac {


   public Set<Integer> visitedLineSet;

    Sjavac(){
        this.visitedLineSet  = new HashSet<>();
    }


    public static void main(String[] args) throws IOException {
        Sjavac validator = new Sjavac();
        String filePath = args[0];
        // .mathces();
        //
        //String filePath = "testFile";
        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int lineCounter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineCounter++;
                Pattern commentLinePattern = Pattern.compile("//");
                System.out.println(lineCounter);
                //Pattern whiteSpacePattern = Pattern.compile("\\s*");
                //Matcher lineSpace = whiteSpacePattern.matcher((line));
                Matcher commentLine = commentLinePattern.matcher((line));
                if (line.matches("\\s*")||commentLine.lookingAt()) {
                    System.out.println("STRING");
                    validator.visitedLineSet.add(lineCounter);
                    continue;
                }
                else{ // NO MACH
                    System.out.println(line);
                }


                // if (line.matches("\\s*")){
            }
            //if Pattern.
            //System.out.println(line);
        }


         catch (IOException e) { //TODO CHANGE
            e.printStackTrace();
            return;

    //test guy


        }

        System.out.println(validator.visitedLineSet);

    }

}
