package com.myretailproduct.service.Exception;


/**
 * Created by agane13 on 11/8/17.
 */

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
