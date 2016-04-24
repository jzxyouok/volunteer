package com.comiyun.weixin.utils;

import com.comiyun.weixin.exception.WxException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    public static JsonNode parseJson(String str) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String toJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new WxException("JSON格式转换失败", e);
        }
        return json;
    }

    public static void main(String[] args) {
        JsonNode json = parseJson("{\"errcode1\":40001,\"errmsg\":\"invalid credential, access_token is invalid or not latest hint: [jgl_da0671vr18]\"}");
        String errcode = json.path("errcode").asText();
        System.out.println(errcode);
    }
}
