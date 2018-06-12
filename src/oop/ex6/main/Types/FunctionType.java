package oop.ex6.main.Types;

import java.util.ArrayList;

public class FunctionType {

    private ArrayList<String> params = new ArrayList<>();
    static String[] legalTypes = {"String", "boolean", "char", "int", "double"};

    public FunctionType(ArrayList<String> functionParameters) {
        params.addAll(functionParameters);
    }

}
