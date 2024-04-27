package com.epam.rd.autotasks.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class EpamEmailValidation {

    public static boolean validateEpamEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("([a-z]+_[a-z]+@epam.com)|([a-z]+_[a-z]+\\d@epam.com)");
        return pattern.matcher(email).matches();
    }
}
