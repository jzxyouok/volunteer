package com.comiyun.volunteer.volun.enums;

/**
 * 积分变更业务代码
 *
 * @author david
 */
public enum IntegralBizType {

    wxreg("微信注册"), actjoin("参加活动"), actmiss("报名未参加活动"), exchange("积分兑换"), hand("手动变更"), transfe("积分转换");

    private String text;

    private IntegralBizType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
