package oop.ex6.main;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;

import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Regex.RegexRepository;
import oop.ex6.main.Types.FunctionType;
import oop.ex6.main.Types.JavaType;

import java.io.*;
import java.util.regex.*;
import java.util.*;


public class Sjavac {


    public Set<Integer> visitedLineSet;
    public ArrayList<TreeMap<String, JavaType>> variableDict;
    public TreeMap<FunctionType, String> methodDict;


    Sjavac() {
        this.visitedLineSet = new HashSet<>();
        this.variableDict = new ArrayList<>();
        variableDict.add(new TreeMap<>());
        this.methodDict = new TreeMap<>();
    }


    private void firstRunner(String filePath, int scope) throws IOException, EmptyFinalDeclarationException {

        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int lineCounter = 0;
            //todo - change the signature of regex to accept the new treemaps!!
            RegexRepository variableHandler = new RegexRepository("", this.variableDict.get(0), scope);
            while ((line = bufferedReader.readLine()) != null) {
                lineCounter++;
                Pattern commentLinePattern = Pattern.compile("//");
                //System.out.println(lineCounter); //TODO DEL
                Matcher commentLine = commentLinePattern.matcher((line));
                if (line.matches("\\s*") || commentLine.lookingAt()) {
                    //System.out.println("STRING");  //TODO DEL
                    this.visitedLineSet.add(lineCounter);
                    continue;
                } else {
                    variableHandler.setString(line);
                    variableHandler.checkSyntaxValidity();
                    /*
                    psuedocode
                    if(!variableHandler.checkSyntaxValidity()) // should change name to syntax of javatypes
                        variableHandler.checkFuncDecSyntax()
                    f
                     */
                }


                // if (line.matches("\\s*")){
            }
            //if Pattern.
            //System.out.println(line);
        } catch (IOException e) { //TODO CHANGE
            e.printStackTrace();
            return;

            //test guy


        }

        //System.out.println(this.visitedLineSet);

    }

    public static void main(String[] args) throws IOException, EmptyFinalDeclarationException {
        // TODO  - CHEK PARAMETERS???
        String pathName = args[0];
        Sjavac validator = new Sjavac();
        validator.firstRunner(pathName, 0);
        System.out.println(validator.variableDict.get(0).size()); // should be 1
        for (TreeMap<String, JavaType> tree : validator.variableDict) {
            for (String treeKey : tree.keySet()) {
                System.out.println(tree.get(treeKey));
                System.out.println("also, its name is : " + treeKey);
            }
        }


    }


}
