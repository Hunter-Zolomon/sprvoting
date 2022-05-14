package com.spr.votingsystem.utilities;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HelperFunctions {
    public static boolean isSessionValid(HttpSession session) {
        return (session != null && session.getAttribute("username") != null && session.getAttribute("role") != null);
    }

    public static boolean isUser(HttpSession session, String role) {
        String usr_role = (String) session.getAttribute("role");
        return usr_role.equals(role);
    }

    public static String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean validatePasswordHash(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

    public static String randomPasswordGenerator() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
          .concat(numbers)
          .concat(specialChar)
          .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
          .mapToObj(c -> (char) c)
          .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
          .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
          .toString();
    }
}
