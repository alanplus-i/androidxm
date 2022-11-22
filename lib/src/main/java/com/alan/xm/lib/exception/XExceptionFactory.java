package com.alan.xm.lib.exception;

import java.io.IOException;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class XExceptionFactory {

    public static final int ERR_CODE_UNKNOWN = 911000;
    public static final int ERR_CODE_ENCODING = 911001;
    public static final int ERR_CODE_URL_INVALID = 911002;
    public static final int ERR_CODE_HTTP_BODY_NULL = 911003;
    public static final int ERR_CODE_HTTP_IO = 911004;

    public static XException createEnCodingError() {
        return new XException(ERR_CODE_ENCODING, "encoding error");
    }

    public static XException createUrlInvalidError() {
        return new XException(ERR_CODE_URL_INVALID, "url is invalid");
    }

    public static XException createHttpBodyNullError() {
        return new XException(ERR_CODE_HTTP_BODY_NULL, "http body is null");
    }

    public static XException createHttpIoError(IOException e) {
        return new XException(ERR_CODE_HTTP_IO, e == null ? "error is null" : e.getMessage());
    }

    public static XException createUnknownError(Throwable e) {
        return new XException(ERR_CODE_UNKNOWN, e == null ? "error is null" : e.getMessage());
    }

}
