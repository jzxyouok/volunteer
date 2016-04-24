package com.comiyun.weixin.utils;

import com.thoughtworks.xstream.XStream;

public class XStreamUtil {

    public static String serializeToXml(Object obj) {
        XStream xstream = new XStream();
        xstream.processAnnotations(obj.getClass());
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }

}
