package com.alan.xm.lib.exception;

/**
 * @author alan ye
 * created on  2022/11/19
 * 封装自定义错误类
 */
public class XException extends Exception {

    /**
     * 错误码
     */
    public int code;
    /**
     * 保存异常信息
     */
    public Throwable throwable = null;

    /**
     * 错误提示信息
     */
    public String message = null;

    /**
     * 保存错误
     */
    public String content;


    public XException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
