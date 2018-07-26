package org.rokomari.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
