package com.example.sp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    private static final String EMAIL_REGEX =
            "([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])";

    private StringUtils() {
    }

    public static List<String> getEmail(String text) {
        List<String> mention = new ArrayList<>();
        Matcher matcher = Pattern.compile(EMAIL_REGEX).matcher(text);
        while (matcher.find()) {
            mention.add(matcher.group());
        }
        return mention;
    }
}
