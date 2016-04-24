package com.comiyun.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则式工具类
 *
 * @author ydwcn
 * @ClassName: RegexUtil
 * @date 2014-6-25 上午9:36:36
 */
public class RegexUtil {

    public static final String REG_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REG_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 是否邮箱
     *
     * @param str
     * @return boolean
     * @throws
     * @Title: isEmail
     */
    public static boolean isEmail(String str) {
        boolean flag = validate(str, REG_EMAIL);
        return flag;
    }

    /**
     * @param str
     * @return boolean
     * @throws
     * @Title: isMobile
     */
    public static boolean isMobile(String str) {
        boolean flag = validate(str, REG_MOBILE);
        return flag;
    }

    /**
     * 验证正则式
     *
     * @param value 需要验证的值
     * @param regex 正则式
     * @return boolean
     * @throws
     * @Title: validate
     */
    public static boolean validate(String value, String regex) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(value);
        boolean rs = mat.find();
        return rs;
    }
}
