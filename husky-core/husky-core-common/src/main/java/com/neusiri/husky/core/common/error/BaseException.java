package com.neusiri.husky.core.common.error;

/**
 * <p>系统异常基类</p>
 * <p>创建日期：2019-08-09</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
