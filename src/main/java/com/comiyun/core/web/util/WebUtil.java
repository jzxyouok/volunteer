package com.comiyun.core.web.util;

import com.comiyun.core.exception.ServiceException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtil {

    /**
     * UrlEncode
     *
     * @param srcUrl
     * @return
     */
    public static String urlEncode(String srcUrl) {
        String targetUrl = null;
        try {
            targetUrl = URLEncoder.encode(srcUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("URL转码失败", e);
        }
        return targetUrl;
    }

}
