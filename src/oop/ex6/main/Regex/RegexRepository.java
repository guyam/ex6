package oop.ex6.main.Regex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRepository {
    String[] classNames={"String","double","int","boolean","char"};
    public static final Pattern finalTypeRegex = Pattern.compile("^final\\s.*;$");
    public static final Pattern assignmentTypeRegex = Pattern.compile("");
}
