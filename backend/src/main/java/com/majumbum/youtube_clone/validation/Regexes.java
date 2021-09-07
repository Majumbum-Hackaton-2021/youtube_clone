package com.majumbum.youtube_clone.validation;

public class Regexes {

    public static final String MAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String TEXT = "[^<>°§$%&={}*~#\\[\\]]+";
    public static final String NUMBER = "^[0-9]*$";
    public static final String STREETNUMBER = "^([1-9])([\\d-\\s]*?)\\s*([\\w])?$";
    public static final String PASSWORD = "(?=.{8,})" + // at least 8 characters
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])|" + // combination of digits, small letters and big letters
            "(?=.*\\d)(?=.*[A-Z])(?=.*[\\W_])|" + // combination of digits, big letters and special characters
            "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])|" + // combination of small letters, big letters and special characters
            "(?=.*\\d)(?=.*[a-z])(?=.*[\\W_]))" + // combination of digits, small letters and special characters
            "^(([^<>{}\";\\/\\\\´`~^°§\\(\\)\\|]))*$" + // excluding some special characters
            ".*";
            // conclusion: passwords must have at least three of the four character groups:
            // digits
            // small letters
            // big letters
            // special characters except ^<>{}";/\´`~^°§()|
    private Regexes(){}
}
