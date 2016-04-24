package com.comiyun.core.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码错误异常
 *
 * @author ydwcn
 * @ClassName: IncorrectCaptchaException
 * @date 2014-6-20 上午10:21:17
 */
public class IncorrectCaptchaException extends AuthenticationException {

    private static final long serialVersionUID = 5627148541096589060L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
