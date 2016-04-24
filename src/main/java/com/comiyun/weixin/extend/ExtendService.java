package com.comiyun.weixin.extend;

import java.util.Map;

public interface ExtendService {

    /**
     * 封装菜单对象
     *
     * @param btn 菜单按钮
     */
    public void autoWireMenu(Map<String, Object> btn);

    public String service(String fromUserName, String toUserName);

}
