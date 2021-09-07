package com.majumbum.youtube_clone.scopes.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    @Autowired
    public MessageSource messageSource;

    public String getValue(final String key, final Object... args) {
        if (key == null) throw new NullPointerException("Key must not be null.");
        if (key.isEmpty()) throw new IllegalArgumentException("Key must not be empty.");

        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return "Message for key '" + key + "' not found.";
        }
    }

    public String getValueOrException(final String key, final Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

}
