package com.comiyun.weixin.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信相关工具类
 *
 * @author david
 */
public class WeiXinUtil {

    private static final Logger logger = LoggerFactory.getLogger("");

    /**
     * 解析微信参数
     *
     * @param Request
     * @return
     */
    public static Map<String, String> parseXml(HttpServletRequest request) {
        Map<String, String> map = null;
        try {
            map = new HashMap<String, String>();
            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            logger.info("------Weixin-Start---------------------------------------------------------------------");
            logger.info(document.asXML());
            logger.info("------Weixin-End-----------------------------------------------------------------------");
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList)
                map.put(e.getName(), e.getText());

            // 释放资源
            inputStream.close();
            inputStream = null;
        } catch (Exception e) {
            map = null;
            logger.error("解析微信数据失败", e);
        }
        return map;
    }

    /**
     * 检查密钥
     *
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {

        if (StringUtils.isBlank(token) || StringUtils.isBlank(signature)
                || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)) {
            logger.error("验证微信签名异常,参数不合法");
            return false;
        }

        String[] arr = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error("验证微信签名异常", e);
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {

        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 获取安全认证地址
     *
     * @return
     */
    public static String getAuthUrl(String appId, String callback) {
        String url = "";
        try {
            url = URLEncoder.encode(callback, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("URL ENCODER ERROR", e);
        }
        StringBuffer sb = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
        sb.append(appId);
        sb.append("&redirect_uri=");
        sb.append(url);
        sb.append("&response_type=code&scope=snsapi_base&state=comiyun#wechat_redirect");
        return sb.toString();
    }

    public static String getJsApiSign(String jsApiTicket, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("jsapi_ticket=");
        sb.append(jsApiTicket);
        sb.append("&noncestr=Wm3WZYTPz0wzccnW");
        sb.append("&timestamp=1447336908");
        sb.append("&url=");
        sb.append(url);

        String sha1 = DigestUtils.sha1Hex(sb.toString());

        return sha1;
    }

    public static void main(String[] args) {
        String sha1 = getJsApiSign("kgt8ON7yVITDhtdwci0qee3dBdB-MNGfJ84Bun4iCgpd4iCe9M1xZHg-Ow7jUwlZZBYGV_vlAIiZhW9FpGxJfg", "http://sywx.cloudkd.com.cn/mobile/actinfo?actId=431739811250966528&code=041e514d0b80a96fc6ad7588a7e6e04y&state=comiyun");
        System.out.println(sha1);
    }
}
