package org.labcabrera.sample.api.shared.domain.exceptions;

public class EventNotificationException extends DomainException {

    private static final String CODE = "EVENT_NOTIFICATION_ERROR";

    public EventNotificationException(String message, Throwable cause) {
        super(CODE, 500, message, cause);
    }

}
