package com.majumbum.youtube_clone.scopes.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StaticUtils {

    public static HttpServletRequest getHttpServletRequestByContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getHttpServletResponseByContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static FlashMap getOutputFlashMap() {
        return RequestContextUtils.getOutputFlashMap(getHttpServletRequestByContext());
    }

    public static void logoutAndInvalidateSession() throws ServletException {
        final HttpServletRequest httpServletRequest = getHttpServletRequestByContext();

        httpServletRequest.logout();
        SecurityContextHolder.clearContext();
        if (httpServletRequest.getSession() != null) {
            httpServletRequest.getSession().invalidate();
        }
    }

    private static final char[] characters = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };

    private static final char[] secureCharacters = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '!', '?', '#', '&', '_', '-', '*', '=', '$', '@', '€'
    };

    public static String generatePassword(final int length) {
        return generatePassword(length, (length * 3) / 4);
    }

    public static String generateVerificationId() {
        return generatePassword(250, 250);
    }

    private static String generatePassword(final int length, final int amountNormalCharacters) {
        if (length < 1) throw new IllegalArgumentException("Length must be at least 1.");
        if (amountNormalCharacters < 0) throw new IllegalArgumentException("AmountNormalCharacters must not be negative.");
        if (amountNormalCharacters > length) throw new IllegalArgumentException("AmountNormalCharacters must not be larger than length.");

        final Random random = new SecureRandom();

        final int amountSecureCharacters = length - amountNormalCharacters;

        final List<Character> selectedChars = new ArrayList<>();

        for (int i = 0; i < amountNormalCharacters; i++) {
            selectedChars.add(characters[random.nextInt(characters.length)]);
        }

        for (int i = 0; i < amountSecureCharacters; i++) {
            selectedChars.add(secureCharacters[random.nextInt(secureCharacters.length)]);
        }

        Collections.shuffle(selectedChars);

        final StringBuilder sb = new StringBuilder();
        for (final Character character : selectedChars) {
            sb.append(character.charValue());
        }

        return sb.toString();
    }

    public static String getQueryParameterString() {
        final String queryString = getHttpServletRequestByContext().getQueryString();
        if (queryString.startsWith("?")) return queryString;
        else return "?" + queryString;
    }

}
