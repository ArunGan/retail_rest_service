package com.myretailproduct.service.Exception;

import org.springframework.core.NestedExceptionUtils;

/**
 * Created by agane13 on 11/9/17.
 */
public class ProductServiceException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3960931400559453729L;
    /**
     * In case of No Error trace available.
     */
    private static final String NO_TRACE = "No Trace/Exception data available";
    /**
     * Exception Trace text.
     */
    private static final String EXCEPTIONTRACE = "|EXCEPTIONTRACE=";
    /**
     * Length of the trace for short message.
     */
    private static final int S_TRACE_LENGTH = 200;
    protected String errorCode;
    private String className;
    private String methodName;
    private Throwable rootCause;

    /**
     * @param message
     */
    public ProductServiceException(final String message) {
        super(message);

    }

    /**
     * Method ProductServiceException.
     *
     * @param className1
     * @param methodName1
     * @param message
     */
    public ProductServiceException(final String className1, final String methodName1, final String message) {
        super(message);
        className = className1;
        methodName = methodName1;
    }

    /**
     * ProductServiceException
     *
     * @param className1
     * @param methodName1
     * @param message
     * @param exp
     */
    public ProductServiceException(final String className1, final String methodName1, final String message, final Throwable exp) {
        super(message);
        className = className1;
        methodName = methodName1;
        rootCause = exp;
    }


    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the throwable
     */
    public Throwable getRootCause() {
        return rootCause;
    }

    /**
     * This Method would return the Short Trace which is of length <= 200
     * characters only. This can be used generally in logging Short Errors.<br/>
     *
     * @return String
     */
    public String getShortTrace() {
        String shortTrace = null;
        if (this.rootCause == null) {
            shortTrace = NO_TRACE;
        } else {
            String completeTrace = getCompleteTrace();
            if (completeTrace != null) {
                if (completeTrace.length() > S_TRACE_LENGTH) {
                    shortTrace = completeTrace.substring(0, S_TRACE_LENGTH);
                } else {
                    shortTrace = completeTrace;
                }
            }

        }
        return shortTrace;
    }

    /**
     * This Method would return the Complete Trace.<br/>
     * This can be used generally in logging Complete error trace.<br/>
     *
     * @return String
     */
    public String getCompleteTrace() {
        String completeTrace;
        if (rootCause == null) {
            completeTrace = NO_TRACE;
        } else {
            completeTrace = NestedExceptionUtils.buildMessage(methodName, rootCause);
        }
        return completeTrace;
    }
}
