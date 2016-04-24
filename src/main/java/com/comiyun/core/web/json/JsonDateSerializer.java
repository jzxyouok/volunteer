package com.comiyun.core.web.json;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * Json Date 日期序列化
 *
 * @author david
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(DateFormatUtils.format(date, "yyyy-MM-dd"));
    }
}