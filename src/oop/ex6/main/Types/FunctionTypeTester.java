package oop.ex6.main.Types;

import java.util.ArrayList;

public class FunctionTypeTester {
    public static void main(String[] args) {
        ArrayList<String> params1 = new ArrayList<>();
        ArrayList<String> params2 = new ArrayList<>();
        ArrayList<String> params3 = new ArrayList<>();
        ArrayList<String> params4 = new ArrayList<>();
        params1.add("int");
        params1.add("double");
        params1.add("double");
        params1.add("char");
        params1.add("String");
        params2.addAll(params1);
        params3.add("char");
        params3.add("String");
        FunctionType test1 = new FunctionType(params1);
        FunctionType test2 = new FunctionType(params2);
        FunctionType test3 = new FunctionType(params3);
        FunctionType test4 = new FunctionType(params4);
        assert (test1.sameSignature(test2));
        assert (!test1.sameSignature(test3));
        assert (test3.sameSignature(test3));
        assert (!test2.sameSignature(test4));
    }
}

