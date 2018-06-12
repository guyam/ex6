package oop.ex6.main.Types;

import java.util.ArrayList;

public class FunctionType {

    private ArrayList<String> params = new ArrayList<>();
    static String[] legalTypes = {"String", "boolean", "char", "int", "double"};

    public FunctionType(ArrayList<String> functionParameters) {
        params.addAll(functionParameters);
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public boolean sameSignature(FunctionType other) {
        //first, let's check that if the are both just no param functions, we'll automatically return true
        if (other.getParams().size() == 0 && params.size() == 0)
            return true;
        //now, if they are of not the same size, we'll return false, as they can't have the same signature
        if (other.getParams().size() != params.size())
            return false;
        //now we know that they are of the same size, we'll go over them both to see if the signature of the types the
        //function receives is the same.
        for (int i = 0; i < params.size(); i++) {
            if (JavaType.contains(JavaType.compatibleTypes.get(params.get(i)),other.getParams().get(i)))
                return false;
        }
        return true;
    }

}
