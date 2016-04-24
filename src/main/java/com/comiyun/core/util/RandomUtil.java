package com.comiyun.core.util;

/**
 * 随机生成器
 *
 * @author david
 */
public class RandomUtil {

    /**
     * 生成6位随机码
     *
     * @return
     */
    public static String random6() {
        String random = String.valueOf((int) (Math.random() * 1000000));
        int prex = 6 - random.length();
        if (prex > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < prex; i++) {
                sb.append("0");
            }
            random = sb.toString() + random;
        }
        return random;
    }
}
