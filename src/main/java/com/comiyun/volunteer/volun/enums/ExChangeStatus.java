package com.comiyun.volunteer.volun.enums;

public enum ExChangeStatus {

    canEx("可兑换"), stopEx("停止兑换");

    private String text;

    private ExChangeStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
