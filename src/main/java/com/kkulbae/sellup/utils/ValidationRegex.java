package com.kkulbae.sellup.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexPassword(String target) {
        String regex = "^(?=.*\\d)(?=.*[a-z]).{8,20}$";


        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexNickName(String target) {
        String regex = "^(?=.*[가-힣-a-zA-Z-0-9]).{1,10}$";
        //String regex="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[가-힣])[a-zA-Z가-힣\\d]{8,}$";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}

