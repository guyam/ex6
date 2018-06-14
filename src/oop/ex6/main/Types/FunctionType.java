package oop.ex6.main.Types;

import java.util.ArrayList;
import java.util.TreeMap;

public class FunctionType {

    private ArrayList<String> paramName = new ArrayList<>();
    private TreeMap<String,JavaType> parameters = new TreeMap<>();
    static String[] legalTypes = {"String", "boolean", "char", "int", "double"};

    public FunctionType(ArrayList<String> functionParametersName,TreeMap<String,JavaType> functionParameters) {
        paramName.addAll(functionParametersName);
        parameters.putAll(functionParameters);
    }


    public  boolean sameSignature(ArrayList<String> other) {
        //first, let's check that if the are both just no param functions, we'll automatically return true
        if (other.size() == 0 && paramName.size() == 0)
            return true;
        //now, if they are of not the same size, we'll return false, as they can't have the same signature
        if (other.size() != paramName.size())
            return false;
        //now we know that they are of the same size, we'll go over them both to see if the signature of the types the
        //function receives is the same.
        for (int i = 0; i < paramName.size(); i++) {
            if (!JavaType.contains(JavaType.compatibleTypes.get(paramName.get(i)),other.get(i)))
                return false;
        }
        return true;
    }

}
