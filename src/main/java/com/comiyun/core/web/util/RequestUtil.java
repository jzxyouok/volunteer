package com.comiyun.core.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求工具类
 *
 * @author ydwcn
 * @ClassName: RequestUtil
 * @date 2014-6-16 上午10:06:46
 */
public class RequestUtil {

    public static String getString(HttpServletRequest request, String key) {
        return getString(request, key, "");
    }

    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        String value = defaultValue;
        if (request != null) {
            value = request.getParameter(key);
        }
        return value;
    }

    public static Boolean getBoolean(HttpServletRequest request, String key) {
        return getBoolean(request, key, false);
    }

    public static Boolean getBoolean(HttpServletRequest request, String key, Boolean defaultValue) {
        Boolean value = defaultValue;
        if (request != null) {
            String v = request.getParameter(key);
            value = Boolean.valueOf(v);
        }
        return value;
    }

    public static int getInt(HttpServletRequest request, String key,
                             int defaultValue) {
        String str = request.getParameter(key);
        if (StringUtils.isEmpty(str))
            return defaultValue;
        return Integer.parseInt(str.trim());
    }

    public static int getInt(HttpServletRequest request, String key) {
        return getInt(request, key, 0);
    }

    public static long getLong(HttpServletRequest request, String key,
                               long defaultValue) {
        String str = request.getParameter(key);
        if (StringUtils.isEmpty(str))
            return defaultValue;
        return Long.parseLong(str.trim());
    }

    public static long getLong(HttpServletRequest request, String key) {
        return getLong(request, key, 0L);
    }

    public static List<Long> getLongListByStr(HttpServletRequest request, String key) {
        String str = request.getParameter(key);
        if (StringUtils.isEmpty(str))
            return null;
        String[] aryId = str.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < aryId.length; i++) {
            String strValue = aryId[i];
            try {
                Long value = Long.parseLong(strValue);
                list.add(value);
            } catch (NumberFormatException e) {
            }
        }
        return list;
    }

    /**
     * 获取IP
     *
     * @param @param  request
     * @param @return
     * @return String
     * @throws
     * @Title: getIpAddr
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取域名
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://").append(serverName);
        if (port != 80) {
            sb.append(":").append(port);
        }
        sb.append(path);
        return sb.toString();
    }

    /**
     * 判断是否Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(requestType);
    }
}
