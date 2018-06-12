package oop.ex6.main.Types;

import oop.ex6.main.Exceptions.EmptyFinalDeclarationException;
import oop.ex6.main.Exceptions.FinalAssignmentException;

import java.util.ArrayList;

public class JavaTypeTest {
    public static void main(String[] args) throws EmptyFinalDeclarationException, FinalAssignmentException {
        /*
        good guy tests - should compile and run great
         */
        //initialization
        ArrayList<JavaType> testList = new ArrayList<>();
        // empty int
        JavaType emptyint1 = new JavaType("int");
        testList.add(emptyint1);
        // empty char
        JavaType emptychar1 = new JavaType("char");
        testList.add(emptychar1);
        // final int=5;
        JavaType finalint5 = new JavaType("int", "5", true);
        testList.add(finalint5);
        //double =7.2;
        JavaType double72 = new JavaType("double", "7.2", false);
        testList.add(double72);
        //String = ""
        JavaType emptyString = new JavaType("String", "\"\"", false);
        testList.add(emptyString);
        //empty char
        JavaType emptyChar = new JavaType("char", "''", false);
        testList.add(emptyChar);
        // boolean flase
        JavaType boolFalse = new JavaType("boolean", "false", false);
        testList.add(boolFalse);
        //double =7.2;
        JavaType stringBlahbah = new JavaType("String", "\"blahblah\"", false);
        testList.add(stringBlahbah);

        for (JavaType var : testList)
            System.out.println(var);
        System.out.println("\n\n\n\n\n\n\n\n\n\n");

        //assignment - play nice
        double72.update("5.5");
        double72.update("5.0");
        double72.update("5");

        boolFalse.update("true");

        emptyChar.update("\'#\'");

        emptyint1.update("8");

        //assignment - less nice
        double72.update(emptyint1);
        boolFalse.update(double72);
        boolFalse.update("0");

        for (JavaType var : testList)
            System.out.println(var);


        /*
        bad guy tests - gotta catch em' all
         */
        //update a final variable
        try {
            finalint5.update("4");
        } catch (FinalAssignmentException e) {
            System.out.println("Passed test 1");
        }
        //bad update
        try {
            emptyChar.update("'34343'");
        } catch (ClassCastException e) {
            System.out.println("Passed test 2");
        }
        //bad update
        try {
            emptyString.update("\"");
        } catch (ClassCastException e) {
            System.out.println("Passed test 3");
        }
        //bad update
        try {
            emptyString.update("albacore");
        } catch (ClassCastException e) {
            System.out.println("Passed test 4");
        }
        //bad update
        try {
            emptyint1.update("3.5");
        } catch (ClassCastException e) {
            System.out.println("Passed test 5");
        }
        //bad update
        try {
            boolFalse.update("'34343'");
        } catch (ClassCastException e) {
            System.out.println("Passed test 6");
        }
        //bad update
        try {
            double72.update("5A");
        } catch (ClassCastException e) {
            System.out.println("Passed test 7");
        }
    }
}
