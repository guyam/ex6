package oop.ex6.main.Types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.TreeMap;

abstract public class JavaType {


    private String type;
    private boolean wasInitialized;
    private boolean isFinal;
    static TreeMap<String, String[]> compatibleTypes = initializeTypes();

    private static TreeMap<String, String[]> initializeTypes() {
        TreeMap<String, String[]> temp = new TreeMap<>();
        temp.put("int", new String[]{"int"});
        temp.put("double", new String[]{"int","double"});
        temp.put("char", new String[]{"char"});
        temp.put("boolean", new String[]{"int","double","boolean"});
        temp.put("String", new String[]{"int","double","char","boolean","String"});
        return temp;
    }


    public JavaType(String typeName) {
        type = typeName;
        wasInitialized = false;
    }

    public JavaType(String typeName, String initialValue, boolean initWithFinal) {
        type = typeName;
        isFinal=initWithFinal;
        try {
            update(initialValue);
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
        wasInitialized = true;
    }

    public void update(String var) throws ClassCastException {
        try(areComparable(var))
        if (!areComparable(var))
            throw new ClassCastException("Bad Assignment Parameter");
        else {
            assign(var);
            wasInitialized = true;
        }
    }

    protected abstract void assign(String var);

    abstract public void add(String var) throws ClassCastException;

    abstract public void subtract(String var) throws ClassCastException;

    abstract public void multiply(String var) throws ClassCastException;

    private boolean areComparable(String newVal) {

        if (returnType(newVal)==null)

        return true;
    }

    private String returnType(String val) {
        if (isInt(val))
            return "int";
        if (isDouble(val))
            return "double";
        if (isBoolean(val))
            return "boolean";
        if (isChar(val))
            return "char";
        if(isString(val))
            return "string";
        return null;
    }

    private boolean isInt(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String val) {
        return (val.equals("true") || val.equals("false"));
    }

    private boolean isChar(String val) {
        final String regex = "'.'";
        return val.matches(regex);
    }

    private boolean isString(String val) {
        final String regex = "\".*\"";
        return val.matches(regex);
    }

}
