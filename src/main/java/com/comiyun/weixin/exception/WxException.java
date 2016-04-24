package com.comiyun.weixin.exception;

/**
 * 微信服务异常
 *
 * @author david
 */
public class WxException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WxException(String message) {
        super(message);
    }

    public WxException(String message, Throwable cause) {
        super(message, cause);
    }
}
