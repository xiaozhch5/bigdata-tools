package com.zh.ch.bigdata.test.pkmanage.exception;

/**
 * @author xzc
 * @description Properties配置文件找不到key值
 * @date 2021/01/05
 */
public class PropertiesFileKeyNotFoundException extends ProjectException {

    String exceptionMessage;

    public PropertiesFileKeyNotFoundException() {
    }

    public PropertiesFileKeyNotFoundException(String message) {
        super(message);
        exceptionMessage = message;

    }

    public PropertiesFileKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesFileKeyNotFoundException(Throwable cause) {
        super(cause);
    }

    public PropertiesFileKeyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("该Properties文件中不含有该Key: ");
        stringBuilder.append(exceptionMessage);
        return stringBuilder.toString();
    }

}
