package com.anycommon.response.expception;


import com.anycommon.response.constant.ErrMsgEnum;

/**
 * anycommon-parent
 *
 * @author wangkai
 * @date 2020/6/8
 */

public class AppSystemException extends RuntimeException{


    private String    errorCode;
    private String msg;

    public AppSystemException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public AppSystemException(ErrMsgEnum errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
        this.msg = errorCode.getErrorMessage();
    }

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
