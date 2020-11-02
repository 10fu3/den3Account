package net.den3.den3Account;

import java.util.regex.Pattern;

public class StringChecker {
    public static boolean isMailAddress(String mail){
        String pattern = "^[a-zA-Z0-9!#$%&'_`/=~*+\\-?^{|}]+(\\.[a-zA-Z0-9!#$%&'_`/=~*+\\-?^{|}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
        java.util.regex.Pattern p = Pattern.compile(pattern);
        return mail.matches(pattern);
    }
}
