package com.comiyun.core.exception;

/**
 * 业务异常类
 *
 * @author ydwcn
 * @ClassName: BusinessException
 * @date 2014-6-12 上午10:15:57
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
