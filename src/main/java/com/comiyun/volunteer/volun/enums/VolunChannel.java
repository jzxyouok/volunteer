package com.comiyun.volunteer.volun.enums;

/**
 * 义工来源渠道
 *
 * @author david
 */
public enum VolunChannel {

    wxreg("微信注册"), sysadd("系统添加");

    private String text;

    private VolunChannel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
