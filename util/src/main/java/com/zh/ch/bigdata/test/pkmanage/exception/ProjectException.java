package com.zh.ch.bigdata.test.pkmanage.exception;

/**
 * @author xzc
 * @description 全局异常
 * @date 2021/01/05
 */
public class ProjectException extends Exception{

    public ProjectException() {
    }

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectException(Throwable cause) {
        super(cause);
    }

    public ProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
