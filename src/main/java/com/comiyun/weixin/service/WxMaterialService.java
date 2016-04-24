package com.comiyun.weixin.service;

import com.comiyun.weixin.utils.HttpsUtil;
import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 素材管理
 *
 * @author david
 */
public class WxMaterialService {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", "jBEmmnUkolVWZKAmPx0ajNlJwd1NhEwe9r_Qx0-mywqH2a9iPLik9vjXEBnySakXiZEI2ZvkYjog6JInJBW-Hxnhs8_NDhHc5eiFKuSblesJCMiACAYRB");
        String body = "{\"type\":\"news\",\"offset\":0,\"count\":20}";
        JsonNode json = HttpsUtil.sendPost("https://api.weixin.qq.com/cgi-bin/material/batchget_material", params, body);
        System.out.println(json.toString());
    }

}
