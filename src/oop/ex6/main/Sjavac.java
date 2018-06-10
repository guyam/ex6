package oop.ex6.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.regex.*;

public class Sjavac {

    public static void main(String[] args) throws IOException {
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
            while ((line = bufferedReader.readLine()) != null) {
                Pattern whiteSpacePattern = Pattern.compile("\\s*");
                Pattern commentLinePattern = Pattern.compile("//");
                Matcher lineSpace = whiteSpacePattern.matcher((line));
                Matcher commentLine = commentLinePattern.matcher((line));
                if (line.matches("\\s*")||commentLine.lookingAt()) {
                    System.out.println("STRING");
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




        }


    }

}
