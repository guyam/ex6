package oop.ex6.main.Regex;

public class RegexRepository2 {

    final String functionTitleRegex = "[ \\t\\r]*void[ \\t\\r]+([a-zA-Z]+\\w?)[ \\t\\r]*\\((.*)\\)[ \\t\\r]*\\{";
    final String functionCallRegex = "[ \\t\\r]*([a-zA-Z]+\\w?)[ \\t\\r]*\\(.*[ \\t\\r]*;$";
}
