package com.comiyun.weixin;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 微信业务代码
 *
 * @author david
 */
public class WxCode {

    private static final Map<String, String> errorCodeMap = new HashMap<String, String>();

    static {
        ResourceBundle rb = ResourceBundle.getBundle("wx_errorcode");
        if (rb != null) {
            Enumeration<String> keys = rb.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = rb.getString(key);
                errorCodeMap.put(key, value);
            }
        }
    }

    /**
     * 获取微信错误代码描述
     *
     * @param errorcode
     * @return
     */
    public static String getMsg(String errorcode) {
        String msg = errorCodeMap.get(errorcode);
        if (msg != null) {
            return msg;
        } else {
            return "未知错误,请联系管理员";
        }
    }

    public static void main(String[] args) {
        System.out.println(getMsg("40004"));
    }

}
