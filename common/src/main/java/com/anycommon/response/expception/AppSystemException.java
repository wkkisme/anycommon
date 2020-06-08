package com.anycommon.response.expception;


/**
 * anycommon-parent
 *
 * @author wangkai
 * @date 2020/6/8
 */

public class AppSystemException extends RuntimeException{
    public AppSystemException() {
        super();
    }

    public AppSystemException(String message) {
        super(message);
    }

    public AppSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppSystemException(Throwable cause) {
        super(cause);
    }

    protected AppSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
