package oop.ex6.main.Types;

import java.util.Iterator;
import java.util.LinkedHashMap;


public class FunctionType {

    private LinkedHashMap<String, JavaType> parameters = new LinkedHashMap<>();
    static String[] legalTypes = {"String", "boolean", "char", "int", "double"};

    public FunctionType(LinkedHashMap<String, JavaType> functionParameters) {
        parameters.putAll(functionParameters);
    }


    public LinkedHashMap<String, JavaType> getParameters() {
        return parameters;
    }

    public boolean sameSignature(LinkedHashMap<String, JavaType> other) {
        //first, let's check that if the are both just no param functions, we'll automatically return true
        if (other.size() == 0 && parameters.size() == 0)
            return true;
        //now, if they are of not the same size, we'll return false, as they can't have the same signature
        if (other.size() != parameters.size())
            return false;
        //now we know that they are of the same size, we'll go over them both to see if the signature of the types the
        //function receives is the same.
        Iterator<JavaType> first = parameters.values().iterator();
        Iterator<JavaType> second = other.values().iterator();

        while (first.hasNext()) {
            if (!JavaType.contains(JavaType.compatibleTypes.get(first.next().getType()), second.next().getType()))
                return false;
        }
        return true;
    }

}
