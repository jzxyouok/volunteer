package com.comiyun.core.web.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;

/**
 * json类型转换
 *
 * @author david
 * @TODO 有时间 研究jackson2.X
 */
public class JsonObjectMapper extends ObjectMapper {

    public JsonObjectMapper() {
        CustomSerializerFactory factory = new CustomSerializerFactory();
        //Long型转换为字符串,解决Javascript的精度问题
        factory.addGenericMapping(Long.class, new JsonSerializer<Long>() {
            @Override
            public void serialize(Long value,
                                  JsonGenerator jsonGenerator,
                                  SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString(String.valueOf(value));
            }
        });
        this.setSerializerFactory(factory);
    }
}
