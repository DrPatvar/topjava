package ru.javawebinar.topjava.util;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

public class MessageSourceAccessor {

    private final MessageSource messageSource;

    public MessageSourceAccessor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, String defaultMessage) {
        String msg = this.messageSource.getMessage(code, null, defaultMessage, null);
        return (msg != null ? msg : "");
    }

    public String getMessage(String code) throws NoSuchMessageException {
        return this.messageSource.getMessage(code, null, null);
    }
}
