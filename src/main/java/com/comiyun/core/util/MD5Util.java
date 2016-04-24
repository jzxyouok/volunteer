package com.comiyun.core.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密
 *
 * @author david
 */
public class MD5Util {

    public static String md5(String str) {
        String md5 = DigestUtils.md5Hex(str);
        return md5;
    }

    public static void main(String[] args) {
        String m = md5("1");

        System.out.println(m);
    }

}
