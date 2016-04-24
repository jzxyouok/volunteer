package com.comiyun.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author ydwcn
 * @ClassName: ExceptionUtil
 * @date 2014-6-12 上午10:19:40
 */
public class ExceptionUtil {
    public static String getExceptionMessage(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
