package oop.ex6.main;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import oop.ex6.main.Regex.RegexRepository;
import oop.ex6.main.Types.FunctionType;
import oop.ex6.main.Types.JavaType;

import java.io.*;
import java.util.regex.*;
import java.util.*;





public class Sjavac {


   public Set<Integer> visitedLineSet;
   public TreeMap<String, JavaType> variableDict;
   public TreeMap<String, FunctionType> methodDict;


    Sjavac(){
        this.visitedLineSet  = new HashSet<>();
        this.variableDict = new TreeMap<>();
        this.methodDict = new TreeMap<>();
    }






    private void firstRunner(String filePath, int scope) throws IOException {

        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int lineCounter = 0;
            RegexRepository variableHandler = new RegexRepository("", this.variableDict, scope);
            while ((line = bufferedReader.readLine()) != null) {
                lineCounter++;
                Pattern commentLinePattern = Pattern.compile("//");
                //System.out.println(lineCounter); //TODO DEL
                Matcher commentLine = commentLinePattern.matcher((line));
                if (line.matches("\\s*")||commentLine.lookingAt()) {
                    //System.out.println("STRING");  //TODO DEL
                    this.visitedLineSet.add(lineCounter);
                    continue;
                }
                else{
                    variableHandler.setString(line);
                    variableHandler.checkSyntaxValidity();
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

        //System.out.println(this.visitedLineSet);

    }

    public static void main(String[] args) throws IOException{
        // TODO  - CHEK PARAMETERS???
        String pathName = args[0];
        Sjavac validator = new Sjavac();
        validator.firstRunner(pathName, 0);
        System.out.println(validator.variableDict.size()); // should be 1
        for (String treeKey : validator.variableDict.keySet()){
            System.out.println(validator.variableDict.get(treeKey));
            System.out.println(treeKey);
        }


    }


}
