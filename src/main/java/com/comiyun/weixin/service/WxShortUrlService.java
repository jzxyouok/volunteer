package com.comiyun.weixin.service;

import com.comiyun.weixin.utils.HttpsUtil;
import com.comiyun.weixin.utils.JsonUtil;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信端链接URL
 *
 * @author david
 */
@Service
public class WxShortUrlService {
    @Autowired
    private WxAccountService wxAccountService;

    /**
     * 生成短码
     *
     * @param longUrl
     * @return
     */
    public String shortUrl(String longUrl) {
        String token = wxAccountService.getAccessToken();
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        Map<String, Object> action = new HashMap<String, Object>();
        action.put("action", "long2short");
        action.put("long_url", longUrl);
        String body = JsonUtil.toJson(action);
        JsonNode json = HttpsUtil.sendPost("https://api.weixin.qq.com/cgi-bin/shorturl", params, body);
        String short_url = json.path("short_url").asText();
        return short_url;
    }

}
