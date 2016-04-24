package com.comiyun.core.web.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户操作信息
 *
 * @author ydwcn
 * @ClassName: ResultMessage
 * @date 2014年7月9日 上午11:11:43
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 7142285474542129395L;

    /**
     * 是否成功
     */
    private Boolean success = true;
    /**
     * 业务代码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;

    private Map<String, Object> data = new HashMap<String, Object>();

    public void addDataItem(String key, Object value) {
        data.put(key, value);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
