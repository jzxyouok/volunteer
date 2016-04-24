package com.comiyun.weixin.utils;

import com.comiyun.weixin.WxCode;
import com.comiyun.weixin.exception.WxException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpsUtil {

    private static final Logger logger = LoggerFactory.getLogger("");

    /**
     * 发送GET请求
     *
     * @param requestUrl 请求地址
     * @param params     请求参数
     */
    public static JsonNode sendGet(String requestUrl, Map<String, String> params) {
        return send(requestUrl, "GET", params, null);
    }

    /**
     * 发送POST请求
     *
     * @param requestUrl 请求地址
     * @param params     请求参数
     * @param body
     */
    public static JsonNode sendPost(String requestUrl, Map<String, String> params, String body) {
        return send(requestUrl, "POST", params, body);
    }

    /**
     * 发送请求
     *
     * @param requestUrl 请求地址
     * @param method     请求方式
     * @param params     请求参数
     * @param body       body
     */
    private static JsonNode send(String requestUrl, String method, Map<String, String> params, String body) {
        String paramStr = covertParams(params);
        JsonNode result = null;
        if (paramStr != null) {
            requestUrl = requestUrl + "?" + paramStr;
        }

        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            logger.debug("https_url:{}", requestUrl);

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);

            if ("GET".equalsIgnoreCase(method))
                conn.connect();

            // 当有数据需要提交时
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream in = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            in.close();
            in = null;
            conn.disconnect();
            result = JsonUtil.parseJson(buffer.toString());
        } catch (Exception e) {
            logger.error("请求异常,URL:{}", requestUrl, e);
            throw new WxException("调用微信接口异常", e);
        }
        String errorcode = result.path("errcode").asText();
        if (StringUtils.isNotBlank(errorcode) && !("0".equals(errorcode))) {
            String msg = WxCode.getMsg(errorcode);
            throw new WxException(msg);
        }
        return result;
    }

    /**
     * 转化参数为字段卡
     *
     * @param params
     * @return
     */
    private static String covertParams(Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);
                if (sb.length() == 0) {
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                } else {
                    sb.append("&");
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

}
