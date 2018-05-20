package com.myretailproduct.service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by agane13 on 11/8/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product invalid")
public class InvalidProductException extends ProductServiceException {

    public InvalidProductException(final String className,
                                   final String methodName, final String message) {
        super(className, methodName, message);

    }

    public InvalidProductException(final String message) {
        super(message);

    }

    public InvalidProductException(final String className,
                                   final String methodName, final String message, final Throwable thrw) {
        super(className, methodName, message, thrw);

    }
}
